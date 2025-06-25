package com.example.main.services;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.main.dto.ArtistDTO;
import com.example.main.dto.SongDTO;
import com.example.main.entity.Artist;
import com.example.main.entity.Song;
import com.example.main.repository.ArtistRepository;
import com.example.main.repository.SongRepository;

@Service
public class ArtistServiceImplementation implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<ArtistDTO> getAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistDTO getArtistById(Long id) {
        return artistRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public ArtistDTO addArtist(String name, MultipartFile image) {
        Artist artist = new Artist();
        artist.setName(name);

        if (image != null && !image.isEmpty()) {
            Map<String, Object> uploadResult = uploadImage(image);
            artist.setImage((String) uploadResult.get("secure_url"));
            artist.setImageId((String) uploadResult.get("public_id"));
        }

        artist = artistRepository.save(artist);
        return convertToDTO(artist);
    }

    @Override
    public Map<Boolean, Object> updateArtist(Long id, String name, MultipartFile image) {
        Optional<Artist> optionalArtist = artistRepository.findById(id);
        Map<Boolean, Object> data = new HashMap<>();

        if (optionalArtist.isEmpty()) {
            data.put(false, "Artist not found");
            return data;
        }

        Artist artist = optionalArtist.get();

        // Optional: check if new name already exists (only if name changed)
        if (name != null && !name.equalsIgnoreCase(artist.getName())) {
            Optional<Artist> existingByName = artistRepository.findByName(name);
            if (existingByName.isPresent()) {
                data.put(false, "Artist with this name already exists");
                return data;
            }
            artist.setName(name);
        }

        if (image != null && !image.isEmpty()) {
            if (artist.getImageId() != null) {
                deleteImage(artist.getImageId());
            }
            Map<String, Object> uploadResult = uploadImage(image);
            artist.setImage((String) uploadResult.get("secure_url"));
            artist.setImageId((String) uploadResult.get("public_id"));
        }

        artist = artistRepository.save(artist);
        data.put(true, convertToDTO(artist));
        return data;
    }

    @Override
    public boolean deleteArtist(Long id) {
        Optional<Artist> optionalArtist = artistRepository.findById(id);
        if (optionalArtist.isEmpty()) return false;

        Artist artist = optionalArtist.get();

        List<Song> songsWithArtist = songRepository.findByArtist(artist);
        for (Song song : songsWithArtist) {
            song.setArtist(null);
        }
        songRepository.saveAll(songsWithArtist);

        if (artist.getImageId() != null) {
            deleteImage(artist.getImageId());
        }

        artistRepository.delete(artist);
        return true;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> uploadImage(MultipartFile image) {
        try {
            return cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    private void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image", e);
        }
    }

    private ArtistDTO convertToDTO(Artist artist) {
        @SuppressWarnings("null")
		List<SongDTO> songDTOs = Optional.ofNullable(artist.getSongs())
                .orElse(Collections.emptyList())
                .stream()
                .map(song -> new SongDTO(
                        song.getId(),
                        song.getName(),
                        song.getArtist() != null ? song.getArtist().getName() : null,
                        song.getArtist() != null ? song.getArtist().getId() : null,
                        song.getGenre(),
                        song.getLink(),
                        song.getImgLink(),
                        song.getLikeSong(),
                        null
                ))
                .collect(Collectors.toList());

        return new ArtistDTO(artist.getId(), artist.getName(), artist.getImage(), artist.getImageId(), songDTOs);
    }
}

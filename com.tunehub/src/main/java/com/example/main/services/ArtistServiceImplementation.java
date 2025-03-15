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
import com.example.main.repository.ArtistRepository;

@Service
public class ArtistServiceImplementation implements ArtistService {
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
    private Cloudinary cloudinary;
	
	@Override
    public List<ArtistDTO> getAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(this::convertToDTO) // ✅ Use convertToDTO directly
                .collect(Collectors.toList());
    }

    @Override
    public ArtistDTO getArtistById(int id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        return convertToDTO(artist); // ✅ Use convertToDTO directly
    }

    @Override
    public Artist addArtist(String name, MultipartFile image) {
        Artist artist = new Artist();
        artist.setName(name);

        if (image != null && !image.isEmpty()) {
            Map<String, Object> uploadResult = uploadImage(image);
            artist.setImage((String) uploadResult.get("secure_url"));
            artist.setImageId((String) uploadResult.get("public_id"));
        }

        return artistRepository.save(artist);
    }

    @Override
    public Artist updateArtist(int id, String name, MultipartFile image) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        artist.setName(name);

        if (image != null && !image.isEmpty()) {
            // Delete old image from Cloudinary if it exists
            if (artist.getImageId() != null) {
                deleteImage(artist.getImageId());
            }
            // Upload new image
            Map<String, Object> uploadResult = uploadImage(image);
            artist.setImage((String) uploadResult.get("secure_url"));
            artist.setImageId((String) uploadResult.get("public_id"));
        }

        return artistRepository.save(artist);
    }

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
        List<SongDTO> songDTOs = Optional.ofNullable(artist.getSongs())  // ✅ Prevent null pointer
            .orElse(Collections.emptyList())
            .stream()
            .map(song -> new SongDTO(
                song.getId(),
                song.getName(),
                song.getGenre(),
                song.getArtist() != null ? song.getArtist().getId() : null, // ✅ Prevent null artist
                song.getArtist() != null ? song.getArtist().getName() : null,
                song.getLink(),
                song.getImgLink(),
                song.getLikeSong(),
                null
            ))
            .collect(Collectors.toList());

        return new ArtistDTO(artist.getId(), artist.getName(), artist.getImage(), artist.getImageId(), songDTOs);
    }
    
    @Override
    public void deleteArtist(int id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        if (artist.getImageId() != null) {
            deleteImage(artist.getImageId());
        }

        artistRepository.delete(artist);
    }
}

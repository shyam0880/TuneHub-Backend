package com.example.main.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.main.dto.SongDTO;
import com.example.main.entity.Artist;
import com.example.main.entity.Playlist;
import com.example.main.entity.Song;
import com.example.main.repository.ArtistRepository;
import com.example.main.repository.PlaylistRepository;
import com.example.main.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class SongServiceImplementation implements SongService {

    @Autowired
    SongRepository songRepository;

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    ArtistRepository artistRepository;

    private Map<String, String> uploadFile(MultipartFile file, String resourceType) throws Exception {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", resourceType));
        return Map.of(
                "fileUrl", (String) uploadResult.get("secure_url"),
                "fileId", (String) uploadResult.get("public_id")
        );
    }

    @Override
    public String addSong(String name, String genre, Long artistId, MultipartFile songFile, MultipartFile imageFile) throws Exception {
        if (songRepository.existsByName(name)) {
            return "Song Already Exists";
        }

        Map<String, String> songUpload = null;
        Map<String, String> imageUpload = null;

        try {
            // Upload files to cloud
            songUpload = uploadFile(songFile, "video");
            imageUpload = uploadFile(imageFile, "image");

            // Retrieve artist
            Artist artist = artistId != null ? artistRepository.findById(artistId).orElse(null) : null;

            // Create song entity
            Song song = new Song();
            song.setName(name);
            song.setGenre(genre);
            song.setLink(songUpload.get("fileUrl"));
            song.setSongID(songUpload.get("fileId"));
            song.setImgLink(imageUpload.get("fileUrl"));
            song.setImageID(imageUpload.get("fileId"));
            song.setArtist(artist);

            // Save song to DB
            songRepository.save(song);

            return "Song Added Successfully";

        } catch (Exception e) {
            // Cleanup uploads on failure
            if (songUpload != null && songUpload.containsKey("fileId")) {
            	cloudinaryService.deleteFromCloudinary(songUpload.get("fileId"), "video");
            }
            if (imageUpload != null && imageUpload.containsKey("fileId")) {
                cloudinaryService.deleteFromCloudinary(imageUpload.get("fileId"), "image");
            }
            throw new Exception("Failed to add song: " + e.getMessage(), e);
        }
    }


    @Override
    public String updateSong(Long songId, String name, String genre, Long artistId,
                              MultipartFile newSongFile, MultipartFile newImageFile) throws Exception {

    	Optional<Song> optionalSong = songRepository.findById(songId);
        if (optionalSong.isEmpty()) return "Song not found";
        Song existingSong = optionalSong.get();

        if (newSongFile != null && !newSongFile.isEmpty()) {
            cloudinaryService.deleteFromCloudinary(existingSong.getSongID(), "video");
            Map<String, String> songUpload = uploadFile(newSongFile, "video");
            existingSong.setLink(songUpload.get("fileUrl"));
            existingSong.setSongID(songUpload.get("fileId"));
        }

        if (newImageFile != null && !newImageFile.isEmpty()) {
            cloudinaryService.deleteFromCloudinary(existingSong.getImageID(), "image");
            Map<String, String> imageUpload = uploadFile(newImageFile, "image");
            existingSong.setImgLink(imageUpload.get("fileUrl"));
            existingSong.setImageID(imageUpload.get("fileId"));
        }

        if(name!=null)existingSong.setName(name);
        if(genre!=null)existingSong.setGenre(genre);

        Artist artist = (artistId != null && artistId != 0)
                ? artistRepository.findById(artistId).orElse(null)
                : null;
        existingSong.setArtist(artist);

        songRepository.save(existingSong);
        return "Song updated successfully";
    }

    @Override
    public List<SongDTO> fetchAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map(this::convertToDTO).toList();
    }

    private SongDTO convertToDTO(Song song) {
        List<Long> playlistIds = song.getPlaylists().stream().map(Playlist::getId).toList();

        return new SongDTO(
                song.getId(),
                song.getName(),
                song.getGenre(),
                song.getArtist() != null ? song.getArtist().getId() : 0,
                song.getArtist() != null ? song.getArtist().getName() : "Unknown",
                song.getLink(),
                song.getImgLink(),
                song.getLikeSong(),
                playlistIds
        );
    }

    @Override
    public String deleteById(Long id) {
    	Optional<Song> optionalSong = songRepository.findById(id);
        if (optionalSong.isPresent()) {
        	Song song = optionalSong.get();

            cloudinaryService.deleteFromCloudinary(song.getSongID(), "video");
            cloudinaryService.deleteFromCloudinary(song.getImageID(), "image");

            for (Playlist playlist : song.getPlaylists()) {
                playlist.getSongs().remove(song);
            }
            playlistRepository.saveAll(song.getPlaylists());
            songRepository.deleteById(id);
            return "Song is deleted";
        }
        return "Song not found";
    }

    @Override
    public SongDTO findById(Long id) {
    	Optional<Song> optionalSong = songRepository.findById(id);
    	if(optionalSong.isEmpty()) return null;
    	else return convertToDTO(optionalSong.get());
    }

	@Override
	public List<SongDTO> getPlaylistSong(Long id) {
		Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
		if(optionalPlaylist.isEmpty()) return null;
		Playlist playlist = optionalPlaylist.get();
		List<SongDTO> songs = playlist.getSongs().stream().map(this::convertToDTO).toList();
		return songs;
	}
    
    
}

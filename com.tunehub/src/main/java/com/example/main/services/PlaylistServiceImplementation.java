package com.example.main.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.main.dto.PlaylistDTO;
import com.example.main.entity.Playlists;
import com.example.main.entity.Songs;
import com.example.main.entity.Users;
import com.example.main.repository.PlaylistRepository;
import com.example.main.repository.SongRepository;
import com.example.main.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImplementation implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SongRepository songRepository;
    
	@Autowired
	Cloudinary cloudinary;
	
	@Autowired
	private ObjectMapper objectMapper;
    
	@Override
	public String createPlaylist(String name, String type, Long userId, MultipartFile image, String songsJson) {
	    if (name == null || type == null || image == null || songsJson == null)
	        return "Invalid input data";

	    Optional<Users> userOptional = usersRepository.findById(userId);
	    if (userOptional.isEmpty()) return "User not found";

	    String imageUrl = null, publicId = null;

	    try {
	        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
	        imageUrl = (String) uploadResult.get("secure_url");
	        publicId = (String) uploadResult.get("public_id");

	        Set<Long> songIds = objectMapper.readValue(songsJson, new TypeReference<>() {});
	        Set<Songs> songs = new HashSet<>(songRepository.findAllById(songIds));

	        if (songs.isEmpty()) {
	            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
	            return "No valid songs found";
	        }

	        Playlists playlist = new Playlists();
	        playlist.setName(name);
	        playlist.setType(type);
	        playlist.setUser(userOptional.get());
	        playlist.setImgLink(imageUrl);
	        playlist.setImageId(publicId);
	        playlist.setSongs(songs);

	        playlistRepository.save(playlist);

	        return "Playlist created successfully";

	    } catch (Exception e) {
	        if (publicId != null) {
	            try {
	                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }

	        throw new RuntimeException("Failed to create playlist: " + e.getMessage(), e);
	    }
	}


    @Override
    public String updatePlaylist(Long playlistId, String name, String type, MultipartFile image, String songsJson) throws Exception {
        Optional<Playlists> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isEmpty()) {
            return "Playlist not found!";
        }

        Playlists existingPlaylist = optionalPlaylist.get();

        if (image != null && !image.isEmpty()) {
            try {
                if (existingPlaylist.getImageId() != null) {
                    cloudinary.uploader().destroy(existingPlaylist.getImageId(), ObjectUtils.emptyMap());
                }

                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("secure_url");
                String publicId = (String) uploadResult.get("public_id");

                existingPlaylist.setImgLink(imageUrl);
                existingPlaylist.setImageId(publicId);
            } catch (Exception e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        if (name != null) existingPlaylist.setName(name);
        if (type != null) existingPlaylist.setType(type);

        if (songsJson != null && !songsJson.isBlank()) {
            try {
                Set<Long> songIds = objectMapper.readValue(songsJson, new TypeReference<>() {});
                Set<Songs> updatedSongs = new HashSet<>(songRepository.findAllById(songIds));
                existingPlaylist.setSongs(updatedSongs);
            } catch (Exception e) {
                return "Invalid song data!";
            }
        }

        playlistRepository.save(existingPlaylist);
        return "Playlist updated successfully!";
    }


    @Override
    public List<PlaylistDTO> getUserPlaylists(Long userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isEmpty()) return Collections.emptyList();

        Users user = userOptional.get();
        List<Playlists> playlists = playlistRepository.findByUser(user);

        return playlists.stream().map(playlist -> {
            List<Long> songIds = playlist.getSongs().stream()
                    .map(Songs::getId).collect(Collectors.toList());
            return new PlaylistDTO(
                    playlist.getId(),
                    playlist.getName(),
                    playlist.getType(),
                    playlist.getImgLink(),
                    user.getId(),
                    songIds
            );
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<PlaylistDTO> getAllPlaylists(){
    	List<Playlists> playlists = playlistRepository.findAll();
    	return playlists.stream().map(playlist -> {
            List<Long> songIds = playlist.getSongs().stream()
                    .map(Songs::getId).collect(Collectors.toList());
            return new PlaylistDTO(
                    playlist.getId(),
                    playlist.getName(),
                    playlist.getType(),
                    playlist.getImgLink(),
                    playlist.getUser().getId(),
                    songIds
            );
        }).collect(Collectors.toList());
    }
    
    
    @Override
    public String removeSongFromPlaylist(Long playlistId, Long songId) {
        Optional<Playlists> optionalPlaylist = playlistRepository.findById(playlistId);
        Optional<Songs> optionalSong = songRepository.findById(songId);

        if (optionalPlaylist.isEmpty() || optionalSong.isEmpty()) {
            return "Playlist or Song not found.";
        }

        Playlists playlist = optionalPlaylist.get();
        Songs song = optionalSong.get();

        if (!playlist.getSongs().contains(song)) {
            return "Song not found in the playlist.";
        }

        playlist.getSongs().remove(song);
        song.getPlaylists().remove(playlist); 

        playlistRepository.save(playlist); 

        return "Song removed from playlist successfully.";
    }
    
    @Override
    public String addSongToPlaylist(Long playlistId, Long songId) {
        Optional<Playlists> optionalPlaylist = playlistRepository.findById(playlistId);
        Optional<Songs> optionalSong = songRepository.findById(songId);

        if (optionalPlaylist.isEmpty() || optionalSong.isEmpty()) {
            return "Playlist or Song not found!";
        }

        Playlists playlist = optionalPlaylist.get();
        Songs song = optionalSong.get();

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            song.getPlaylists().add(playlist);
            playlistRepository.save(playlist);
            return "Song added to playlist successfully!";
        }

        return "Song already exists in playlist!";
    }

    @Override
    public String deletePlaylist(Long playlistId) {
        Optional<Playlists> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isEmpty()) return "Playlist not found";

        Playlists playlist = optionalPlaylist.get();

        if (playlist.getImageId() != null) {
            try {
                cloudinary.uploader().destroy(playlist.getImageId(), ObjectUtils.emptyMap());
            } catch (IOException e) {
                System.err.println("Failed to delete image from Cloudinary: " + e.getMessage());
            }
        }

        playlistRepository.delete(playlist);
        return "Playlist deleted successfully";
    }
    
    @Override
    public List<PlaylistDTO> getPlaylistsByAdmin(){
    	List<Playlists> adminPlaylists = playlistRepository.findByUserRole("ADMIN");
    	return adminPlaylists.stream().map(playlist -> {
            List<Long> songIds = playlist.getSongs().stream()
                    .map(Songs::getId).collect(Collectors.toList());
            return new PlaylistDTO(
                    playlist.getId(),
                    playlist.getName(),
                    playlist.getType(),
                    playlist.getImgLink(),
                    playlist.getUser().getId(),
                    songIds
            );
        }).collect(Collectors.toList());
    }
    
    @Override
    public boolean deleteUserWithPlaylists(Long userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isEmpty()) return false;

        Users user = userOptional.get();
        List<Playlists> playlists = playlistRepository.findByUser(user);

        for (Playlists playlist : playlists) {
            deletePlaylist(playlist.getId());
        }

        return true;
    }

}

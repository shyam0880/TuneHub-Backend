package com.example.main.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.main.dto.PlaylistDTO;
import com.example.main.dto.SongDTO;
import com.example.main.entity.Playlist;
import com.example.main.entity.Song;
import com.example.main.repository.PlaylistRepository;
import com.example.main.repository.SongRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PlaylistServiceImplementation implements PlaylistService{

	@Autowired
	PlaylistRepository playlistRepository;
	
	@Autowired
	SongRepository songRepository;
	
	@Autowired
	SongService songService;
	
	@Autowired
	Cloudinary cloudinary;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
	public String addPlaylist(String name, String type, MultipartFile image, String songsJson) throws Exception {
		Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());

        String imageUrl = (String) uploadResult.get("secure_url"); 
        String publicId = (String) uploadResult.get("public_id"); 
        
     // ✅ Convert JSON string (songs) to List
        ObjectMapper objectMapper = new ObjectMapper();
        List<Song> songs = objectMapper.readValue(songsJson, new TypeReference<List<Song>>() {});
        
        Playlist newPlaylist = new Playlist();
        newPlaylist.setName(name);
        newPlaylist.setType(type);
        newPlaylist.setImgLink(imageUrl); // ✅ Store Image URL
        newPlaylist.setImageId(publicId);   // ✅ Store Image Public ID (for deletion)
        newPlaylist.setSongs(songs);
        
		playlistRepository.save(newPlaylist);
		
		for (Song song : songs) {
        	songService.updateSong(song); 
        }
		return "Playlist added successfully!";
	}
	
	public PlaylistDTO findById(int id) {
	    Playlist playlist = playlistRepository.findById(id);
	    return convertToPlaylistDTO(playlist);
	}
	
	@Override
	public String addSongToPlaylist(int playlistId, int songId) {
	    Playlist playlist = playlistRepository.findById(playlistId);
	    Song song = songRepository.findById(songId);

	    if (playlist == null || song == null) {
	        return "Playlist or Song not found!";
	    }

	    if (!playlist.getSongs().contains(song)) {
	        playlist.getSongs().add(song);
	        song.getPlaylists().add(playlist);
	        playlistRepository.save(playlist);
	        return "Song added to playlist successfully!";
	    }

	    return "Song already exists in playlist!";
	}


	@Override
	public String updatePlaylist(int id, String name, String type, MultipartFile image, String songsJson) throws Exception {
	    Playlist existingPlaylist = playlistRepository.findById(id);

	    if (existingPlaylist == null) {
	        return "Playlist not found!";
	    }

	    // Upload new image if provided
	    if (image != null && !image.isEmpty()) {
	        // Delete old image
	        cloudinary.uploader().destroy(existingPlaylist.getImageId(), ObjectUtils.emptyMap());

	        // Upload new one
	        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
	        String imageUrl = (String) uploadResult.get("secure_url");
	        String publicId = (String) uploadResult.get("public_id");

	        existingPlaylist.setImgLink(imageUrl);
	        existingPlaylist.setImageId(publicId);
	    }

	    existingPlaylist.setName(name);
	    existingPlaylist.setType(type);

	    // ✅ Update songs
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<Song> updatedSongs = objectMapper.readValue(songsJson, new TypeReference<List<Song>>() {});
	    existingPlaylist.setSongs(updatedSongs);

	    playlistRepository.save(existingPlaylist);

	    for (Song song : updatedSongs) {
	        songService.updateSong(song);
	    }

	    return "Playlist updated successfully!";
	}


	private PlaylistDTO convertToPlaylistDTO(Playlist playlist) {
	    List<SongDTO> songDTOs = convertSongsToDTOs(playlist.getSongs(), List.of(playlist));
	    return new PlaylistDTO(
	        playlist.getId(),
	        playlist.getName(),
	        playlist.getType(),
	        playlist.getImgLink(),
	        songDTOs
	    );
	}

	@SuppressWarnings("null")
	private List<SongDTO> convertSongsToDTOs(List<Song> songs, List<Playlist> playlists) {
	    return songs.stream()
	        .map(song -> new SongDTO(
	            song.getId(),
	            song.getName(),
	            song.getGenre(),
	            song.getArtist() != null ? song.getArtist().getId() : null,  
	            song.getArtist() != null ? song.getArtist().getName() : "Unknown", 
	            song.getLink(),
	            song.getImgLink(),
	            song.getLikeSong(),
	            getPlaylistIdsContainingSong(song, playlists)
	        ))
	        .collect(Collectors.toList());
	}


	private List<Integer> getPlaylistIdsContainingSong(Song song, List<Playlist> playlists) {
	    return playlists.stream()
	        .filter(p -> p.getSongs().contains(song))
	        .map(Playlist::getId)
	        .collect(Collectors.toList());
	}

	

	@Override
	public List<PlaylistDTO> fetchAllPlaylist() {
	    List<Playlist> playlists = playlistRepository.findAllPlaylistsWithSongs();

	    return playlists.stream()
	        .map(playlist -> {
	            List<SongDTO> songDTOs = playlist.getSongs().stream()
	                .map(song -> new SongDTO(
	                    song.getId(),
	                    song.getName(),
	                    song.getGenre(),
	                    song.getArtist().getId(), 
	                    song.getArtist().getName(),
	                    song.getLink(),
	                    song.getImgLink(),
	                    song.getLikeSong(),
	                    playlists.stream()
	                        .filter(p -> p.getSongs().contains(song))
	                        .map(Playlist::getId)
	                        .collect(Collectors.toList())
	                ))
	                .collect(Collectors.toList());

	            return new PlaylistDTO(
	                playlist.getId(),
	                playlist.getName(),
	                playlist.getType(),
	                playlist.getImgLink(),
	                songDTOs
	            );
	        })
	        .collect(Collectors.toList());
	}


	public boolean existById(int id) {
		return playlistRepository.existsById(id);
	}

	@Override
	public String deleteById(int id) throws Exception{
		Playlist playlist = playlistRepository.findById(id);
		String publicId = playlist.getImageId();

		
		 // ✅ Delete from Cloudinary
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
		playlistRepository.deleteById(id);
		return "Playlist delete Successfully";
	}
	
	public String removeSongFromPlaylist(int playlistId, int songId) {
        Playlist playlist = playlistRepository.findById(playlistId);
        Song song = songRepository.findById(songId);

        if (playlistRepository.existsById(playlistId) && songRepository.existsById(songId)) {

            if (playlist.getSongs().contains(song)) {
                playlist.getSongs().remove(song); 
                song.getPlaylists().remove(playlist);  
                
                playlistRepository.save(playlist); 
                return "Song removed from playlist successfully.";
            } else {
                return "Song not found in the playlist.";
            }
        }
        return "Playlist or Song not found.";
    }


}

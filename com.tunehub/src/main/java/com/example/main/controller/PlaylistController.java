package com.example.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.main.dto.PlaylistDTO;
import com.example.main.entity.Playlist;
import com.example.main.entity.Song;
import com.example.main.services.PlaylistService;
import com.example.main.services.SongService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.*;


@RestController
public class PlaylistController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
		
	
//	public String addToPlaylist(@RequestBody Playlist playlist) {
	@PostMapping("/addToPlaylist")
	public ResponseEntity<?> addPlaylist(
	            @RequestParam("name") String name,
	            @RequestParam("type") String type,
	            @RequestParam("image") MultipartFile image,  // Accept file
	            @RequestParam("songs") String songsJson) {
		try {
			String message = playlistService.addPlaylist(name, type, image, songsJson);
            return ResponseEntity.ok(message);

		}
		catch(Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading playlist");
		}

    }
	
	// ✅ 2. Update Playlist
    @PutMapping("/updatePlaylist/{id}")
    public ResponseEntity<String> updatePlaylist(
            @PathVariable int id,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("songs") String songsJson) {
        try {
            String response = playlistService.updatePlaylist(id, name, type, image, songsJson);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
	
	@GetMapping("/viewPlaylist")
	public List<PlaylistDTO> viewPlaylist() {
		List<PlaylistDTO> playlist = playlistService.fetchAllPlaylist();
		return playlist;
	}
	
	@DeleteMapping("/deletePlaylist/{id}")
	public ResponseEntity<String> deletePlaylist(@PathVariable int id) {
		try {
			if(playlistService.existById(id)) {
				String message = playlistService.deleteById(id);
				return ResponseEntity.ok(message);
			}
			else {
				return ResponseEntity.ok("Playlist not deleted");
			}
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting playlist");
		}
		
	}
	
	@GetMapping("/viewPlaylistById/{id}")
	public PlaylistDTO viewPlaylistById(@PathVariable int id) {
		PlaylistDTO playlistDTO = playlistService.findById(id);
		return playlistDTO;
	}
	
	@DeleteMapping("/playlist/{playlistId}/songs/{songId}")
    public ResponseEntity<String> removeSongFromPlaylist(
            @PathVariable int playlistId,
            @PathVariable int songId) {
		try {
			String message = playlistService.removeSongFromPlaylist(playlistId, songId);
			return ResponseEntity.ok(message);			
		}
		catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting playlist");
		}

    }
	
	// ✅ 7. Add Song to Playlist
    @PostMapping("/{playlistId}/add-song/{songId}")
    public ResponseEntity<String> addSongToPlaylist(
            @PathVariable int playlistId,
            @PathVariable int songId) {
        String response = playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok(response);
    }
	
	
	

}

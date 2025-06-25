package com.example.main.controller;

import com.example.main.dto.PlaylistDTO;
import com.example.main.services.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@Tag(name = "Playlist Controller", description = "Endpoints for playlist management")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Operation(
            summary = "Create new playlist",
            description = "Create a playlist for the logged-in user with songs."
    )   
    @PostMapping
	public ResponseEntity<?> createPlaylist(
	            @RequestParam("name") String name,
	            @RequestParam("type") String type,
	            @RequestParam("userId") Long userId,
	            @RequestParam("image") MultipartFile image,  // Accept file
	            @RequestParam("songs") String songsJson)  throws Exception  {
		try {
			String message = playlistService.createPlaylist(name, type, userId, image, songsJson);
            return ResponseEntity.ok(message);
		}
		catch(Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading playlist");
		}
    }

    @Operation(
            summary = "Fetch all playlists by user",
            description = "Fetch all playlists for a particular user."
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPlaylists(@PathVariable Long userId) {
        List<PlaylistDTO> playlists = playlistService.getUserPlaylists(userId);
        return ResponseEntity.ok( playlists);
    }
    
    @Operation(
            summary = "Fetch all playlists",
            description = "Fetch all the details of playlists for a Admin."
    )
    @GetMapping
    public ResponseEntity<?> fetchAllPlaylist(){
    	List<PlaylistDTO> playlists = playlistService.getAllPlaylists();
    	return ResponseEntity.ok( playlists);
    }
    
    @Operation(
    		summary = "Fetch all playlists from admin",
    		description = "Fetch all the details of playlists created by Admin."
    		)
    @GetMapping("/admin")
    public ResponseEntity<?> fetchPlaylistByUserRole(){
    	List<PlaylistDTO> playlists = playlistService.getPlaylistsByAdmin();
    	return ResponseEntity.ok( playlists);
    }

    @Operation(
            summary = "Update playlists",
            description = "Update all the details of playlists for a particular user."
    )
    @PutMapping("/updatePlaylist/{id}")
    public ResponseEntity<String> updatePlaylist(
            @PathVariable Long id,
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
    
    @Operation(
            summary = "Remove Songs from playlists",
            description = "Remove Songs from playlists."
    )
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<String> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        String result = playlistService.removeSongFromPlaylist(playlistId, songId);

        HttpStatus status = result.equals("Song removed from playlist successfully.") 
            ? HttpStatus.OK 
            : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(result, status);
    }
    
	@Operation(
	    summary = "Add song to playlist",
	    description = "Adds a song to a specified playlist"
	)
	@PutMapping("/{playlistId}/songs/{songId}")
	public ResponseEntity<String> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
	    String result = playlistService.addSongToPlaylist(playlistId, songId);

	    HttpStatus status = result.equals("Song added to playlist successfully!")
	        ? HttpStatus.OK
	        : HttpStatus.BAD_REQUEST;

	    return new ResponseEntity<>(result, status);
	}

    
    @Operation(
            summary = "Delete playlist",
            description = "Delete a playlist by playlist ID."
    )
    @DeleteMapping("/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long playlistId) {
        String message = playlistService.deletePlaylist(playlistId);
        if (message.equals("Playlist deleted successfully")) {
            return ResponseEntity.ok(message); 
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message); 
        }
    }
    
}

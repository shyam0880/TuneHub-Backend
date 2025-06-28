package com.example.main.controller;

import com.example.main.dto.SongDTO;
import com.example.main.services.SongService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/songs")
@Tag(name = "Song Management", description = "Operations related to managing songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Operation(summary = "Add a new song", description = "Add a new song with audio and image files (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Song added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addSong(
            @RequestParam("name") String name,
            @RequestParam("genre") String genre,
            @RequestParam(value = "artistId", required = false) Long artistId,
            @RequestParam("songFile") MultipartFile songFile,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            String result = songService.addSong(name, genre, artistId, songFile, imageFile);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    
    @Operation(summary = "Get dashboard data", description = "Fetches all songs and artists in a dashboard-friendly format")
    @GetMapping("/data")
    public ResponseEntity<?> getDashboardData(){
    	Map<String,Object> data = songService.getDashData();
    	List<?> songs = (List<?>) data.get("songs");
	    List<?> artists = (List<?>) data.get("artists");

	    if (songs.isEmpty() && artists.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No data present");
	    }
    	return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get all songs", description = "Returns a list of all songs")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of songs")
    @GetMapping
    public ResponseEntity<?> getAllSongs() {
        List<SongDTO> songs = songService.fetchAllSongs();
        return ResponseEntity.ok(songs);
    }
    
    @Operation(summary = "Get song by ID", description = "Fetch a single song by its ID")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Song found"),
    		@ApiResponse(responseCode = "404", description = "Song not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getSong(@PathVariable Long id){
    	if(songService.findById(id)==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
    	else return ResponseEntity.ok(songService.findById(id));
    	
    }

    @Operation(summary = "Update a song", description = "Update song metadata or files (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Song updated successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("genre") String genre,
            @RequestParam(value = "artistId", required = false) Long artistId,
            @RequestParam(value = "songFile", required = false) MultipartFile songFile,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            String result = songService.updateSong(id, name, genre, artistId, songFile, imageFile);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    
    @Operation(summary = "Get songs by playlist ID", description = "Fetch all songs in a specific playlist")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Songs fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Playlist not found")
    })
    @GetMapping("/playlistId/{id}")
    public ResponseEntity<?> getSongByPlaylistId(@PathVariable Long id) {
    	List<SongDTO> songs = songService.getPlaylistSong(id);
    	if(songs==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not exist");
    	return ResponseEntity.ok(songs);
    }
    
    @Operation(summary = "Delete a song", description = "Delete a song by its ID (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Song deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Song not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        String result = songService.deleteById(id);
        return ResponseEntity.ok(result);
    }
}

package com.example.main.controller;

import com.example.main.dto.SongDTO;
import com.example.main.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

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

    @GetMapping
    public ResponseEntity<?> getAllSongs() {
        List<SongDTO> songs = songService.fetchAllSongs();
        return ResponseEntity.ok(songs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getSong(@PathVariable Long id){
    	if(songService.findById(id)==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
    	else return ResponseEntity.ok(songService.findById(id));
    	
    }

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
    
    @GetMapping("/playlistId/{id}")
    public ResponseEntity<?> getSongByPlaylistId(@PathVariable Long id) {
    	List<SongDTO> songs = songService.getPlaylistSong(id);
    	if(songs==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not exist");
    	return ResponseEntity.ok(songs);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        String result = songService.deleteById(id);
        return ResponseEntity.ok(result);
    }
}

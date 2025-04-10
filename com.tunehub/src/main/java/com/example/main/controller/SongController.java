package com.example.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.SongDTO;
import com.example.main.entity.Song;
import com.example.main.services.CloudinaryService;
import com.example.main.services.SongService;

import io.jsonwebtoken.io.IOException;


@RestController
public class SongController {
	
	@Autowired
	SongService songService;
	
	@Autowired
	CloudinaryService cloudinaryService;
	
	//Adding song
	@PostMapping("/addSong")
    public ResponseEntity<String> addSong(
        @RequestParam("name") String name,
        @RequestParam("genre") String genre,
        @RequestParam(value = "artistId", required = false) Integer artistId,
        @RequestParam("songFile") MultipartFile songFile,
        @RequestParam("imageFile") MultipartFile imageFile) throws Exception {

        try {
            String result = songService.addSong(name, genre, artistId, songFile, imageFile);

            if ("Song Added Successfully".equals(result)) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }
	
	//View all song
	@GetMapping("/displayAllSongs")
	public List<SongDTO> displaySongs() {
		List<SongDTO> songsList = songService.fetchAllSongs();
		return songsList;
	}
	
	//Song update
	@PutMapping("/songupdate/{id}")
    public ResponseEntity<String> updateSong(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("genre") String genre,
            @RequestParam(value = "artistId", required = false) Integer artistId,
            @RequestParam(value = "songFile", required = false) MultipartFile songFile,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        try {
            String result = songService.updateSong(id, name, genre, artistId, songFile, imageFile);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update song: " + e.getMessage());
        }
    }
	
	//Deleting song
	@DeleteMapping("/deleteById/{id}")
	public String deleteById(@PathVariable int id) {
		return songService.deleteById(id);
	}
	

}

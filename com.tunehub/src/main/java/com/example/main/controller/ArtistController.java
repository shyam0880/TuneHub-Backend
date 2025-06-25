package com.example.main.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.ArtistDTO;
import com.example.main.services.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public ResponseEntity<?> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable Long id) {
        ArtistDTO artist = artistService.getArtistById(id);
        if (artist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Artist not found");
        }
        return ResponseEntity.ok(artist);
    }

    @PostMapping
    public ResponseEntity<?> addArtist(
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        ArtistDTO artist = artistService.addArtist(name, image);
        return new ResponseEntity<>( artist, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArtist(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MultipartFile image) {
        
        Map<Boolean, Object> data = artistService.updateArtist(id, name, image);
        
        if (data.containsKey(false)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(data.get(false));
        }

        return ResponseEntity.ok(data.get(true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id) {
        boolean deleted = artistService.deleteArtist(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Artist not found");
        }
        return ResponseEntity.ok("Artist deleted successfully");
    }
}

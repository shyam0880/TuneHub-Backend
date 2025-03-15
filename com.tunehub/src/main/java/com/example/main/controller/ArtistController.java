package com.example.main.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.ArtistDTO;
import com.example.main.entity.Artist;
import com.example.main.services.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {
	
    @Autowired
    private ArtistService artistService;
    
    @GetMapping
    public List<ArtistDTO> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ArtistDTO getArtistById(@PathVariable int id) {
        return artistService.getArtistById(id);
    }

    @PostMapping
    public ResponseEntity<Artist> addArtist(@RequestParam("name") String name,
                                            @RequestParam(value = "image", required = false) MultipartFile image) {
        Artist artist = artistService.addArtist(name, image);
        return new ResponseEntity<>(artist, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable int id,
                                               @RequestParam("name") String name,
                                               @RequestParam(value = "image", required = false) MultipartFile image) {
        Artist updatedArtist = artistService.updateArtist(id, name, image);
        return ResponseEntity.ok(updatedArtist);
    }
    
    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable int id) {
        artistService.deleteArtist(id);
    }

}

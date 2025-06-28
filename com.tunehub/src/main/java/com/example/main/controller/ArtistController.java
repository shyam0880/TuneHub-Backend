package com.example.main.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.ArtistDTO;
import com.example.main.services.ArtistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/artists")
@Tag(name = "Artist Management", description = "Endpoints for managing artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Operation(summary = "Get all artists", description = "Returns a list of all artists")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of artists")
    @GetMapping
    public ResponseEntity<?> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @Operation(summary = "Get artist by ID", description = "Returns a single artist by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Artist found"),
        @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable Long id) {
        ArtistDTO artist = artistService.getArtistById(id);
        if (artist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Artist not found");
        }
        return ResponseEntity.ok(artist);
    }

    @Operation(summary = "Add a new artist", description = "Creates a new artist with an optional image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Artist created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addArtist(
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        ArtistDTO artist = artistService.addArtist(name, image);
        return new ResponseEntity<>( artist, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing artist", description = "Updates the name and/or image of an artist")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Artist updated successfully"),
        @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
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

    @Operation(summary = "Delete an artist", description = "Deletes an artist by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Artist deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
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

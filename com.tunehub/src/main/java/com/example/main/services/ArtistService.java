package com.example.main.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.ArtistDTO;
import com.example.main.entity.Artist;

public interface ArtistService {

	List<ArtistDTO> getAllArtists();

    ArtistDTO getArtistById(int id);

    Artist addArtist(String name, MultipartFile image);

    Artist updateArtist(int id, String name, MultipartFile image);
    
    void deleteArtist(int id);
}

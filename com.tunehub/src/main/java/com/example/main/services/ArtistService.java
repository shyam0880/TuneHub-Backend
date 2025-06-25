package com.example.main.services;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.ArtistDTO;

public interface ArtistService {

	List<ArtistDTO> getAllArtists();

    ArtistDTO getArtistById(Long id);

    ArtistDTO addArtist(String name, MultipartFile image);

    Map<Boolean, Object> updateArtist(Long id, String name, MultipartFile image);
    
    boolean deleteArtist(Long id);
}


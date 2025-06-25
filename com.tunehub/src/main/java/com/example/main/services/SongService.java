package com.example.main.services;

import com.example.main.dto.SongDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService {
    String addSong(String name, String genre, Long artistId, MultipartFile songFile, MultipartFile imageFile) throws Exception;
    String updateSong(Long songId, String name, String genre, Long artistId, MultipartFile songFile, MultipartFile imageFile) throws Exception;
    List<SongDTO> fetchAllSongs();
    String deleteById(Long id);
    SongDTO findById(Long id);
    List<SongDTO> getPlaylistSong(Long id);
}

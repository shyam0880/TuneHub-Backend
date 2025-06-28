package com.example.main.services;

import com.example.main.dto.SongDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SongService {
	Map<String,Object> getDashData();
    String addSong(String name, String genre, Long artistId, MultipartFile songFile, MultipartFile imageFile) throws Exception;
    String updateSong(Long songId, String name, String genre, Long artistId, MultipartFile songFile, MultipartFile imageFile) throws Exception;
    List<SongDTO> fetchAllSongs();
    String deleteById(Long id);
    SongDTO findById(Long id);
    List<SongDTO> getPlaylistSong(Long id);
}

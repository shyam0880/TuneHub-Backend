package com.example.main.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.SongDTO;
import com.example.main.entity.Song;

public interface SongService {

//	public void addSong(Song song);
	public String addSong(String name, String genre, Integer artistId, MultipartFile songFile,  MultipartFile imageFile)  throws Exception;
	
	public boolean songExist(int id);
	
	public SongDTO findByIdDTO(int id);
	
	public Song findById(int id);

	public List<SongDTO> fetchAllSongs();
	
	public boolean songExists(String name);

	public String updateSong(int songId, String name, String genre, Integer artistId, MultipartFile newSongFile, MultipartFile newImageFile) throws Exception;
	
	public String deleteById(int id);

	public void updateSong(Song song);

}

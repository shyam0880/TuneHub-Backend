package com.example.main.services;

import java.util.List;

import com.example.main.dto.SongDTO;
import com.example.main.entity.Song;

public interface SongService {

	public void addSong(Song song);
	
	public boolean songExist(int id);
	
	public SongDTO findByIdDTO(int id);
	
	public Song findById(int id);

	public List<SongDTO> fetchAllSongs();
	
	public boolean songExists(String name);

	public void updateSong(Song song);
	
	public String deleteById(int id);

}

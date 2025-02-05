package com.example.main.services;

import java.util.List;

import com.example.main.dto.PlaylistDTO;
import com.example.main.entity.Playlist;

public interface PlaylistService {

	public void addPlaylist(Playlist playlist);

	public List<PlaylistDTO> fetchAllPlaylist();
	
	public PlaylistDTO findById(int id);
	
	public boolean existById(int id);
	
	public void deleteById(int id);

	public String removeSongFromPlaylist(int playlistId, int songId);

}

package com.example.main.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.main.dto.PlaylistDTO;
//import com.example.main.entity.Playlist;

public interface PlaylistService {

	String addPlaylist(String name, String type, MultipartFile image, String songsJson) throws Exception;

	List<PlaylistDTO> fetchAllPlaylist();
	
	PlaylistDTO findById(int id);
	
	boolean existById(int id);
	
	String deleteById(int id) throws Exception;

	String removeSongFromPlaylist(int playlistId, int songId);

	String updatePlaylist(int id, String name, String type, MultipartFile image, String songsJson) throws Exception;

	String addSongToPlaylist(int playlistId, int songId);

}

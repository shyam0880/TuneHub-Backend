package com.example.main.services;

import java.util.List;

import com.example.main.entity.Playlist;

public interface PlaylistService {

	public void addPlaylist(Playlist playlist);

	public List<Playlist> fetchAllPlaylist();

}

package com.example.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.entity.Playlist;
import com.example.main.repository.PlaylistRepository;

@Service
public class PlaylistServiceImplementation implements PlaylistService{

	@Autowired
	PlaylistRepository repo;
	
	@Override
	public void addPlaylist(Playlist playlist) {
		repo.save(playlist);
		
	}

	@Override
	public List<Playlist> fetchAllPlaylist() {
		return repo.findAll();
	}

}

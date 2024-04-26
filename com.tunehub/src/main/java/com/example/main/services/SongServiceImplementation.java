package com.example.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.entity.Song;
import com.example.main.repository.SongRepository;

@Service
public class SongServiceImplementation implements SongService{

	@Autowired
	SongRepository repo;
	
	@Override
	public void addSong(Song song) {
		repo.save(song);
		
	}

	@Override
	public List<Song> fetchAllSongs() {
		return repo.findAll();
	}

	@Override
	public boolean songExists(String name) {
		if(repo.findByName(name)==null)
		{
			return false;			
		}
		else {
			return true;
		}
	}

	@Override
	public void updateSong(Song song) {
		repo.save(song);
	}

}

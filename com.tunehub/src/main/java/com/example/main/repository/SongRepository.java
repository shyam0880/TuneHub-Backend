package com.example.main.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Artist;
import com.example.main.entity.Song;

public interface SongRepository extends JpaRepository<Song, Integer>{

	Song findByName(String name);
	
	boolean existsByName(String name);
	
	Song findById(int id);

	List<Song> findByArtist(Artist artist);

}

package com.example.main.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Artist;
import com.example.main.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long>{

	Song findByName(String name);
	
	boolean existsByName(String name);

	List<Song> findByArtist(Artist artist);

}

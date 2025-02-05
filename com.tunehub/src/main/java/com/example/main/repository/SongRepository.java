package com.example.main.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Song;

public interface SongRepository extends JpaRepository<Song, Integer>{

	Song findByName(String name);
	
	Song findById(int id);

}

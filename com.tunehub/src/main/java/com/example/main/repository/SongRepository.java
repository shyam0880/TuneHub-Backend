package com.example.main.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Artist;
import com.example.main.entity.Songs;

public interface SongRepository extends JpaRepository<Songs, Long>{

	Songs findByName(String name);
	
	boolean existsByName(String name);

	List<Songs> findByArtist(Artist artist);
	
	List<Songs> findTop5ByOrderByIdDesc();

}

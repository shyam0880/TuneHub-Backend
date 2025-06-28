package com.example.main.repository;

import com.example.main.entity.Artist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

	Optional<Artist> findByName(String name);
	
	List<Artist> findTop8ByOrderByIdDesc();

}

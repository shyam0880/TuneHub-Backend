package com.example.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.main.entity.Playlist;

public interface PlaylistRepository	extends JpaRepository<Playlist, Integer>
{
	@Query("SELECT p FROM Playlist p LEFT JOIN FETCH p.songs")
	List<Playlist> findAllPlaylistsWithSongs();
	
	Playlist findById(int id);

}

package com.example.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Playlist;

public interface PlaylistRepository 
					extends JpaRepository<Playlist, Integer>
{

}

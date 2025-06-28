package com.example.main.repository;

import com.example.main.entity.Playlists;
import com.example.main.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlists, Long> {
    List<Playlists> findByUser(Users user);
    
    List<Playlists> findByUserRole(String role);
    
    void deleteAllByUser(Users user);

}

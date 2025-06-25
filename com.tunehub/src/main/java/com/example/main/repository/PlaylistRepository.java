package com.example.main.repository;

import com.example.main.entity.Playlist;
import com.example.main.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUser(Users user);
    
    List<Playlist> findByUserRole(String role);
    
    void deleteAllByUser(Users user);

}

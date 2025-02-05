package com.example.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.main.dto.PlaylistDTO;
import com.example.main.dto.SongDTO;
import com.example.main.entity.Playlist;
import com.example.main.entity.Song;
import com.example.main.services.PlaylistService;
import com.example.main.services.SongService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "*")
@RestController
public class PlaylistController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	
	
	@PostMapping("/addToPlaylist")
	public String addToPlaylist(@RequestBody Playlist playlist) {
		
        System.out.println("Received playlist: " + playlist);
        playlistService.addPlaylist(playlist);

        List<Song> songList = playlist.getSongs();
        for (Song song : songList) {
            songService.updateSong(song); 
        }

        return "Successfully playlist added";
    }
	
	@GetMapping("/viewPlaylist")
	public List<PlaylistDTO> viewPlaylist() {
		List<PlaylistDTO> playlist = playlistService.fetchAllPlaylist();
		return playlist;
	}
	
	@DeleteMapping("/deletePlaylist/{id}")
	public String deletePlaylist(@PathVariable int id) {
		if(playlistService.existById(id)) {
			playlistService.deleteById(id);
			return "Playlist deleted";
		}
		else {
			return "Playlist not deleted";
		}
		
	}
	
	@GetMapping("/viewPlaylistById/{id}")
	public PlaylistDTO viewPlaylistById(@PathVariable int id) {
		PlaylistDTO playlistDTO = playlistService.findById(id);
		return playlistDTO;
	}
	
	@DeleteMapping("/playlist/{playlistId}/songs/{songId}")
    public ResponseEntity<String> removeSongFromPlaylist(
            @PathVariable int playlistId,
            @PathVariable int songId) {

        String message = playlistService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.ok(message);
    }
	
	
	

}

package com.example.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.main.dto.SongDTO;
import com.example.main.entity.Song;
import com.example.main.services.SongService;

@CrossOrigin(origins = "*")
@RestController
public class SongController {
	
	@Autowired
	SongService srv;
	
	@PostMapping("/addSong")
    public ResponseEntity<String> addSong(@RequestBody Song song) {
        boolean songExists = srv.songExists(song.getName());
        
        if (!songExists) {
            srv.addSong(song);
            return ResponseEntity.ok("Song Added Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Song Already Exists");
        }
    }
	
//	@GetMapping("/viewSongs")
//	public String viewSongs(Model model) {
//		List<Song> songsList = srv.fetchAllSongs();
//		model.addAttribute("songs", songsList);		
//		return "displaySongs";
//	}
	
	@GetMapping("/displayAllSongs")
	public List<SongDTO> displaySongs() {
		List<SongDTO> songsList = srv.fetchAllSongs();
		return songsList;
	}
	
	@GetMapping("/playSongs")
	public String playSongs(Model model) {
		boolean premiumUser = true;
		if(premiumUser) {
			List<SongDTO> songsList = srv.fetchAllSongs();
			model.addAttribute("songs", songsList);		
			return "displaySongs";
		}
		else {
			return "makePayment";
		}
	}
	

}

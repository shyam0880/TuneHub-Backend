package com.example.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.main.dto.SongDTO;
import com.example.main.entity.Song;
import com.example.main.services.CloudinaryService;
import com.example.main.services.SongService;

@CrossOrigin(origins = "*")
@RestController
public class SongController {
	
	@Autowired
	SongService songService;
	
	@Autowired
	CloudinaryService cloudinaryService;
	
	@PostMapping("/addSong")
    public ResponseEntity<String> addSong(@RequestBody Song song) {
        boolean songExists = songService.songExists(song.getName());
        
        if (!songExists) {
        	songService.addSong(song);
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
		List<SongDTO> songsList = songService.fetchAllSongs();
		return songsList;
	}
	
	@GetMapping("/playSongs")
	public String playSongs(Model model) {
		boolean premiumUser = true;
		if(premiumUser) {
			List<SongDTO> songsList = songService.fetchAllSongs();
			model.addAttribute("songs", songsList);		
			return "displaySongs";
		}
		else {
			return "makePayment";
		}
	}
	
	
	@DeleteMapping("/deleteById/{id}")
	public String deleteById(@PathVariable int id) {
		return songService.deleteById(id);
	}
	

}

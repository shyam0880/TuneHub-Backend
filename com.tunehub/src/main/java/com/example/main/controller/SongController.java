package com.example.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.main.entity.Song;
import com.example.main.services.SongService;


@Controller
public class SongController {
	
	@Autowired
	SongService srv;
	
	@PostMapping("/addSong")
	public String addSong(@ModelAttribute Song song) {
		boolean songStatus = srv.songExists(song.getName());
		if(songStatus == false) {
			srv.addSong(song);
			System.out.println("Song Added Successfully");
		}
		else {
			System.out.println("Song Already Exits");
		}
		return "adminHome";
		
	}
	
	@GetMapping("/viewSongs")
	public String viewSongs(Model model) {
		List<Song> songsList = srv.fetchAllSongs();
		model.addAttribute("songs", songsList);		
		return "displaySongs";
	}
	
	@GetMapping("/playSongs")
	public String playSongs(Model model) {
		boolean premiumUser = true;
		if(premiumUser) {
			List<Song> songsList = srv.fetchAllSongs();
			model.addAttribute("songs", songsList);		
			return "displaySongs";
		}
		else {
			return "makePayment";
		}
	}
	

}

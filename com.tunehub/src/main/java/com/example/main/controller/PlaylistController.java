package com.example.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.main.entity.Playlist;
import com.example.main.entity.Song;
import com.example.main.services.PlaylistService;
import com.example.main.services.SongService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		
		List<Song> songlist = songService.fetchAllSongs();
		model.addAttribute("songs", songlist);
		return "createPlaylist";
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		//update playlist table
		playlistService.addPlaylist(playlist);	
		
		//update song table
		List<Song> Songlist = playlist.getSongs();
		for(Song s: Songlist) {
			s.getPlaylists().add(playlist);
			songService.updateSong(s);
		}
		return "adminhome";
	}
	
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {
		List<Playlist> playlist = playlistService.fetchAllPlaylist();
		model.addAttribute("playlists", playlist);
		return "displayPlaylist";
	}
	
	

}

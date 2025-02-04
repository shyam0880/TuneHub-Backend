package com.example.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


@CrossOrigin(origins = "*")
@RestController
public class PlaylistController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		
		List<SongDTO> songlist = songService.fetchAllSongs();
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
	
	

}

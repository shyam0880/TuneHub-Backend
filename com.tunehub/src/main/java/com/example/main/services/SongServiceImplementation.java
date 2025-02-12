package com.example.main.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.dto.SongDTO;
import com.example.main.entity.Playlist;
import com.example.main.entity.Song;
import com.example.main.repository.PlaylistRepository;
import com.example.main.repository.SongRepository;

@Service
public class SongServiceImplementation implements SongService{

	@Autowired
	SongRepository songRepository;
	
	@Autowired
	PlaylistRepository playlistRepository;
	
	@Override
	public void addSong(Song song) {
		songRepository.save(song);
		
	}
	
	@Override
	public boolean songExist(int id) {
		if(songRepository.existsById(id)) {
			return true;
		}
		else {
			return false;			
		}
	}

	@Override
	public SongDTO findByIdDTO(int id) {
		Song song = songRepository.findById(id);
		return convertToDTO(song);
	}
	
	public Song findById(int id) {
		return songRepository.findById(id);
	}

	@Override
	public List<SongDTO> fetchAllSongs() {
		List<Song> songs= songRepository.findAll();
		return songs.stream().map(this::convertToDTO).toList();
	}
	
	private SongDTO convertToDTO(Song song) {
        List<Integer> playlistIds = song.getPlaylists().stream()
                                        .map(Playlist::getId)
                                        .toList();

        return new SongDTO(
            song.getId(),
            song.getName(),
            song.getArtist(),
            song.getGenre(),
            song.getLink(),
            song.getImgLink(),
            song.getLikeSong(),
            playlistIds
        );
	}

	@Override
	public boolean songExists(String name) {
		if(songRepository.findByName(name)==null)
		{
			return false;			
		}
		else {
			return true;
		}
	}

	@Override
	public void updateSong(Song song) {
		songRepository.save(song);
	}

	public String deleteById(int id) {
		if(songRepository.existsById(id)) {
			Song song = songRepository.findById(id);
			
			for(Playlist playlist: song.getPlaylists()) {
				playlist.getSongs().remove(song);
			}
			playlistRepository.saveAll(song.getPlaylists());
			songRepository.deleteById(id);
			return "Songs is deleted";
		}
		else {
			return "Song not found";
		}
		
	}



}

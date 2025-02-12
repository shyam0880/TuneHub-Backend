package com.example.main.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.main.dto.PlaylistDTO;
import com.example.main.dto.SongDTO;
import com.example.main.entity.Playlist;
import com.example.main.entity.Song;
import com.example.main.repository.PlaylistRepository;
import com.example.main.repository.SongRepository;

@Service
public class PlaylistServiceImplementation implements PlaylistService{

	@Autowired
	PlaylistRepository playlistRepository;
	
	@Autowired
	SongRepository songRepository;
	
	@Override
	public void addPlaylist(Playlist playlist) {
		playlistRepository.save(playlist);
		
	}
	
	public PlaylistDTO findById(int id) {
	    Playlist playlist = playlistRepository.findById(id);
	    return convertToPlaylistDTO(playlist);
	}


	private PlaylistDTO convertToPlaylistDTO(Playlist playlist) {
	    List<SongDTO> songDTOs = convertSongsToDTOs(playlist.getSongs(), List.of(playlist));
	    return new PlaylistDTO(playlist.getId(), playlist.getName(), playlist.getType(), playlist.getImgLink(), songDTOs);
	}

	private List<SongDTO> convertSongsToDTOs(List<Song> songs, List<Playlist> playlists) {
	    return songs.stream()
	        .map(song -> new SongDTO(
	            song.getId(),
	            song.getName(),
	            song.getGenre(),
	            song.getArtist(),
	            song.getLink(),
	            song.getImgLink(),
	            song.getLikeSong(),
	            getPlaylistIdsContainingSong(song, playlists)
	        ))
	        .collect(Collectors.toList());
	}

	private List<Integer> getPlaylistIdsContainingSong(Song song, List<Playlist> playlists) {
	    return playlists.stream()
	        .filter(p -> p.getSongs().contains(song))
	        .map(Playlist::getId)
	        .collect(Collectors.toList());
	}

	
	@Override
	public List<PlaylistDTO> fetchAllPlaylist() {
		List<Playlist> playlists = playlistRepository.findAllPlaylistsWithSongs(); 
		
		List<PlaylistDTO> playlistDTOs = playlists.stream().map(playlist -> {
			
			List<SongDTO> songDTOs = playlist.getSongs().stream()
					.map(song -> new SongDTO(song.getId(),song.getName(),song.getGenre(), song.getArtist(),song.getLink(),song.getImgLink(),song.getLikeSong(),playlists.stream()
							.filter(p -> p.getSongs().contains(song)) 
							.map(Playlist::getId) 
							.collect(Collectors.toList()))) 
					.collect(Collectors.toList());
			
			return new PlaylistDTO(playlist.getId(), playlist.getName(),playlist.getType(),playlist.getImgLink(), songDTOs);
		}).collect(Collectors.toList());
		
		return playlistDTOs;
	}

	public boolean existById(int id) {
		return playlistRepository.existsById(id);
	}

	@Override
	public void deleteById(int id) {
		playlistRepository.deleteById(id);
		
	}
	
	public String removeSongFromPlaylist(int playlistId, int songId) {
        Playlist playlist = playlistRepository.findById(playlistId);
        Song song = songRepository.findById(songId);

        if (playlistRepository.existsById(playlistId) && songRepository.existsById(songId)) {

            if (playlist.getSongs().contains(song)) {
                playlist.getSongs().remove(song); 
                song.getPlaylists().remove(playlist);  
                
                playlistRepository.save(playlist); 
                return "Song removed from playlist successfully.";
            } else {
                return "Song not found in the playlist.";
            }
        }
        return "Playlist or Song not found.";
    }


}

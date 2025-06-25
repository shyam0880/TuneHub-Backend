package com.example.main.services;

import com.example.main.dto.PlaylistDTO;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface PlaylistService {
    String createPlaylist(String name,String type,Long userId,MultipartFile image,String songsJson) throws Exception;
    List<PlaylistDTO> getUserPlaylists(Long userId);
    List<PlaylistDTO> getAllPlaylists();
    List<PlaylistDTO> getPlaylistsByAdmin();
    String updatePlaylist(Long playlistId, String name, String type, MultipartFile image, String songsJson)throws Exception;
    String deletePlaylist(Long playlistId);
    String addSongToPlaylist(Long playlistId, Long songId);
    String removeSongFromPlaylist(Long playlistId, Long songId);
    boolean deleteUserWithPlaylists(Long userId);
}

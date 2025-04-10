package com.example.main.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.main.dto.SongDTO;
import com.example.main.entity.Artist;
import com.example.main.entity.Playlist;
import com.example.main.entity.Song;
import com.example.main.repository.ArtistRepository;
import com.example.main.repository.PlaylistRepository;
import com.example.main.repository.SongRepository;

@Service
public class SongServiceImplementation implements SongService{

	@Autowired
	SongRepository songRepository;
	
	@Autowired
	PlaylistRepository playlistRepository;
	
	@Autowired
	CloudinaryService cloudinaryService;
	
	@Autowired
	Cloudinary cloudinary;
	
	@Autowired
	ArtistRepository artistRepository;
	
	
	private Map<String, String> uploadFile(MultipartFile file, String resourceType) throws Exception {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", resourceType));
        return Map.of(
            "fileUrl", (String) uploadResult.get("secure_url"),
            "fileId", (String) uploadResult.get("public_id")
        );
    }
	
	@Override
	public String addSong(String name, String genre, Integer artistId, MultipartFile songFile,  MultipartFile imageFile)  throws Exception {
		if (songRepository.existsByName(name)) {
			return "Song Already Exists";
		}

    // Upload files to Cloudinary
    Map<String, String> songUpload = uploadFile(songFile, "video");
    Map<String, String> imageUpload = uploadFile(imageFile, "image");

    // Find artist (optional)
    Artist artist = artistId != null ? artistRepository.findById(artistId).orElse(null) : null;

    // Save song to database
    Song song = new Song();
    song.setName(name);
    song.setGenre(genre);
    song.setLink(songUpload.get("fileUrl"));
    song.setSongID(songUpload.get("fileId"));
    song.setImgLink(imageUpload.get("fileUrl"));
    song.setImageID(imageUpload.get("fileId"));
    song.setArtist(artist);

    songRepository.save(song);
    return "Song Added Successfully";
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
	
	@SuppressWarnings("null")
	private SongDTO convertToDTO(Song song) {
        List<Integer> playlistIds = song.getPlaylists().stream()
                                        .map(Playlist::getId)
                                        .toList();

        return new SongDTO(
            song.getId(),
            song.getName(),
            song.getGenre(),
            song.getArtist() != null ? song.getArtist().getId() : 0,  
    	    song.getArtist() != null ? song.getArtist().getName() : "Unknown",
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
	public String updateSong(int songId, String name, String genre, Integer artistId,
            MultipartFile newSongFile, MultipartFile newImageFile) throws Exception {
		
		System.out.println("i am updating");

		// 1. Fetch existing song
		Song existingSong = songRepository.findById(songId);
		if (existingSong == null) return "Song not found";
		
		// 2. Delete old files if new ones are provided
		if (newSongFile != null && !newSongFile.isEmpty()) {
		cloudinaryService.deleteFromCloudinary(existingSong.getSongID(), "video");
		Map<String, String> songUpload = uploadFile(newSongFile, "video");
		existingSong.setLink(songUpload.get("fileUrl"));
		existingSong.setSongID(songUpload.get("fileId"));
		}
		
		if (newImageFile != null && !newImageFile.isEmpty()) {
		cloudinaryService.deleteFromCloudinary(existingSong.getImageID(), "image");
		Map<String, String> imageUpload = uploadFile(newImageFile, "image");
		existingSong.setImgLink(imageUpload.get("fileUrl"));
		existingSong.setImageID(imageUpload.get("fileId"));
		}
		
		// 3. Update other fields
		existingSong.setName(name);
		existingSong.setGenre(genre);
		
		Artist artist = (artistId != null && artistId != 0)
		? artistRepository.findById(artistId).orElse(null)
		: null;
		existingSong.setArtist(artist);
		
		// 4. Save updated song
		songRepository.save(existingSong);
		
		return "Song updated successfully";
	}


	public String deleteById(int id) {
		if(songRepository.existsById(id)) {
			Song song = songRepository.findById(id);
			
			String musicPublicId = song.getSongID();
	        String imagePublicId = song.getImageID();
			
			boolean audioDeleted = cloudinaryService.deleteFromCloudinary(musicPublicId, "video");
	        boolean imageDeleted = cloudinaryService.deleteFromCloudinary(imagePublicId, "image");

	        if (audioDeleted && imageDeleted) {
	        	//remove song from playlist if exist 
				for(Playlist playlist: song.getPlaylists()) {
					playlist.getSongs().remove(song);
				}
				playlistRepository.saveAll(song.getPlaylists());
				songRepository.deleteById(id);
				return "Songs is deleted";
	        } else {
	            throw new RuntimeException("Failed to delete Cloudinary files");
	        }
		}
		else {
			return "Song not found";
		}
		
	}

	@Override
	public void updateSong(Song song) {
		songRepository.save(song);
	}



}

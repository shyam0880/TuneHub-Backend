package com.example.main.dto;

import java.util.List;

public class SongDTO {
	 private int id;
	    private String name;
	    private String artist;
	    private String genre;
	    private String link;
	    private String imgLink;
	    boolean likeSong;
	    private List<Integer> playlistIds;

	    // Default constructor
	    public SongDTO() {}

	    // Parameterized constructor
	    public SongDTO(int id, String name, String artist, String genre, String link, String imgLink,boolean likeSong, List<Integer> playlistIds) {
	        this.id = id;
	        this.name = name;
	        this.artist = artist;
	        this.genre = genre;
	        this.link = link;
	        this.imgLink = imgLink;
	        this.likeSong = likeSong;
	        this.playlistIds = playlistIds;
	    }

	    // Getters and setters
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getArtist() {
	        return artist;
	    }

	    public void setArtist(String artist) {
	        this.artist = artist;
	    }

	    public String getGenre() {
	        return genre;
	    }

	    public void setGenre(String genre) {
	        this.genre = genre;
	    }

	    public String getLink() {
	        return link;
	    }

	    public void setLink(String link) {
	        this.link = link;
	    }

	    public String getImgLink() {
	        return imgLink;
	    }

	    public void setImgLink(String imgLink) {
	        this.imgLink = imgLink;
	    }

	    public boolean getLikeSong() {
			return likeSong;
		}

		public void setLikeSong(boolean likeSong) {
			this.likeSong = likeSong;
		}
	    
	    public List<Integer> getPlaylistIds() {
	        return playlistIds;
	    }

	    public void setPlaylistIds(List<Integer> playlistIds) {
	        this.playlistIds = playlistIds;
	    }

	    @Override
	    public String toString() {
	        return "SongDTO [id=" + id + ", name=" + name + ", artist=" + artist + ", genre=" + genre + ", link=" + link
	                + ", imgLink=" + imgLink + ", playlistIds=" + playlistIds + "]";
	    }

}

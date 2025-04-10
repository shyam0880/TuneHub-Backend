package com.example.main.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String name;
	String genre;
	String link;
	String songID; //song cloudinary id
	String imgLink;
	String imageID;
	boolean likeSong;
	
	@ManyToOne
    @JoinColumn(name = "artist_id", nullable = true) // Ensures every song has an artist
    private Artist artist;
	
	@ManyToMany(mappedBy = "songs")
	List<Playlist> playlists;

	public Song() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Song(int id, String name, String genre, String link, String songID, String imgLink, String imageID,
			boolean likeSong, Artist artist, List<Playlist> playlists) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.link = link;
		this.songID = songID;
		this.imgLink = imgLink;
		this.imageID = imageID;
		this.likeSong = likeSong;
		this.artist = artist;
		this.playlists = playlists;
	}

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

	public String getSongID() {
		return songID;
	}

	public void setSongID(String songID) {
		this.songID = songID;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

	public boolean getLikeSong() {
		return likeSong;
	}

	public void setLikeSong(boolean likeSong) {
		this.likeSong = likeSong;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", genre=" + genre + ", link=" + link + ", songID=" + songID
				+ ", imgLink=" + imgLink + ", imageID=" + imageID + ", likeSong=" + likeSong + ", artist=" + artist
				+ ", playlists=" + playlists + "]";
	}
	
	

		

}

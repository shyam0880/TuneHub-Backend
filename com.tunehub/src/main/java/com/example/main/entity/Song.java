package com.example.main.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String name;
	String artist;
	String genre;
	String link;
	String imgLink;
	boolean likeSong;
	
	@ManyToMany(mappedBy = "songs")
	List<Playlist> playlists;

	public Song() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Song(int id, String name, String artist, String genre, String link,String imgLink,boolean likeSong, List<Playlist> playlists) {
		super();
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.genre = genre;
		this.link = link;
		this.imgLink=imgLink;
		this.likeSong=likeSong;
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
		this.imgLink=imgLink;
	}

	public boolean getLikeSong() {
		return likeSong;
	}

	public void setLikeSong(boolean likeSong) {
		this.likeSong = likeSong;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", artist=" + artist + ", genre=" + genre + ", link=" + link
				+ ", imgLink=" + imgLink + ", likeSong=" + likeSong + ", playlists=" + playlists + "]";
	}

		

}

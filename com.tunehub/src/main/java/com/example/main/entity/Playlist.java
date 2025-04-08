package com.example.main.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Playlist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String type;
	private String imgLink;
	private String imageId;
	
	@ManyToMany
	@JoinTable(
	    name = "playlist_song",  
	    joinColumns = @JoinColumn(name = "playlist_id"), 
	    inverseJoinColumns = @JoinColumn(name = "song_id") 
	)
	List<Song> songs;

	public Playlist() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Playlist(int id, String name, String type, String imgLink,String imageId, List<Song> songs) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.imgLink = imgLink;
		this.imageId = imageId;
		this.songs = songs;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", name=" + name + ", type=" + type + ", imgLink=" + imgLink + ", imageId="
				+ imageId + ", songs=" + songs + "]";
	}

	



	
}

package com.example.main.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String name;
	String image;
	String imageId;
	
	 @OneToMany(mappedBy = "artist")
	 private List<Song> songs = new ArrayList<>();

	public Artist() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Artist(int id, String name, String image, String imageId, List<Song> songs) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
		return "Artist [id=" + id + ", name=" + name + ", image=" + image + ", imageId=" + imageId + ", songs=" + songs
				+ "]";
	}
	 
	 
	
	

}

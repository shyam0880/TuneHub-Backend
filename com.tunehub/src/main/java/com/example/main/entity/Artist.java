package com.example.main.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	String image;
	String imageId;
	
	 @OneToMany(mappedBy = "artist")
	 private List<Songs> songs = new ArrayList<>();

	public Artist() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Artist(Long id, String name, String image, String imageId, List<Songs> songs) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.imageId = imageId;
		this.songs = songs;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getImage() { return image; }
	public void setImage(String image) { this.image = image; }

	public String getImageId() { return imageId; }
	public void setImageId(String imageId) { this.imageId = imageId; }

	public List<Songs> getSongs() { return songs; }
	public void setSongs(List<Songs> songs) { this.songs = songs; }

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", image=" + image + ", imageId=" + imageId + ", songs=" + songs
				+ "]";
	}
	 
}

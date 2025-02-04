package com.example.main.dto;

import java.util.List;


public class PlaylistDTO {
	private int id;
    private String name;
    private String type;
    private String imgLink;
    private List<SongDTO> songs; // Store only song IDs to avoid circular dependency
    
	public PlaylistDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlaylistDTO(int id, String name, String type, String imgLink, List<SongDTO> songs) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.imgLink = imgLink;
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

	public List<SongDTO> getSongs() {
		return songs;
	}

	public void setSongs(List<SongDTO> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "PlaylistDTO [id=" + id + ", name=" + name + ", type=" + type + ", imgLink=" + imgLink + ", songs="
				+ songs + "]";
	}

    
}

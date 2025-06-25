package com.example.main.dto;

import java.util.List;

public class PlaylistDTO {

    private Long id;
    private String name;
    private String type;
	private String imgLink;
    private Long userId;
    private List<Long> songIds;

    public PlaylistDTO() {}

    public PlaylistDTO(Long id, String name, String type, String imgLink, Long userId, List<Long> songIds) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.imgLink = imgLink;
		this.userId = userId;
		this.songIds = songIds;
	}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String description) { this.type = description; }
    
    public String getImgLink() { return imgLink; }
	public void setImgLink(String imgLink) { this.imgLink = imgLink; }

	public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<Long> getSongIds() { return songIds; }
    public void setSongIds(List<Long> songIds) { this.songIds = songIds; }
}

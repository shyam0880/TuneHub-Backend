package com.example.main.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
	private String imgLink;
	private String imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<Song> songs = new HashSet<>();

    public Playlist() {}

    public Playlist(Long id, String name, String type, String imgLink, String imageId, Users user, Set<Song> songs) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.imgLink = imgLink;
		this.imageId = imageId;
		this.user = user;
		this.songs = songs;
	}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String description) { this.type = description; }
    
    public String getImgLink() { return imgLink; }
	public void setImgLink(String imgLink) { this.imgLink = imgLink; }

	public String getImageId() { return imageId; }
	public void setImageId(String imageId) { this.imageId = imageId; }

	public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Set<Song> getSongs() { return songs; }
    public void setSongs(Set<Song> songs) { this.songs = songs; }
}

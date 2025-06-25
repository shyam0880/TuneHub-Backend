package com.example.main.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String genre;
    private String link;
    private String imgLink;
    private String songID;
    private String imageID;
    private boolean likeSong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToMany(mappedBy = "songs", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private Set<Playlist> playlists = new HashSet<>();

    public Song() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getImgLink() { return imgLink; }
    public void setImgLink(String imgLink) { this.imgLink = imgLink; }

    public String getSongID() { return songID; }
    public void setSongID(String songID) { this.songID = songID; }

    public String getImageID() { return imageID; }
    public void setImageID(String imageID) { this.imageID = imageID; }

    public boolean getLikeSong() { return likeSong; }
    public void setLikeSong(boolean likeSong) { this.likeSong = likeSong; }

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }

    public Set<Playlist> getPlaylists() { return playlists; }
    public void setPlaylists(Set<Playlist> playlists) { this.playlists = playlists; }
}

package com.example.main.dto;

import java.util.List;

public class SongDTO {
    private Long id;
    private String name;
    private String genre;
    private Long artistId;
    private String artistName;
    private String link;
    private String imgLink;
    private boolean likeSong;
    private List<Long> playlistIds;

    public SongDTO() {}

    public SongDTO(Long id, String name, String genre, Long artistId, String artistName,
                   String link, String imgLink, boolean likeSong, List<Long> playlistIds) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.artistId = artistId;
        this.artistName = artistName;
        this.link = link;
        this.imgLink = imgLink;
        this.likeSong = likeSong;
        this.playlistIds = playlistIds;
    }

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getImgLink() { return imgLink; }
    public void setImgLink(String imgLink) { this.imgLink = imgLink; }

    public boolean isLikeSong() { return likeSong; }
    public void setLikeSong(boolean likeSong) { this.likeSong = likeSong; }

    public List<Long> getPlaylistIds() { return playlistIds; }
    public void setPlaylistIds(List<Long> playlistIds) { this.playlistIds = playlistIds; }
}

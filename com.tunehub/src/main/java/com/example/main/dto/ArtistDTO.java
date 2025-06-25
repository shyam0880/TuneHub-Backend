package com.example.main.dto;

import java.util.List;

public class ArtistDTO {
    private Long id;
    private String name;
    private String image;
    private String imageId;
    private List<SongDTO> songs;

    public ArtistDTO() {
    }

    public ArtistDTO(Long id, String name, String image, String imageId, List<SongDTO> songs) {
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

    public List<SongDTO> getSongs() { return songs;  }
    public void setSongs(List<SongDTO> songs) { this.songs = songs; }

    @Override
    public String toString() {
        return "ArtistDTO [id=" + id + ", name=" + name + ", image=" + image + ", imageId=" + imageId + ", songs=" + songs + "]";
    }
}


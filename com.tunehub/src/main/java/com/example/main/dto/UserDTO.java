package com.example.main.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String gender;
    private String role;
    private String address;
    private String image;
    private boolean isPremium;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, String gender, String role, String address, String image, boolean isPremium) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.address = address;
        this.image = image;
        this.isPremium = isPremium;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public boolean isPremium() { return isPremium; }
    public void setPremium(boolean isPremium) { this.isPremium = isPremium; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", isPremium=" + isPremium +
                '}';
    }
}


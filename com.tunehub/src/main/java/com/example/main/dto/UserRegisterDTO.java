package com.example.main.dto;

public class UserRegisterDTO {
    private String username;
    private String email;
    private String password;  // ✅ Only available here for registration
    private String gender;
    private String address;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String username, String email, String password, String gender, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +  // ✅ Don't print actual password in logs
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

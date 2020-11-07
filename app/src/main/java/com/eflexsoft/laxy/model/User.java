package com.eflexsoft.laxy.model;

public class User {

    private String username;
    private String email;
    private String id;
    private String profilePicture;
    private String address;

    public User() {
    }

    public User(String username, String email, String id, String profilePicture, String address) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.profilePicture = profilePicture;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

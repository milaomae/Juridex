package com.example.lucas.juridex_v13.models;

/**
 * Created by Lucas on 26/11/2017.
 */

public class User {

    private String email;
    private String username;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public User() {
        }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                " email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

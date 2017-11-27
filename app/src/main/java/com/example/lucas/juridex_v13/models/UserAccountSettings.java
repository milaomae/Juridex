package com.example.lucas.juridex_v13.models;

/**
 * Created by Lucas on 26/11/2017.
 */

public class UserAccountSettings {

    private String profile_photo;
    private String username;
    private long total_easy;
    private long total_hard;
    private long total_medium;
    private long xp;

    public UserAccountSettings(String profile_photo, String username, long total_easy, long total_hard, long total_medium, long xp) {
        this.profile_photo = profile_photo;
        this.username = username;
        this.total_easy = total_easy;
        this.total_hard = total_hard;
        this.total_medium = total_medium;
        this.xp = xp;
    }

    public UserAccountSettings() {
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTotal_easy() {
        return total_easy;
    }

    public void setTotal_easy(long total_easy) {
        this.total_easy = total_easy;
    }

    public long getTotal_hard() {
        return total_hard;
    }

    public void setTotal_hard(long total_hard) {
        this.total_hard = total_hard;
    }

    public long getTotal_medium() {
        return total_medium;
    }

    public void setTotal_medium(long total_medium) {
        this.total_medium = total_medium;
    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = xp;
    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "profile_photo='" + profile_photo + '\'' +
                ", username='" + username + '\'' +
                ", total_easy=" + total_easy +
                ", total_hard=" + total_hard +
                ", total_medium=" + total_medium +
                ", xp=" + xp +
                '}';
    }
}

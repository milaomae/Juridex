package com.example.lucas.juridex_v13.models;

/**
 * Created by Lucas on 26/11/2017.
 */

public class UserAccountSettings {

    private String photo;
    private long testes_easy;
    private long testes_hard;
    private long testes_medium;
    private long score;

    public UserAccountSettings() {

    }

    public UserAccountSettings(String photo, long testes_easy, long testes_hard, long testes_medium, long score) {
        this.photo = photo;
        this.testes_easy = testes_easy;
        this.testes_hard = testes_hard;
        this.testes_medium = testes_medium;
        this.score = score;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getTestes_easy() {
        return testes_easy;
    }

    public void setTestes_easy(long testes_easy) {
        this.testes_easy = testes_easy;
    }

    public long getTestes_hard() {
        return testes_hard;
    }

    public void setTestes_hard(long testes_hard) {
        this.testes_hard = testes_hard;
    }

    public long getTestes_medium() {
        return testes_medium;
    }

    public void setTestes_medium(long testes_medium) {
        this.testes_medium = testes_medium;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "photo='" + photo + '\'' +
                ", testes_easy=" + testes_easy +
                ", testes_hard=" + testes_hard +
                ", testes_medium=" + testes_medium +
                ", score=" + score +
                '}';
    }
}

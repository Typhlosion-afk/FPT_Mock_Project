package com.dore.myapplication.model;

import android.graphics.Bitmap;

import java.util.List;

public class MusicStyle {
    String name;
    List<Song> songs;
    Bitmap theme;

    public MusicStyle(String name, List<Song> songs, Bitmap theme) {
        this.name = name;
        this.songs = songs;
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Bitmap getTheme() {
        return theme;
    }

    public void setTheme(Bitmap theme) {
        this.theme = theme;
    }
}

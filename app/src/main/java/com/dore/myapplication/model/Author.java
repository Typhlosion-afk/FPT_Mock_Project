package com.dore.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Author extends AbsObject implements Serializable {

    private String name;
    private List<Album> albums;
    private List<Song> songs;

    public Author(String name, List<Album> albums, List<Song> songs){
        this.name = name;
        this.albums = albums;
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}

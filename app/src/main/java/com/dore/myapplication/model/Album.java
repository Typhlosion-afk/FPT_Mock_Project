package com.dore.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Album {

    private String name;

    private ArrayList<Song> listSong = new ArrayList<>();

    private String author;

    public Album(String name, String author ,List<Song> songList){
        this.name = name;
        this.listSong.addAll(songList);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Song> getListSong() {
        return listSong;
    }

    public void setListSong(ArrayList<Song> listSong) {
        this.listSong = listSong;
    }
}

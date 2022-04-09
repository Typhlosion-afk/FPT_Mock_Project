package com.dore.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {

    private String name;

    private ArrayList<Song> listSong = new ArrayList<>();

    private String author;

    private String year;

    public Album(String name, String author, List<Song> songList, String year) {
        this.name = name;
        this.listSong.addAll(songList);
        this.author = author;
        this.year = year;
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

    public String getYear() {
        return this.year;
    }
}

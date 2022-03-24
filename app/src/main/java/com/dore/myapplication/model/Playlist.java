package com.dore.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist extends AbsObject{

    private String name;

    private ArrayList<Song> listSong = new ArrayList<>();
    public Playlist(String name, List<Song> songList){
        this.name = name;
        this.listSong.addAll(songList);
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

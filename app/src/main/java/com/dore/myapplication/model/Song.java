package com.dore.myapplication.model;

public class Song extends AbsObject{

    public String name;
    public String author;
    public String path;

    public Song(String name, String author, String path){
        this.name = name;
        this.author = author;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

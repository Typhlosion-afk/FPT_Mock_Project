package com.dore.myapplication.model;

import androidx.annotation.IdRes;

import java.io.Serializable;

public class Song extends AbsObject implements Serializable {

    public String name;
    public String author;
    public String path;
    public int res;

    public Song(String name, String author, String path){
        this.name = name;
        this.author = author;
        this.path = path;
    }

    public Song(String name, String author, int res){
        this.name = name;
        this.author = author;
        this.res = res;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
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

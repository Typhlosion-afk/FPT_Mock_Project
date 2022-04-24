package com.dore.myapplication.model;

import android.net.Uri;

import androidx.annotation.IdRes;

import java.io.Serializable;

public class Song extends AbsObject implements Serializable {

    public String name;
    public String author;
    public String path;
    public String imgPath;
    public String album;
    public String dur;
//
//    public Song(String name, String author, String path) {
//        this.name = name;
//        this.author = author;
//        this.path = path;
//    }

    public Song(String imgPath,String name, String author, String path, String album, String dur) {
        this.imgPath = imgPath;
        this.name = name;
        this.author = author;
        this.path = path;
        this.album = album;
        this.dur = dur;
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

    public Uri getUri() {
        return Uri.parse(this.path);
    }

    public void setUri(Uri uri){
        this.path = uri.toString();
    }

    public String getExtension() {
        return path.substring(path.lastIndexOf('.'));
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAlbumName() {
        return album;
    }

    public String getStrDurTime() {
        return dur;
    }
}

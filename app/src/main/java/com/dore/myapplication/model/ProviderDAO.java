package com.dore.myapplication.model;

import static com.dore.myapplication.utilities.Constants.ALBUMART_URI;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.dore.myapplication.utilities.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class ProviderDAO {

    private Cursor cursor;

    private String[] projection;

    private List<Song> allSongs = new ArrayList<>();

    private List<Song> recentlySongs = new ArrayList<>();

    private List<Album> allAlbum = new ArrayList<>();

    int idColumn;
    int nameColumn;
    int artistColumn;
    int dataColumn;
    int albumArtColumn;

    public ProviderDAO(Context context) {
        projection = new String[]
                {
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DATA,
                };

        cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            idColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            nameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        }

    }

    public List<Song> getAllSongs() {
        if (allSongs.size() == 0) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String uriSong = ContentUris.withAppendedId(ALBUMART_URI, Integer.parseInt(cursor.getString(idColumn))).toString();

                    LogUtils.d(uriSong);

                    Song song = new Song(
                            uriSong,
                            cursor.getString(nameColumn),
                            cursor.getString(artistColumn),
                            cursor.getString(dataColumn));

                    if (song.getExtension().equals(".mp3")) {
                        allSongs.add(song);
                    }

                } while (cursor.moveToNext());
            }
        }
        return allSongs;
    }

    public List<Song> getSongWithArtist(String artist) {
        List<Song> songs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Song song = new Song(cursor.getString(nameColumn),
                        cursor.getString(artistColumn),
                        cursor.getString(dataColumn));

                if (song.getExtension().equals(".mp3")) {
                    if (song.getAuthor().equals(artist)) {
                        songs.add(song);
                    }
                }

                LogUtils.d(song.getExtension());
            } while (cursor.moveToNext());
            cursor.close();
        }
        return songs;

    }

    public List<Song> getRecentlySong() {
        // data for test
        recentlySongs.clear();
        recentlySongs.addAll(getAllSongs());
        return recentlySongs;
    }

    public List<Album> getAllAlbum() {
        return allAlbum;
    }

    public void closeCursor() {
        cursor.close();
    }
}

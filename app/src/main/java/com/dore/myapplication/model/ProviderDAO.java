package com.dore.myapplication.model;

import static com.dore.myapplication.utilities.Constants.ALBUMART_URI;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.dore.myapplication.utilities.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class ProviderDAO {

    private final Cursor cursor;

    private List<Song> mAllSongs = new ArrayList<>();

    private List<Album> mAllAlbum = new ArrayList<>();

    private Context mContext;


    int idColumn;
    int nameColumn;
    int artistColumn;
    int dataColumn;
    int albumColumn;
    int durColumn;

    public ProviderDAO(Context context) {

        this.mContext = context;

        String[] projection = new String[]
                {
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DURATION,
                };

        cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            idColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            nameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            durColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        }

    }

    public List<Song> getAllSongs() {
        mAllSongs.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String uriSong = ContentUris
                        .withAppendedId(ALBUMART_URI, Integer.parseInt(cursor.getString(idColumn)))
                        .toString();

                Song song = new Song(
                        uriSong,
                        cursor.getString(nameColumn),
                        cursor.getString(artistColumn),
                        cursor.getString(dataColumn),
                        cursor.getString(albumColumn),
                        cursor.getString(durColumn));

                if (song.getExtension().equals(".mp3")) {
                    mAllSongs.add(song);
                }

            } while (cursor.moveToNext());
        }
        return mAllSongs;
    }

    public List<Artist> getAllArtist() {
        List<Artist> artists = new ArrayList<>();

        if (mAllAlbum.size() == 0) {
            mAllAlbum = getAllAlbum();
        }

        for (Album album : mAllAlbum) {
            boolean isExist = false;
            for (Artist a : artists) {
                if (a.getName().equals(album.getAuthor())) {
                    isExist = true;
                    a.getAlbums().add(album);
                }
            }
            if (!isExist) {
                List<Album> albums = new ArrayList<>();
                albums.add(album);
                artists.add(new Artist(album.getAuthor(), albums));
            }
        }

        return artists;
    }

    public List<Song> getRecentlySong() {
        // data for test
        List<Song> allSongs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String uriSong = ContentUris
                        .withAppendedId(ALBUMART_URI, Integer.parseInt(cursor.getString(idColumn)))
                        .toString();

                Song song = new Song(
                        uriSong,
                        cursor.getString(nameColumn),
                        cursor.getString(artistColumn),
                        cursor.getString(dataColumn),
                        cursor.getString(albumColumn),
                        cursor.getString(durColumn));

                if (song.getExtension().equals(".mp3")) {
                    allSongs.add(song);
                }

            } while (cursor.moveToNext());
        }
        return allSongs;
    }

    public List<Album> getAllAlbum() {
        if (mAllSongs.size() == 0) {
            mAllSongs = getAllSongs();
        }

        mAllAlbum.clear();

        for (Song song : mAllSongs) {
            boolean isExist = false;
            for (Album a : mAllAlbum) {
                if (a.getName().equals(song.getAlbumName())) {
                    isExist = true;
                    a.getListSong().add(song);
                }
            }
            if (!isExist) {
                List<Song> songs = new ArrayList<>();
                songs.add(song);
                mAllAlbum.add(new Album(song.getAlbumName(), song.getAuthor(), songs, ""));
            }
        }
        return mAllAlbum;
    }

    private List<Album> addSongToAlbum(Song song, List<Album> album) {
        return album;
    }

    public void closeCursor() {
        cursor.close();
    }

    public List<Song> search(String s, int limit) {
        List<Song> searchList = new ArrayList<>();
        String where = MediaStore.Audio.Media.TITLE + " LIKE ?";
        String[] params = new String[]{"%" + s + "%"};

        LogUtils.d("search: " + s);

        String[] projection = new String[]
                {
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DURATION,
                };

        try (Cursor songCursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                where,
                params,
                null)) {
            while (songCursor.moveToNext() && searchList.size() < limit) {
                String uriSong = ContentUris
                        .withAppendedId(ALBUMART_URI, Integer.parseInt(songCursor.getString(idColumn)))
                        .toString();

                Song song = new Song(
                        uriSong,
                        songCursor.getString(nameColumn),
                        songCursor.getString(artistColumn),
                        songCursor.getString(dataColumn),
                        songCursor.getString(albumColumn),
                        songCursor.getString(durColumn)
                );

                if (song.getExtension().equals(".mp3")) {
                    searchList.add(song);
                }
            }
        }
        Log.d("TAG", "search: " + searchList.size());
        return searchList;
    }
}

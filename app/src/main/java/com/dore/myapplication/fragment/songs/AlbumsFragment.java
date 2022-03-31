package com.dore.myapplication.fragment.songs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dore.myapplication.R;
import com.dore.myapplication.adapter.AlbumsAdapter;
import com.dore.myapplication.adapter.AllSongsAdapter;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends Fragment {


    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    private List<Album> mListAlbum = new ArrayList<>();

    private AlbumsAdapter mAlbumsAdapter;

    private RecyclerView mRecyclerView;


    public AlbumsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_albums, container, false);

        initData();
        initAdapter();

        return mRootView;
    }

    private void initData() {
        mListSong.clear();
        mListSong.add(new Song("Bai hat so 1", "Ca si 1", "path 1"));
        mListSong.add(new Song("Bai hat so 2", "Ca si 1", "path 2"));
        mListSong.add(new Song("Bai hat so 3", "Ca si 2", "path 3"));
        mListSong.add(new Song("Bai hat so 4", "Ca si 2", "path 4"));
        mListSong.add(new Song("Bai hat so 5", "Ca si 2", "path 5"));
        mListSong.add(new Song("Bai hat so 6", "Ca si 3", "path 6"));

        mListAlbum.clear();
        mListAlbum.add(new Album("Album 1", "Author 10", mListSong));
        mListAlbum.add(new Album("Album 2", "Author 15", mListSong));
        mListAlbum.add(new Album("Album 3", "Author 45", mListSong));
        mListAlbum.add(new Album("Album 4", "Author 78", mListSong));
        mListAlbum.add(new Album("Album 5", "Author 5", mListSong));
        mListAlbum.add(new Album("Album 6", "Author 456", mListSong));
        mListAlbum.add(new Album("Album 7", "Author 4", mListSong));
        mListAlbum.add(new Album("Album 8", "Author 6", mListSong));
        mListAlbum.add(new Album("Album 9", "Author 51", mListSong));
        mListAlbum.add(new Album("Album 10", "Author 18", mListSong));


    }

    private void initAdapter() {
        mAlbumsAdapter = new AlbumsAdapter(mListAlbum);
        mRecyclerView = mRootView.findViewById(R.id.albums_container);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRootView.getContext(), 2, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAlbumsAdapter);
    }
}
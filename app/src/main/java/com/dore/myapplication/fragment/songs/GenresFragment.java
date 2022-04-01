package com.dore.myapplication.fragment.songs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dore.myapplication.R;
import com.dore.myapplication.adapter.AllSongsAdapter;
import com.dore.myapplication.adapter.GenresAdapter;
import com.dore.myapplication.model.MusicStyle;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class GenresFragment extends Fragment {

    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    private List<MusicStyle> mListMusicStyle = new ArrayList<>();

    private GenresAdapter mGenresAdapter;

    private RecyclerView mRecyclerView;

    public GenresFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_genres, container, false);

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
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7", "Ca si 3", "path 7"));

        mListMusicStyle.clear();
        mListMusicStyle.add(new MusicStyle("Classical", mListSong, BitmapFactory.decodeResource(getResources(), R.drawable.classical_bg)));
        mListMusicStyle.add(new MusicStyle("Pop", mListSong, BitmapFactory.decodeResource(getResources(), R.drawable.pop_bg)));
        mListMusicStyle.add(new MusicStyle("Hip-Hop", mListSong, BitmapFactory.decodeResource(getResources(), R.drawable.hiphop_bg)));
        mListMusicStyle.add(new MusicStyle("Rock", mListSong, BitmapFactory.decodeResource(getResources(), R.drawable.rock_bg)));
        mListMusicStyle.add(new MusicStyle("Soul and R&B", mListSong, BitmapFactory.decodeResource(getResources(), R.drawable.soul_rb_bg)));
        mListMusicStyle.add(new MusicStyle("Instrumental", mListSong, BitmapFactory.decodeResource(getResources(), R.drawable.instrumental_bg)));
        mListMusicStyle.add(new MusicStyle("Jazz", mListSong, BitmapFactory.decodeResource(getResources(), R.drawable.jazz_bg)));
        mListMusicStyle.add(new MusicStyle("Country", mListSong, BitmapFactory.decodeResource(getResources(), R.drawable.country_bg)));

    }

    private void initAdapter() {
        mRecyclerView = mRootView.findViewById(R.id.genres_container);
        mGenresAdapter = new GenresAdapter(mListMusicStyle);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRootView.getContext(), 2, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mGenresAdapter);
    }
}
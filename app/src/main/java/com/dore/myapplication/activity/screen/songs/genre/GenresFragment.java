package com.dore.myapplication.activity.screen.songs.genre;

import android.graphics.BitmapFactory;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.dore.myapplication.R;
import com.dore.myapplication.activity.screen.songs.genre.adapter.GenresAdapter;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.model.MusicStyle;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.utilities.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class GenresFragment extends BaseFragment {

    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    private List<MusicStyle> mListMusicStyle = new ArrayList<>();

    private GenresAdapter mGenresAdapter;

    private RecyclerView mRecyclerView;

    public GenresFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_genres;
    }

    @Override
    public void onViewReady(View rootView) {
        LogUtils.d("Create");
        mRootView = rootView;
        initData();
        initAdapter();
    }

    @Override
    public void onStart() {
        LogUtils.d("Start");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtils.d("Resume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.d("Pause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtils.d("Stop");
        super.onStop();
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
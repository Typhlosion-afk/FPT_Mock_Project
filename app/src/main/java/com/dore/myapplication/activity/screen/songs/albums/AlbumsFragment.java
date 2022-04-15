package com.dore.myapplication.activity.screen.songs.albums;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.dore.myapplication.R;
import com.dore.myapplication.activity.screen.songs.albums.adapter.AlbumsAdapter;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends BaseFragment {


    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    private List<Album> mListAlbum = new ArrayList<>();

    private AlbumsAdapter mAlbumsAdapter;

    private RecyclerView mRecyclerView;


    public AlbumsFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_albums;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;

        initData();
        initAdapter();
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
        mListAlbum.add(new Album("Album 1", "Author 10", mListSong, "1991"));
        mListAlbum.add(new Album("Album 2", "Author 15", mListSong, "1991"));
        mListAlbum.add(new Album("Album 3", "Author 45", mListSong, "2016"));
        mListAlbum.add(new Album("Album 4", "Author 78", mListSong, "1991"));
        mListAlbum.add(new Album("Album 5", "Author 5", mListSong, "2015"));
        mListAlbum.add(new Album("Album 6", "Author 456", mListSong, "2000"));
        mListAlbum.add(new Album("Album 7", "Author 4", mListSong, "1998"));
        mListAlbum.add(new Album("Album 8", "Author 6", mListSong, "1991"));
        mListAlbum.add(new Album("Album 9", "Author 51", mListSong, "2020"));
        mListAlbum.add(new Album("Album 10", "Author 18", mListSong, "1991"));


    }

    private void initAdapter() {
        mAlbumsAdapter = new AlbumsAdapter(mListAlbum, mRootView.getContext());
        mRecyclerView = mRootView.findViewById(R.id.albums_container);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRootView.getContext(), 2, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAlbumsAdapter);
    }
}
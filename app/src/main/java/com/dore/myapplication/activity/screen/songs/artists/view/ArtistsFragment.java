package com.dore.myapplication.activity.screen.songs.artists.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.dore.myapplication.R;
import com.dore.myapplication.activity.screen.songs.artists.adapter.ArtistsAdapter;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Author;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class ArtistsFragment extends BaseFragment {


    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    private List<Album> mListAlbum = new ArrayList<>();

    private List<Author> mListAuthor = new ArrayList<>();

    private ArtistsAdapter mArtistsAdapter;

    private RecyclerView mRecyclerView;

    public ArtistsFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_artists;
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
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7", "Ca si 3", "path 7"));

        mListAlbum.clear();
        mListAlbum.add(new Album("album", "author", mListSong, "1991"));
        mListAlbum.add(new Album("album", "author", mListSong, "1991"));
        mListAlbum.add(new Album("album", "author", mListSong, "1991"));
        mListAlbum.add(new Album("album", "author", mListSong, "1991"));

        mListAuthor.add(new Author("Ca si 1", mListAlbum, mListSong));
        mListAuthor.add(new Author("Ca si 2", mListAlbum, mListSong));
        mListAuthor.add(new Author("Ca si 3", mListAlbum, mListSong));
        mListAuthor.add(new Author("Ca si 4", mListAlbum, mListSong));
        mListAuthor.add(new Author("Ca si 5", mListAlbum, mListSong));
        mListAuthor.add(new Author("Ca si 6", mListAlbum, mListSong));
        mListAuthor.add(new Author("Ca si 7", mListAlbum, mListSong));
        mListAuthor.add(new Author("Ca si 8", mListAlbum, mListSong));

    }

    private void initAdapter() {
        mArtistsAdapter = new ArtistsAdapter(mListAuthor, mRootView.getContext());
        mRecyclerView = mRootView.findViewById(R.id.artists_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mArtistsAdapter);
    }
}
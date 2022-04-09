package com.dore.myapplication.fragment.songs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dore.myapplication.R;
import com.dore.myapplication.adapter.ArtistAlbumAdapter;
import com.dore.myapplication.adapter.ArtistSongAdapter;
import com.dore.myapplication.fragment.BaseFragment;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Author;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class ArtistDetailsFragment extends BaseFragment {

    private View mRootView;

    private ImageView img;

    private TextView mTxtArtistName;

    private TextView mTxtMusicTypes;

    private TextView mNumFollower;

    private TextView mNumListeners;

    private Button mBtnFollow;

    private RecyclerView mTopAlbumRecyclerView;

    private RecyclerView mTopSongRecyclerView;

    private ArtistAlbumAdapter mArtistAlbumAdapter;

    private ArtistSongAdapter mArtistSongAdapter;

    private List<Album> mListAlbums = new ArrayList<>();

    private List<Song> mListSong = new ArrayList<>();

    private Author mAuthor;


    public ArtistDetailsFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_artist_details;
    }

    @Override
    public void onViewReady(View rootView) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_artist_details, container, false);

        initData();
        initView();
        initAdapter();

        return mRootView;
    }

    private void initData() {
        if (getArguments() != null) {
            mAuthor = (Author) getArguments().getSerializable("author");
            Log.d("TAG", "initData: " + mAuthor.getName());

        }

        mListSong.clear();
        mListSong.add(new Song("Bai hat so 1", "Ca si 1", "path 1"));
        mListSong.add(new Song("Bai hat so 2", "Ca si 1", "path 2"));
        mListSong.add(new Song("Bai hat so 3", "Ca si 1", "path 3"));
        mListSong.add(new Song("Bai hat so 4", "Ca si 1", "path 4"));
        mListSong.add(new Song("Bai hat so 5", "Ca si 1", "path 5"));
        mListSong.add(new Song("Bai hat so 6", "Ca si 1", "path 6"));
        mListSong.add(new Song("Bai hat so 12", "Ca si 1", "path 1"));
        mListSong.add(new Song("Bai hat so 22", "Ca si 1", "path 2"));
        mListSong.add(new Song("Bai hat so 32", "Ca si 1", "path 3"));
        mListSong.add(new Song("Bai hat so 42", "Ca si 1", "path 4"));
        mListSong.add(new Song("Bai hat so 52", "Ca si 1", "path 5"));
        mListSong.add(new Song("Bai hat so 62", "Ca si 1", "path 6"));

        mListAlbums.clear();
        mListAlbums.add(new Album("Album 1", "Author 10", mListSong, "1991"));
        mListAlbums.add(new Album("Album 2", "Author 15", mListSong, "1991"));
        mListAlbums.add(new Album("Album 3", "Author 45", mListSong, "2016"));
        mListAlbums.add(new Album("Album 4", "Author 78", mListSong, "1991"));
        mListAlbums.add(new Album("Album 5", "Author 5", mListSong, "2015"));
        mListAlbums.add(new Album("Album 6", "Author 456", mListSong, "2000"));
        mListAlbums.add(new Album("Album 7", "Author 4", mListSong, "1998"));
        mListAlbums.add(new Album("Album 8", "Author 6", mListSong, "1991"));
        mListAlbums.add(new Album("Album 9", "Author 51", mListSong, "2020"));
        mListAlbums.add(new Album("Album 10", "Author 18", mListSong, "1991"));

    }

    private void initView() {
        img = mRootView.findViewById(R.id.img_cover);

        img.setImageResource(R.drawable.img_bg_recommend_default);
    }

    private void initAdapter() {
        mTopAlbumRecyclerView = mRootView.findViewById(R.id.artist_album_container);
        mArtistAlbumAdapter = new ArtistAlbumAdapter(mRootView.getContext(), mListAlbums);
        mTopAlbumRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext(), RecyclerView.HORIZONTAL, false));
        mTopAlbumRecyclerView.setAdapter(mArtistAlbumAdapter);

        mTopSongRecyclerView = mRootView.findViewById(R.id.artist_songs_container);
        mTopSongRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext(), RecyclerView.VERTICAL, false));
        mArtistSongAdapter = new ArtistSongAdapter(mRootView.getContext(), mListSong);
        mTopSongRecyclerView.setAdapter(mArtistSongAdapter);

    }

}
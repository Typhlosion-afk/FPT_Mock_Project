package com.dore.myapplication.activity.screen.artistdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.dore.myapplication.activity.screen.artistdetail.adapter.ArtistAlbumAdapter;
import com.dore.myapplication.activity.screen.artistdetail.adapter.ArtistSongAdapter;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Artist;
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

    private Artist mArtist;


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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_artist_details, container, false);

        initData();
        initView();
        initAdapter();

        return mRootView;
    }

    private void initData() {
        if (getArguments() != null) {
            mArtist = (Artist) getArguments().getSerializable("artist");
            mListAlbums = mArtist.getAlbums();
            mListSong.clear();
            for (Album album: mListAlbums){
                mListSong.addAll(album.getListSong());
            }
        }
    }

    private void initView() {
        img = mRootView.findViewById(R.id.img_album_cover);

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
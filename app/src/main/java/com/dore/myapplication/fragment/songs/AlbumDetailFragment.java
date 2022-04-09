package com.dore.myapplication.fragment.songs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dore.myapplication.R;
import com.dore.myapplication.adapter.AlbumDetailAdapter;
import com.dore.myapplication.adapter.ArtistAlbumAdapter;
import com.dore.myapplication.adapter.ArtistSongAdapter;
import com.dore.myapplication.fragment.BaseFragment;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Author;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailFragment extends BaseFragment {

    private View mRootView;

    private ImageView img;

    private TextView mTxtName;

    private TextView mTxtAuthor;

    private TextView mTxtDetail;

    private Button mBtnFollow;

    private RecyclerView mRecyclerView;

    private AlbumDetailAdapter mAlbumDetailAdapter;

    private Album album;

    private List<Album> mListAlbums = new ArrayList<>();

    private List<Song> mListSong = new ArrayList<>();

    @Override
    public int getLayoutID() {
        return R.layout.fragment_album_detail;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;

        initData();
        initView();
        initAdapter();
    }

    private void initData() {

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

        album = new Album("Album custom", "Ca si 1", mListSong, "1998");

        if (getArguments() != null) {
            album = (Album) getArguments().getSerializable("album");
        }

    }

    private void initView() {
        mTxtName = mRootView.findViewById(R.id.txt_album_name);
        mTxtAuthor = mRootView.findViewById(R.id.txt_album_author);
        mTxtDetail = mRootView.findViewById(R.id.txt_album_detail);

        int time = 32;

        mTxtName.setText(album.getName());
        mTxtAuthor.setText(album.getAuthor());
        mTxtDetail.setText(strAlbumDetails(album.getYear(), album.getListSong().size(), time));

    }

    private void initAdapter() {
        mRecyclerView = mRootView.findViewById(R.id.album_songs_container);
        mAlbumDetailAdapter = new AlbumDetailAdapter(mRootView.getContext(), mListSong);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAlbumDetailAdapter);
    }

    private String strAlbumDetails(String year, int sizeAlbum, int timeAlbum) {
        return year + " . " + sizeAlbum + " Songs . " + timeAlbum + " min";
    }


}
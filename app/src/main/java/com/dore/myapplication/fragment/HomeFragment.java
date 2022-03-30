package com.dore.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dore.myapplication.R;
import com.dore.myapplication.adapter.HomePlaylistAdapter;
import com.dore.myapplication.adapter.HomeRecentlyAdapter;
import com.dore.myapplication.adapter.HomeRecommendAdapter;
import com.dore.myapplication.model.Playlist;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View mRootView;

    private RecyclerView mRecycleViewRecently;

    private RecyclerView mRecycleViewPlaylist;

    private RecyclerView mRecycleViewRecommend;

    private HomePlaylistAdapter mHomePlaylistAdapter;

    private HomeRecentlyAdapter mHomeRecentlyAdapter;

    private HomeRecommendAdapter mHomeRecommendAdapter;

    private ArrayList<Song> mListSong = new ArrayList();

    private ArrayList<Playlist> mListPlaylist = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

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

        mListPlaylist.clear();
        mListPlaylist.add(new Playlist("Playlist 1", mListSong));
        mListPlaylist.add(new Playlist("Playlist 2", mListSong));
        mListPlaylist.add(new Playlist("Playlist 3", mListSong));
        mListPlaylist.add(new Playlist("Playlist 4", mListSong));
        mListPlaylist.add(new Playlist("Playlist 5", mListSong));
        mListPlaylist.add(new Playlist("Playlist 6", mListSong));
        mListPlaylist.add(new Playlist("Playlist 7", mListSong));
        mListPlaylist.add(new Playlist("Playlist 8", mListSong));

    }

    private void initAdapter() {
        LinearLayoutManager layoutRecently =
                new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.VERTICAL, false);

        LinearLayoutManager layoutPlaylist =
                new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layoutHomeRecommend =
                new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        mRecycleViewRecommend = mRootView.findViewById(R.id.recycle_hot_rec);
        mRecycleViewRecommend.setLayoutManager(layoutHomeRecommend);
        mHomeRecommendAdapter = new HomeRecommendAdapter(mListSong);
        mRecycleViewRecommend.setAdapter(mHomeRecommendAdapter);

        mRecycleViewPlaylist = mRootView.findViewById(R.id.recycle_playlist);
        mRecycleViewPlaylist.setLayoutManager(layoutPlaylist);
        mHomePlaylistAdapter = new HomePlaylistAdapter(mListPlaylist);
        mRecycleViewPlaylist.setAdapter(mHomePlaylistAdapter);

        mRecycleViewRecently = mRootView.findViewById(R.id.recycle_recently);
        mRecycleViewRecently.setLayoutManager(layoutRecently);
        mHomeRecentlyAdapter = new HomeRecentlyAdapter(mListSong);
        mRecycleViewRecently.setAdapter(mHomeRecentlyAdapter);
    }
}
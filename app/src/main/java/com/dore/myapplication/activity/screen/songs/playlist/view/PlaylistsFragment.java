package com.dore.myapplication.activity.screen.songs.playlist.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.dore.myapplication.R;
import com.dore.myapplication.activity.screen.songs.playlist.adapter.MyPlaylistAdapter;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.model.Playlist;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsFragment extends BaseFragment {

    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    private List<Playlist> mListPlaylist = new ArrayList<>();

    private RecyclerView mRecycler;

    private MyPlaylistAdapter mMyPlaylistAdapter;

    public PlaylistsFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_playlists;
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
        mListSong.add(new Song("Bai hat so 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7", "Ca si 3", "path 7"));

        mListPlaylist.clear();
        mListPlaylist.add(new Playlist("Playlist 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7", mListSong));
        mListPlaylist.add(new Playlist("Playlist 1", mListSong));
        mListPlaylist.add(new Playlist("Playlist 2", mListSong));
        mListPlaylist.add(new Playlist("Playlist 3", mListSong));
        mListPlaylist.add(new Playlist("Playlist 4", mListSong));
        mListPlaylist.add(new Playlist("Playlist 5", mListSong));
        mListPlaylist.add(new Playlist("Playlist 6", mListSong));
        mListPlaylist.add(new Playlist("Playlist 7", mListSong));
    }

    private void handleMenu() {


    }

    private void initAdapter() {
        mMyPlaylistAdapter = new MyPlaylistAdapter(mListPlaylist, mRootView.getContext());
        mRecycler = mRootView.findViewById(R.id.my_playlist_container);
        mRecycler.setLayoutManager(new LinearLayoutManager(mRootView.getContext(), RecyclerView.HORIZONTAL, false));
        mRecycler.setAdapter(mMyPlaylistAdapter);
    }
}

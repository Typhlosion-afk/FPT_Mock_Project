package com.dore.myapplication.activity.screen.songs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dore.myapplication.activity.screen.songs.albums.AlbumsFragment;
import com.dore.myapplication.activity.screen.songs.allsong.AllSongsFragment;
import com.dore.myapplication.activity.screen.songs.artists.ArtistsFragment;
import com.dore.myapplication.activity.screen.songs.genre.GenresFragment;
import com.dore.myapplication.activity.screen.songs.playlist.PlaylistsFragment;

import java.util.ArrayList;
import java.util.List;

public class SongsPagerAdapter extends FragmentStateAdapter {

    private final int NUM_PAGES = 5;

    private List<Fragment> lsFm = new ArrayList<>();

    public SongsPagerAdapter(Fragment fa) {
        super(fa);

        initData();
    }

    private void initData() {
        lsFm.add(new AllSongsFragment());
        lsFm.add(new PlaylistsFragment());
        lsFm.add(new AlbumsFragment());
        lsFm.add(new ArtistsFragment());
        lsFm.add(new GenresFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return lsFm.get(position);
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}

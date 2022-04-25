package com.dore.myapplication.activity.screen.songs.albums;

import static com.dore.myapplication.MusicApplication.providerDAO;

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
        mListAlbum.clear();
        mListAlbum = providerDAO.getAllAlbum();
    }

    private void initAdapter() {
        mAlbumsAdapter = new AlbumsAdapter(mListAlbum, mRootView.getContext());
        mRecyclerView = mRootView.findViewById(R.id.albums_container);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRootView.getContext(), 2, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAlbumsAdapter);
    }
}
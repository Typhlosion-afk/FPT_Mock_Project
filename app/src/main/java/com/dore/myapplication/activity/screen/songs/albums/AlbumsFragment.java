package com.dore.myapplication.activity.screen.songs.albums;

import static com.dore.myapplication.activity.MainActivity.providerDAO;

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

    private final List<Song> mListSong = new ArrayList<>();

    private List<Album> mListAlbum = new ArrayList<>();

    AlbumsAdapter mAlbumsAdapter;

    public AlbumsFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_albums;
    }

    @Override
    public void onViewReady(View rootView) {

        mRootView = rootView;

        initAdapter();
        setThreadUpdateData();
    }


    private void initData() {
        mListAlbum.clear();
        mListAlbum = providerDAO.getAllAlbum();
    }

    private void setThreadUpdateData() {

        Runnable updateUiRunnable = () -> {
            if (mAlbumsAdapter != null) {
                mAlbumsAdapter.update(mListAlbum);
            }
        };

        Runnable dataRunnable = () -> {
            initData();
            requireActivity().runOnUiThread(updateUiRunnable);
        };

        Thread dataThread = new Thread(dataRunnable);
        dataThread.start();
    }

    private void initAdapter() {
        mAlbumsAdapter = new AlbumsAdapter(mListAlbum, mRootView.getContext());
        RecyclerView mRecyclerView = mRootView.findViewById(R.id.albums_container);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRootView.getContext(), 2, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAlbumsAdapter);
    }


}
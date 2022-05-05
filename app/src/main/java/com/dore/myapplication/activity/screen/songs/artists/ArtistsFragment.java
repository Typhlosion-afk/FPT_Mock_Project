package com.dore.myapplication.activity.screen.songs.artists;

import static com.dore.myapplication.activity.MainActivity.providerDAO;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.dore.myapplication.R;
import com.dore.myapplication.activity.screen.songs.artists.adapter.ArtistsAdapter;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Artist;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.utilities.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class ArtistsFragment extends BaseFragment {


    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    private List<Album> mListAlbum = new ArrayList<>();

    private List<Artist> mListArtist = new ArrayList<>();

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
        LogUtils.d("Create");

        initData();
        initAdapter();
    }

    @Override
    public void onResume() {
        LogUtils.d("Resume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.d("Pause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtils.d("Stop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        LogUtils.d("Destroy");
        super.onDestroy();
    }

    private void initData() {
        mListArtist.clear();
        mListArtist.addAll(providerDAO.getAllArtist());
    }

    private void initAdapter() {
        mArtistsAdapter = new ArtistsAdapter(mListArtist, mRootView.getContext());
        mRecyclerView = mRootView.findViewById(R.id.artists_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mArtistsAdapter);
    }
}
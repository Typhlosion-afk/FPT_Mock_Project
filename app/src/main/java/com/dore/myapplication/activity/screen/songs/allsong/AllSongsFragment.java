package com.dore.myapplication.activity.screen.songs.allsong;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.dore.myapplication.R;
import com.dore.myapplication.activity.screen.songs.allsong.adapter.AllSongsAdapter;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.utilities.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class AllSongsFragment extends BaseFragment {

    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    private AllSongsAdapter mAllSongsAdapter;

    private RecyclerView mRecycleView;

    public AllSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_all_songs;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;

        initData();
        initAdapter();
    }

    @Override
    public void onPause() {
        LogUtils.d("Pause");
        super.onPause();
    }

    @Override
    public void onResume() {
        LogUtils.d("Resume");
        super.onResume();
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
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 7", "Ca si 3", "path 7"));
        mListSong.add(new Song("Bai hat so 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7", "Ca si 3", "path 7"));
    }

    private void initAdapter() {
        mAllSongsAdapter = new AllSongsAdapter(mListSong);
        mRecycleView = mRootView.findViewById(R.id.recycle_all_song);

        mRecycleView.setLayoutManager(new LinearLayoutManager(
                mRootView.getContext(),
                RecyclerView.VERTICAL,
                false));

        mRecycleView.setAdapter(mAllSongsAdapter);
    }
}
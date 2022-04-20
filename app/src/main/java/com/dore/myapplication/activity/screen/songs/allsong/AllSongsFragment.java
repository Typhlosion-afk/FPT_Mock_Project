package com.dore.myapplication.activity.screen.songs.allsong;

import static com.dore.myapplication.MusicApplication.providerDAO;

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
        mListSong.addAll(providerDAO.getAllSongs());
    }

    private void initAdapter() {
        mAllSongsAdapter = new AllSongsAdapter(mRootView.getContext(), mListSong);
        mRecycleView = mRootView.findViewById(R.id.recycle_all_song);

        mRecycleView.setLayoutManager(new LinearLayoutManager(
                mRootView.getContext(),
                RecyclerView.VERTICAL,
                false));

        mRecycleView.setAdapter(mAllSongsAdapter);
    }
}
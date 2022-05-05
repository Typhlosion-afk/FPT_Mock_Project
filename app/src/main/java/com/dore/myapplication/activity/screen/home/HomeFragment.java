package com.dore.myapplication.activity.screen.home;

import static com.dore.myapplication.activity.MainActivity.providerDAO;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;

import com.dore.myapplication.R;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.activity.screen.home.adapter.HomePlaylistAdapter;
import com.dore.myapplication.activity.screen.home.adapter.HomeRecentlyAdapter;
import com.dore.myapplication.activity.screen.home.adapter.HomeRecommendAdapter;
import com.dore.myapplication.model.Playlist;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    private View mRootView;

    private Context mContext;

    private RecyclerView mRecycleViewRecently;

    private RecyclerView mRecycleViewPlaylist;

    private RecyclerView mRecycleViewRecommend;

    private HomePlaylistAdapter mHomePlaylistAdapter;

    private HomeRecentlyAdapter mHomeRecentlyAdapter;

    private HomeRecommendAdapter mHomeRecommendAdapter;

    private ArrayList<Song> mListRecommend = new ArrayList<>();

    private ArrayList<Playlist> mListPlaylist = new ArrayList<>();

    private ArrayList<Song> mListRecently = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;
        mContext = mRootView.getContext();

        initData();
        initAdapter();
    }

    private void initData() {
        mListRecommend.clear();
        mListRecommend.addAll(providerDAO.getAllSongs());

        mListRecently.clear();
        mListRecently.addAll(providerDAO.getRecentlySong());
    }

    private void initAdapter() {
        LinearLayoutManager layoutRecently =
                new LinearLayoutManager(
                        mRootView.getContext(),
                        LinearLayoutManager.VERTICAL,
                        false);

        LinearLayoutManager layoutPlaylist =
                new LinearLayoutManager(
                        mRootView.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false);

        LinearLayoutManager layoutHomeRecommend =
                new LinearLayoutManager(
                        mRootView.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false);

        mRecycleViewRecommend = mRootView.findViewById(R.id.recycle_hot_rec);
        mRecycleViewRecommend.setLayoutManager(layoutHomeRecommend);
        mRecycleViewRecommend.setNestedScrollingEnabled(false);
        mHomeRecommendAdapter = new HomeRecommendAdapter(mContext, mListRecommend);
        mRecycleViewRecommend.setAdapter(mHomeRecommendAdapter);

        mRecycleViewPlaylist = mRootView.findViewById(R.id.recycle_playlist);
        mRecycleViewPlaylist.setLayoutManager(layoutPlaylist);
        mHomePlaylistAdapter = new HomePlaylistAdapter(mContext ,mListPlaylist);
        mRecycleViewPlaylist.setNestedScrollingEnabled(false);
        mRecycleViewPlaylist.setAdapter(mHomePlaylistAdapter);

        mRecycleViewRecently = mRootView.findViewById(R.id.recycle_recently);
        mRecycleViewRecently.setNestedScrollingEnabled(false);
        mRecycleViewRecently.setLayoutManager(layoutRecently);
        mHomeRecentlyAdapter = new HomeRecentlyAdapter(mListRecently);
        mRecycleViewRecently.setAdapter(mHomeRecentlyAdapter);
    }

}
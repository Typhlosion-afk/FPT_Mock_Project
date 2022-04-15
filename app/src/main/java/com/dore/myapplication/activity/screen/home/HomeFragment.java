package com.dore.myapplication.activity.screen.home;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    public int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;

        initData();
        initAdapter();
    }

    private void initData() {

        mListSong.clear();
        mListSong.add(new Song("Đế vương", "Đình Dũng", R.raw.de_vuong));
        mListSong.add(new Song("Hãy trao cho anh", "Sơn Tùng - MTP", R.raw.hay_trao_cho_anh));
        mListSong.add(new Song("Lạc trôi", "Sơn Tùng - MTP", R.raw.lac_troi));
        mListSong.add(new Song("Nắng ấm xa dần", "Sơn Tùng - MTP", R.raw.nang_am_xa_dan));
        mListSong.add(new Song("Thức giấc", "DA LAB", R.raw.thuc_giac));

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
        mRecycleViewRecommend.setNestedScrollingEnabled(false);
        mHomeRecommendAdapter = new HomeRecommendAdapter(mListSong);
        mRecycleViewRecommend.setAdapter(mHomeRecommendAdapter);

        mRecycleViewPlaylist = mRootView.findViewById(R.id.recycle_playlist);
        mRecycleViewPlaylist.setLayoutManager(layoutPlaylist);
        mHomePlaylistAdapter = new HomePlaylistAdapter(mListPlaylist);
        mRecycleViewPlaylist.setNestedScrollingEnabled(false);
        mRecycleViewPlaylist.setAdapter(mHomePlaylistAdapter);

        mRecycleViewRecently = mRootView.findViewById(R.id.recycle_recently);
        mRecycleViewRecently.setNestedScrollingEnabled(false);
        mRecycleViewRecently.setLayoutManager(layoutRecently);
        mHomeRecentlyAdapter = new HomeRecentlyAdapter(mListSong);
        mRecycleViewRecently.setAdapter(mHomeRecentlyAdapter);
    }
}
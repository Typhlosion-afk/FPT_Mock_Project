package com.dore.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.dore.myapplication.R;
import com.dore.myapplication.adapter.SongsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SongFragment extends BaseFragment {

    private View mRootView;

    private ViewPager2 mViewPager;

    private TabLayout mTabLayout;

    private SongsPagerAdapter mSongsPagerAdapter;

    public SongFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_song;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;
        initView();
    }

    private void initView() {
        mViewPager = mRootView.findViewById(R.id.view_pager);
        mTabLayout = mRootView.findViewById(R.id.tab_layout);
        mSongsPagerAdapter = new SongsPagerAdapter(this);

        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setAdapter(mSongsPagerAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText("All Songs");
                    break;
                }
                case 1: {
                    tab.setText("Playlists");
                    break;
                }
                case 2: {
                    tab.setText("Albums");
                    break;
                }
                case 3: {
                    tab.setText("Artists");
                    break;
                }
                case 4: {
                    tab.setText("Genres");
                    break;
                }
            }
        }).attach();
    }
}
package com.dore.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

public class NowPlayingFragment extends Fragment {

    private View mRootView;

    private Song mSong;

    public NowPlayingFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_now_playing, container, false);
        initData();
        initView();
        handleAction();

        return mRootView;
    }

    private void initData(){
        if(getArguments() != null){
            mSong = (Song) getArguments().getSerializable("song");
        }
        if(mSong != null) {
            Log.d("TAG", "initData: " + mSong.getName());

        }else {
            Log.d("TAG", "initData: null");
        }

        mSong = new Song("Cháu đi mẫu giáo", "Bé Xuân Mai", R.raw.chau_di_mau_giao);
    }

    private void initView(){

    }

    private void handleAction(){

    }




}
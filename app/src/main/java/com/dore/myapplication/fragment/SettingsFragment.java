package com.dore.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dore.myapplication.R;

public class SettingsFragment extends Fragment {

    private View mRootView;

    private View cardDisplay;

    private View cardAudio;

    private View cardHeadset;

    private View cardLockscreen;

    private View cardAdvanced;

    private View cardOther;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_settings, container, false);
        iniView();
        initAction();

        return mRootView;

    }

    private void initAction() {
        cardDisplay.setOnClickListener(v -> {
            Log.d("TAG", "clicked display");
        });

        cardAudio.setOnClickListener(v -> {
            Log.d("TAG", "clicked audio");
        });

        cardHeadset.setOnClickListener(v -> {
            Log.d("TAG", "clicked headset");
        });

        cardLockscreen.setOnClickListener(v -> {
            Log.d("TAG", "clicked lockscreen");
        });

        cardAdvanced.setOnClickListener(v -> {
            Log.d("TAG", "clicked advanced");
        });

        cardOther.setOnClickListener(v -> {
            Log.d("TAG", "clicked other");
        });

    }

    private void iniView() {
        cardDisplay = mRootView.findViewById(R.id.setting_display);
        cardAudio = mRootView.findViewById(R.id.setting_audio);
        cardAdvanced = mRootView.findViewById(R.id.setting_advanced);
        cardHeadset = mRootView.findViewById(R.id.setting_headset);
        cardOther = mRootView.findViewById(R.id.setting_other);
        cardLockscreen = mRootView.findViewById(R.id.setting_lockscreen);
    }
}
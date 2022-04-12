package com.dore.myapplication.activity.screen.settings;

import android.util.Log;
import android.view.View;

import com.dore.myapplication.R;
import com.dore.myapplication.base.BaseFragment;

public class SettingsFragment extends BaseFragment {

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
    public int getLayoutID() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;

        iniView();
        initAction();
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
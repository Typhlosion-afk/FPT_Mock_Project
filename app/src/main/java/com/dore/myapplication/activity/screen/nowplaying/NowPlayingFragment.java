package com.dore.myapplication.activity.screen.nowplaying;

import static com.dore.myapplication.utilities.Constants.MAX_SEEKBAR_VALUE;

import android.content.Intent;

import android.view.View;
import android.widget.ImageButton;

import com.dore.myapplication.R;
import com.dore.myapplication.activity.MainActivity;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.customview.CirSeekBar;
import com.dore.myapplication.minterface.OnMediaRunning;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

public class NowPlayingFragment extends BaseFragment implements OnMediaRunning {

    private View mRootView;

    private Song mSong;

    private ImageButton btnNext;

    private ImageButton btnPrev;

    private ImageButton btnPlay;

    private boolean isPlaying;

    private MusicService mService;

    private int mCurPos = 0;

    private int mSongDur = MAX_SEEKBAR_VALUE;

    private CirSeekBar seekBar;

    public NowPlayingFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_now_playing;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;

        initBoundService();
        initData();
        initView();
        initAction();
        startService();
    }

    private void initBoundService() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.isBound()) {
            this.mService = mainActivity.getBoundService();
        }
    }

    private void initData() {
        if (getArguments() != null) {
            mSong = (Song) getArguments().getSerializable("song");
        }
        if (mSong != null) {
            //do something when song null
        } else {
            //so something when song
        }

        mSong = new Song("Cháu đi mẫu giáo", "Bé Xuân Mai", R.raw.chau_di_mau_giao);

    }

    private void startService() {
        if (mSong != null) {
            Intent i = new Intent(mRootView.getContext(), MusicService.class);
            i.putExtra("song", mSong);
            mRootView.getContext().startService(i);
            btnPlay.setImageResource(R.drawable.ic_control_pause);
        }
    }

    private void initView() {
        btnNext = mRootView.findViewById(R.id.btn_control_next);
        btnPrev = mRootView.findViewById(R.id.btn_control_prev);
        btnPlay = mRootView.findViewById(R.id.btn_control_play);

        seekBar = mRootView.findViewById(R.id.seek_bar);

    }

    private void initAction() {
        btnNext.setOnClickListener(v -> {
            next();
        });

        btnPlay.setOnClickListener(v -> {
            play();
        });

        btnPrev.setOnClickListener(v -> {
            prev();
        });

        seekBar.onChangeIndicatorPosition(percent -> {
            mSongDur = mService.getSongDur();
            mService.seekTo((int) (percent * mSongDur / 100));
        });

        mService.setCurPosListener(this);
    }

    private void play() {
        if (mService.isPlaying) {
            btnPlay.setImageResource(R.drawable.ic_control_play);
            mService.pauseSong();
        } else {
            btnPlay.setImageResource(R.drawable.ic_control_pause);
            mService.resumeSong();
        }
    }

    private void next() {

    }

    private void prev() {

    }

    private void seekTo(int cur) {

    }

    private void updateUiWithCur() {
        if (mSongDur != 0) {
            seekBar.setProgress((mCurPos * 1.0f / mSongDur) * 100);
        }
    }

    @Override
    public void sendPos(int pos) {
        mCurPos = pos;
        updateUiWithCur();
    }

    @Override
    public void sendDur(int duration) {
        mSongDur = duration;
    }
}
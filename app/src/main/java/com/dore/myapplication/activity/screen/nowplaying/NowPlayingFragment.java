package com.dore.myapplication.activity.screen.nowplaying;

import static com.dore.myapplication.utilities.Constants.MAX_SEEKBAR_VALUE;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dore.myapplication.R;
import com.dore.myapplication.activity.MainActivity;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.customview.CirSeekBar;
import com.dore.myapplication.minterface.OnMediaStateController;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingFragment extends BaseFragment implements OnMediaStateController {

    private View mRootView;

    private Song mSong;

    private ImageButton btnNext;

    private ImageButton btnPrev;

    private ImageButton btnPlay;

    private TextView txtSongName;

    private TextView txtSongAuthor;

    private TextView txtDurTime;

    private TextView txtCurTime;

    private boolean mIsPlaying;

    private MusicService mService;

    private int mSongCur = 0;

    private boolean isSeekBarTouching = false;

    private int mSongDur = MAX_SEEKBAR_VALUE;

    private CirSeekBar seekBar;

    private int mSongPos = 0;

    private List<Song> mListSong = new ArrayList<>();

    private MainActivity mainActivity;

    public NowPlayingFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_now_playing;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;

        initDataService();
        initView();
        initAction();
    }

    private void initDataService() {
        mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            this.mService = mainActivity.getBoundService();

            mSong = mService.getPlayingSong();
            mListSong = mService.getListSong();
            mIsPlaying = mService.isPlaying;
            mSongPos = mService.getPlayingSongPos();
            mSongDur = mService.getSongDur();

            mainActivity.hideController();
        }
    }

    private void initView() {
        btnNext = mRootView.findViewById(R.id.btn_control_next);
        btnPrev = mRootView.findViewById(R.id.btn_control_prev);
        btnPlay = mRootView.findViewById(R.id.btn_control_play);

        txtSongName = mRootView.findViewById(R.id.txt_playing_name);
        txtSongAuthor = mRootView.findViewById(R.id.txt_playing_detail);
        txtCurTime = mRootView.findViewById(R.id.txt_playing_cur);
        txtDurTime = mRootView.findViewById(R.id.txt_playing_dur);

        seekBar = mRootView.findViewById(R.id.seek_bar);

        // first state when create view for fragment
        if (mService != null && mSong != null) {
            txtSongName.setText(mSong.getName());
            txtSongAuthor.setText(mSong.getAuthor());

            btnPlay.setImageResource(mIsPlaying ? R.drawable.ic_control_pause : R.drawable.ic_control_play);

            updateUiWithCur();
        }

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

        seekBar.onChangeIndicatorPosition(new CirSeekBar.OnChangeIndicatorPosition() {
            @Override
            public void onChangingPos(float percent) {
                isSeekBarTouching = true;
                txtCurTime.setText(posToTime((int) (percent / 100 * mSongDur)));
            }

            @Override
            public void onChangedPos(float percent) {
                isSeekBarTouching = false;

                mSongDur = mService.getSongDur();
                mService.seekTo((int) (percent * mSongDur / 100));
            }
        });

        mService.setMediaControllerListener(this);
    }

    private void play() {
        if (mService.isPlaying) {
            btnPlay.setImageResource(R.drawable.ic_control_pause);
            mService.pauseSong();
        } else {
            btnPlay.setImageResource(R.drawable.ic_control_play);
            mService.resumeSong();
        }
    }

    private void next() {
        mService.nextSong();
    }

    private void prev() {
        mService.prevSong();
    }

    private void updateUiWithCur() {
        if (mSongDur != 0) {
            seekBar.setProgress((mSongCur * 1.0f / mSongDur) * 100);
            txtCurTime.setText(posToTime(mSongCur));
        }
    }

    @Override
    public void onDestroy() {
        mainActivity.showController();
        super.onDestroy();
    }

    private String posToTime(int pos) {
        int m = pos / 1000 / 60;
        int s = pos / 1000 % 60;
        String min = m < 10 ? "0" + m : "" + m;
        String sec = s < 10 ? "0" + s : "" + s;
        return min + ":" + sec;
    }

    @Override
    public void onChangeListSong(List<Song> songs) {
        mListSong = songs;
    }

    @Override
    public void onPlayNewSong(Song song, int pos) {
        txtSongName.setText(song.getName());
        txtSongAuthor.setText(song.getAuthor());
    }

    @Override
    public void onPlayingStateChange(boolean isPlaying) {
        if (isPlaying) {
            btnPlay.setImageResource(R.drawable.ic_control_pause);
        } else {
            btnPlay.setImageResource(R.drawable.ic_control_play);
        }
    }

    @Override
    public void onRunning(int cur) {
        if (!isSeekBarTouching) {
            mSongCur = cur;
            updateUiWithCur();
        }
    }

    @Override
    public void onDurationChange(int duration) {
        mSongDur = duration;
        txtDurTime.setText(posToTime(mSongDur));
    }

}
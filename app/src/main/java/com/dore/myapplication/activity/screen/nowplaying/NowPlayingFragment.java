package com.dore.myapplication.activity.screen.nowplaying;

import static com.dore.myapplication.utilities.Constants.LOCAL_BROADCAST_RECEIVER;
import static com.dore.myapplication.utilities.Constants.MAX_SEEKBAR_VALUE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.dore.myapplication.R;
import com.dore.myapplication.activity.MainActivity;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.customview.CirSeekBar;
import com.dore.myapplication.customview.MuzicVisualizer;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.ImageUtil;
import com.dore.myapplication.utilities.LogUtils;
import com.dore.myapplication.utilities.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingFragment extends BaseFragment{

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

    private BroadcastReceiver receiver;

    private ImageView mImgSong;

    private MuzicVisualizer muzicVisualizer;

    private int mAudioSession = -1;

    private TimeUtil mTimeUtil = new TimeUtil();

    private ImageUtil mImageUtil = new ImageUtil();

    public NowPlayingFragment() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_now_playing;
    }

    @Override
    public void onViewReady(View rootView) {

        mRootView = rootView;

        initBroadcast();
        initDataService();
        initView();
        initAction();
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager
                .getInstance(mRootView.getContext())
                .registerReceiver(receiver, new IntentFilter(LOCAL_BROADCAST_RECEIVER));

    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(mRootView.getContext()).unregisterReceiver(receiver);
        super.onStop();
    }

    private void initBroadcast(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                update((Song) intent.getSerializableExtra("song"),
                        intent.getIntExtra("dur", MAX_SEEKBAR_VALUE),
                        intent.getIntExtra("cur", 0),
                        intent.getBooleanExtra("playing", false),
                        intent.getIntExtra("session", - 1));

            }
        };
    }

    private void update(Song song, int dur, int cur, boolean playing, int audioSession){
        if(song != mSong){
            mSong = song;
            txtSongName.setText(mSong.getName());
            txtSongAuthor.setText(mSong.getAuthor());
            mSongDur = dur;
            txtDurTime.setText(mTimeUtil.posToTime(mSongDur));

            mImageUtil.showSongImage(mRootView.getContext(), mSong, mImgSong);
        }

        if (mIsPlaying != playing) {
            mIsPlaying = playing;
            btnPlay.setImageResource(mIsPlaying ? R.drawable.ic_control_pause : R.drawable.ic_control_play);
        }

        if (!isSeekBarTouching) {
            mSongCur = cur;
            updateUiWithCur();
        }

        if(mAudioSession != audioSession) {
            mAudioSession = audioSession;
            muzicVisualizer.setUpWithAudioSession(mAudioSession);
        }
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

        mImgSong = mRootView.findViewById(R.id.img_playing);

        seekBar = mRootView.findViewById(R.id.seek_bar);

        muzicVisualizer = mRootView.findViewById(R.id.muzicVisualizer);

        // first state when create view for fragment
        if (mService != null && mSong != null) {
            txtSongName.setText(mSong.getName());
            txtSongAuthor.setText(mSong.getAuthor());

            mImageUtil.showSongImage(mRootView.getContext(), mSong, mImgSong);

            btnPlay.setImageResource(mIsPlaying ? R.drawable.ic_control_pause : R.drawable.ic_control_play);
            txtDurTime.setText(mTimeUtil.posToTime(mSongDur));

            updateUiWithCur();
        }

    }

    private void initAction() {
        btnNext.setOnClickListener(v -> next());

        btnPlay.setOnClickListener(v -> play());

        btnPrev.setOnClickListener(v -> prev());

        seekBar.onChangeIndicatorPosition(new CirSeekBar.OnChangeIndicatorPosition() {
            @Override
            public void onChangingPos(float percent) {
                isSeekBarTouching = true;
                txtCurTime.setText(mTimeUtil.posToTime((int) (percent / 100 * mSongDur)));
            }

            @Override
            public void onChangedPos(float percent) {
                isSeekBarTouching = false;

                mSongDur = mService.getSongDur();
                mService.seekTo((int) (percent * mSongDur / 100));
            }
        });

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
            txtCurTime.setText(new TimeUtil().posToTime(mSongCur));
        }
    }

    @Override
    public void onDestroy() {
        mainActivity.showController();
        super.onDestroy();
    }

}
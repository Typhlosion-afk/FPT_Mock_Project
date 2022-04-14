package com.dore.myapplication.activity;

import static com.dore.myapplication.utilities.Constants.MAX_SEEKBAR_VALUE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dore.myapplication.R;
import com.dore.myapplication.minterface.OnMediaStateController;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMediaStateController {

    private EditText mEdtSearch;

    private ImageView mBtnDrawer;

    private ImageView mBtnSearch;

    private Boolean isDrawerShowing = false;

    private NavController mNavController;

    private NavigationView mDrawerNavigationView;

    private DrawerLayout mDrawerLayout;

    private MusicService mMusicService;

    private SeekBar mSeekBar;

    private View mControllerView;

    private ImageButton mBtnPrev;

    private ImageButton mBtnNext;

    private ImageButton mBtnPlay;

    private TextView mTxtSongName;

    private TextView mTxtSongAuthor;

    private int mSongCur = 0;

    private int mSongDur = MAX_SEEKBAR_VALUE;

    private int mSongPos = 0;

    private boolean mBound = false;

    private boolean mIsPlaying = false;

    private Song mSong;

    private boolean isSeekbarTouching = false;

    private List<Song> mListSong = new ArrayList<>();

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            mBound = true;

            mMusicService.setMediaControllerListener(MainActivity.this);
            mSong = mMusicService.getPlayingSong();
            if (mSong != null) {
                mIsPlaying = mMusicService.isPlaying;
                mSongDur = mMusicService.getSongDur();
                mSong = mMusicService.getPlayingSong();
                showController();
            } else {
                hideController();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBindService();
        initView();
        initNav();

        handleAction();
    }


    private void initBindService() {

        Intent iStartService = new Intent(this, MusicService.class);
        startService(iStartService);
        bindService(iStartService, connection, Context.BIND_AUTO_CREATE);

    }

    private void initView() {
        mEdtSearch = findViewById(R.id.edt_searcher);
        mBtnSearch = findViewById(R.id.btn_searcher);
        mBtnDrawer = findViewById(R.id.btn_nav_drawer);
        mSeekBar = findViewById(R.id.seek_bar);

        mTxtSongName = findViewById(R.id.txt_name);
        mTxtSongAuthor = findViewById(R.id.txt_author);

        mBtnPlay = findViewById(R.id.btn_control_play);
        mBtnNext = findViewById(R.id.btn_control_next);
        mBtnPrev = findViewById(R.id.btn_control_prev);

        mControllerView = findViewById(R.id.media_controller_view);

        mBtnSearch.setVisibility(View.VISIBLE);
        mEdtSearch.setVisibility(View.INVISIBLE);

        mSeekBar.setMax(mSongDur);
    }


    private void hideController() {
        mSeekBar.setVisibility(View.GONE);
        mControllerView.setVisibility(View.GONE);
    }

    private void showController() {
        mSeekBar.setVisibility(View.VISIBLE);
        mControllerView.setVisibility(View.VISIBLE);

        updateController();
    }

    private void updateController(){
        if (mIsPlaying) {
            mBtnPlay.setImageResource(R.drawable.ic_control_pause_small);
        } else {
            mBtnPlay.setImageResource(R.drawable.ic_control_play_small);
        }

        if(mSong != null) {
            mTxtSongName.setText(mSong.getName());
            mTxtSongAuthor.setText(mSong.getAuthor());

            mSeekBar.setProgress(mSongCur);
            mSeekBar.setMax(mSongDur);
        }

    }

    private void handleAction() {
        mBtnSearch.setOnClickListener(v -> {
            mBtnSearch.setVisibility(View.INVISIBLE);
            mEdtSearch.setVisibility(View.VISIBLE);
        });

        mBtnDrawer.setOnClickListener(v -> {

        });

        mControllerView.setOnClickListener(v -> {
            mNavController.navigate(R.id.action_play_song);
        });

        mBtnPlay.setOnClickListener(v -> {
            if (mMusicService != null) {
                if (mMusicService.isPlaying) {
                    mMusicService.pauseSong();
                } else {
                    mMusicService.resumeSong();
                }
            }
        });

        mBtnNext.setOnClickListener(v -> {
            if (mMusicService != null) {
                mMusicService.nextSong();
            }
        });

        mBtnPrev.setOnClickListener(v -> {
            if (mMusicService != null) {
                mMusicService.prevSong();
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekbarTouching = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSongCur = seekBar.getProgress();
                mMusicService.seekTo(mSongCur);
                isSeekbarTouching = false;
            }
        });
    }

    private void initNav() {
        BottomNavigationView bottomNavigationView = this.findViewById(R.id.bottom_nav);
        mDrawerNavigationView = this.findViewById(R.id.drawer_nav);
        mDrawerLayout = this.findViewById(R.id.layout_drawer);

        NavHostFragment navHostFragment = (NavHostFragment) this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();

            bottomNavigationView.setItemIconTintList(null);
            mDrawerNavigationView.setItemIconTintList(null);

            mNavController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
                        mEdtSearch.setVisibility(View.GONE);
                        mBtnSearch.setVisibility(View.VISIBLE);
                        if (navDestination.getId() == R.id.nowPlayingFragment) {
                            hideController();
                        } else {
                            showController();
                        }
                    }
            );

            NavigationUI.setupWithNavController(bottomNavigationView, mNavController);
            NavigationUI.setupWithNavController(mDrawerNavigationView, mNavController);
        }
    }

    public MusicService getBoundService() {
        return mMusicService;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerNavigationView.isShown()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }


    @Override
    public void onChangeListSong(List<Song> songs) {
        mListSong = songs;
    }

    @Override
    public void onPlayNewSong(Song song, int pos) {
        mSong = song;
        mSongPos = pos;

        mTxtSongName.setText(mSong.getName());
        mTxtSongAuthor.setText(mSong.getAuthor());
    }

    @Override
    public void onPlayingStateChange(boolean isPlaying) {
        mIsPlaying = isPlaying;

        if (mIsPlaying) {
            mBtnPlay.setImageResource(R.drawable.ic_control_pause_small);
        } else {
            mBtnPlay.setImageResource(R.drawable.ic_control_play_small);
        }
    }

    @Override
    public void onRunning(int cur) {
        if (!isSeekbarTouching) {
            mSongCur = cur;
            mSeekBar.setProgress(mSongCur);
        }
    }

    @Override
    public void onDurationChange(int duration) {
        mSongDur = duration;
        mSeekBar.setMax(mSongDur);
    }

}
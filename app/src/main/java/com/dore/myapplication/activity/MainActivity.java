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
import android.util.Log;
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

    private ImageButton btnPrev;

    private ImageButton btnNext;

    private ImageButton btnPlay;

    private TextView txtSongName;

    private TextView txtSongAuthor;

    private int mSongCur = 0;

    private int mSongDur = MAX_SEEKBAR_VALUE;

    private int mSongPos = 0;

    private boolean mBound = false;

    private boolean mIsPlaying = false;

    private Song mSong;

    private List<Song> mListSong = new ArrayList<>();

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            mBound = true;

            mMusicService.setCurPosListener(MainActivity.this);
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

        bindService();
        initView();
        handleAction();
        initNav();

    }


    private void bindService() {
        Intent iStartService = new Intent(this, MusicService.class);
        startService(iStartService);
        bindService(iStartService, connection, Context.BIND_AUTO_CREATE);

    }

    private void initView() {
        mEdtSearch = findViewById(R.id.edt_searcher);
        mBtnSearch = findViewById(R.id.btn_searcher);
        mBtnDrawer = findViewById(R.id.btn_nav_drawer);
        mSeekBar = findViewById(R.id.seek_bar);

        txtSongName = findViewById(R.id.txt_name);
        txtSongAuthor = findViewById(R.id.txt_author);

        btnPlay = findViewById(R.id.btn_control_play);
        btnNext = findViewById(R.id.btn_control_next);
        btnPrev = findViewById(R.id.btn_control_prev);

        mControllerView = findViewById(R.id.media_controller_view);
        mSeekBar.setMax(MAX_SEEKBAR_VALUE);

        mBtnSearch.setVisibility(View.VISIBLE);
        mEdtSearch.setVisibility(View.INVISIBLE);
    }

    private void setHideController() {
        mSeekBar.setVisibility(View.GONE);
        mControllerView.setVisibility(View.GONE);
    }

    private void setShowController() {
        mSeekBar.setVisibility(View.VISIBLE);
        mControllerView.setVisibility(View.VISIBLE);
    }

    private void handleAction() {
        mBtnSearch.setOnClickListener(v -> {
            mBtnSearch.setVisibility(View.INVISIBLE);
            mEdtSearch.setVisibility(View.VISIBLE);
        });

        mBtnDrawer.setOnClickListener(v -> {

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
                        if (navDestination.getId() == R.id.nowPlayingFragment || mSong == null) {
                            setHideController();
                        } else {
                            setShowController();
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

    public boolean isBound() {
        return mBound;
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
        super.onStop();
        unbindService(connection);
        mBound = false;
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

        txtSongName.setText(mSong.getName());
        txtSongAuthor.setText(mSong.getAuthor());
    }

    @Override
    public void onPlayingStateChange(boolean isPlaying) {
        mIsPlaying = isPlaying;

        if(mIsPlaying){
            btnPlay.setImageResource(R.drawable.ic_control_pause_small);
        }else{
            btnPlay.setImageResource(R.drawable.ic_control_play_small);
        }
    }

    @Override
    public void onRunning(int cur) {
        mSongCur = cur;
        mSeekBar.setProgress(mSongCur);
    }

    @Override
    public void onDurationChange(int duration) {
        mSongDur = duration;
        mSeekBar.setMax(mSongDur);
    }

}
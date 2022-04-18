package com.dore.myapplication.activity;

import static com.dore.myapplication.utilities.Constants.LOCAL_BROADCAST_RECEIVER;
import static com.dore.myapplication.utilities.Constants.MAX_SEEKBAR_VALUE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dore.myapplication.R;
import com.dore.myapplication.dialog.CloseAppDialogFragment;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

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

    private BroadcastReceiver receiver;

    private FragmentManager.BackStackEntry backStackEntry;

    private NavHostFragment navHostFragment;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            mBound = true;

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
        initBroadcast();
        initView();
        initNav();

        handleAction();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(LOCAL_BROADCAST_RECEIVER));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    private void initBindService() {

        Intent iStartService = new Intent(this, MusicService.class);
        startService(iStartService);
        bindService(iStartService, connection, Context.BIND_AUTO_CREATE);

    }

    private void initBroadcast(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                update((Song) intent.getSerializableExtra("song"),
                        intent.getIntExtra("dur", MAX_SEEKBAR_VALUE),
                        intent.getIntExtra("cur", 0),
                        intent.getBooleanExtra("playing", false));
            }
        };
    }

    private void update(Song song, int dur, int cur, boolean playing){
        if(song != mSong){
            mSong = song;
            mSongDur = dur;
            mTxtSongName.setText(mSong.getName());
            mTxtSongAuthor.setText(mSong.getAuthor());
            mSeekBar.setMax(mSongDur);
        }

        if (mIsPlaying != playing) {
            mIsPlaying = playing;
            mBtnPlay.setImageResource(mIsPlaying ? R.drawable.ic_control_pause_small : R.drawable.ic_control_play_small);
        }

        mSongCur = cur;
        mSeekBar.setProgress(mSongCur);
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

    private void handleBackStack(){

    }


    public void hideController() {
        mSeekBar.setVisibility(View.GONE);
        mControllerView.setVisibility(View.GONE);
    }

    public void showController() {
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

    @SuppressLint("NonConstantResourceId")
    private void initNav() {
        BottomNavigationView bottomNavigationView = this.findViewById(R.id.bottom_nav);
        mDrawerNavigationView = this.findViewById(R.id.drawer_nav);
        mDrawerLayout = this.findViewById(R.id.layout_drawer);

        navHostFragment = (NavHostFragment) this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();

            bottomNavigationView.setItemIconTintList(null);
            mDrawerNavigationView.setItemIconTintList(null);

            mNavController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
                        mEdtSearch.setVisibility(View.GONE);
                        mBtnSearch.setVisibility(View.VISIBLE);
                    }
            );

            navHostFragment.getChildFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                }
            });

            NavigationUI.setupWithNavController(bottomNavigationView, mNavController);
            NavigationUI.setupWithNavController(mDrawerNavigationView, mNavController);

            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()){
                    case R.id.homeFragment:
                        mNavController.navigate(R.id.action_goto_home);
                        break;

                    case R.id.songsFragment:
                        mNavController.navigate(R.id.action_goto_songs);
                        break;

                    case R.id.settingsFragment:
                        mNavController.navigate(R.id.action_goto_settings);
                        break;
                }
                return false;
            });

        }
    }

    public MusicService getBoundService() {
        return mMusicService;
    }


    @Override
    public void onBackPressed() {
        LogUtils.d("BackStack Count: " + navHostFragment.getChildFragmentManager().getBackStackEntryCount());
        if (mDrawerNavigationView.isShown()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if(navHostFragment.getChildFragmentManager().getBackStackEntryCount() == 0){
            CloseAppDialogFragment closeFm= new CloseAppDialogFragment();
            closeFm.show(getSupportFragmentManager(), null);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }

}
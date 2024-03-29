package com.dore.myapplication.activity;

import static com.dore.myapplication.utilities.Constants.LOCAL_BROADCAST_RECEIVER;
import static com.dore.myapplication.utilities.Constants.MAX_SEEKBAR_VALUE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dore.myapplication.R;
import com.dore.myapplication.model.ProviderDAO;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.ImageUtil;
import com.dore.myapplication.utilities.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static ProviderDAO providerDAO;

    @SuppressLint("StaticFieldLeak")
    public static NavController mainNavController;

    private EditText mEdtSearch;

    private ImageView mBtnDrawer;

    private ImageView mBtnSearch;

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

    private boolean mBound = false;

    private boolean mIsPlaying = false;

    private Song mSong;

    private boolean isSeekbarTouching = false;

    private BroadcastReceiver receiver;

    private NavHostFragment navHostFragment;

    private ImageView mImgSong;

    private int mBackCount = 0;

    private RecyclerView mSearchRecyclerView;

    private SearchAdapter mSearchAdapter;

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

        initDAO();
        initBindService();
        initBroadcast();
        initView();
        initNav();
        initSearchAdapter();

        handleAction();
        handleSearching();
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

    @Override
    protected void onDestroy() {
        unbindService(connection);
        providerDAO.closeCursor();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerNavigationView.isShown()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (navHostFragment.getChildFragmentManager().getBackStackEntryCount() == 0) {
            doubleBackPress();
        } else {
            super.onBackPressed();
        }
    }

    private void initBindService() {
        Intent iStartService = new Intent(this, MusicService.class);
        bindService(iStartService, connection, Context.BIND_AUTO_CREATE);
    }

    private void initBroadcast() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateController((Song) intent.getSerializableExtra("song"),
                        intent.getIntExtra("dur", MAX_SEEKBAR_VALUE),
                        intent.getIntExtra("cur", 0),
                        intent.getBooleanExtra("playing", false));
            }
        };
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

        mImgSong = findViewById(R.id.img_control_player);

        mControllerView = findViewById(R.id.media_controller_view);

        mSearchRecyclerView = findViewById(R.id.list_search_container);

        mBtnSearch.setVisibility(View.VISIBLE);
        mEdtSearch.setVisibility(View.INVISIBLE);
        mSearchRecyclerView.setVisibility(View.INVISIBLE);

        mSeekBar.setMax(mSongDur);
    }

    @SuppressLint("NonConstantResourceId")
    private void initNav() {
        BottomNavigationView bottomNavigationView = this.findViewById(R.id.bottom_nav);
        mDrawerNavigationView = this.findViewById(R.id.drawer_nav);
        mDrawerLayout = this.findViewById(R.id.layout_drawer);

        navHostFragment = (NavHostFragment) this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            mainNavController = navHostFragment.getNavController();

            bottomNavigationView.setItemIconTintList(null);
            mDrawerNavigationView.setItemIconTintList(null);

            mainNavController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
                        mEdtSearch.setVisibility(View.GONE);
                        mBtnSearch.setVisibility(View.VISIBLE);
                        mSearchRecyclerView.setVisibility(View.GONE);
                        closeKeyBoard();
                    }
            );

            NavigationUI.setupWithNavController(bottomNavigationView, mainNavController);
            NavigationUI.setupWithNavController(mDrawerNavigationView, mainNavController);

            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        mainNavController.navigate(R.id.action_goto_home);
                        break;

                    case R.id.songsFragment:
                        mainNavController.navigate(R.id.action_goto_songs);
                        break;

                    case R.id.settingsFragment:
                        mainNavController.navigate(R.id.action_goto_settings);
                        break;
                }
                return false;
            });

        }
    }

    private final ImageUtil mImageUtil = new ImageUtil();

    private void updateController(Song song, int dur, int cur, boolean playing) {
        if (song != mSong) {
            mSong = song;
            mSongDur = dur;
            mTxtSongName.setText(mSong.getName());
            mTxtSongAuthor.setText(mSong.getAuthor());
            mSeekBar.setMax(mSongDur);

            mImageUtil.showSongImage(this, mSong, mImgSong);
        }

        if (mIsPlaying != playing) {
            mIsPlaying = playing;
            mBtnPlay.setImageResource(mIsPlaying ? R.drawable.ic_control_pause_small : R.drawable.ic_control_play_small);
        }

        mSongCur = cur;
        if (!isSeekbarTouching) {
            mSeekBar.setProgress(mSongCur);
        }
    }

    private void setFirstStateController() {
        if (mIsPlaying) {
            mBtnPlay.setImageResource(R.drawable.ic_control_pause_small);
        } else {
            mBtnPlay.setImageResource(R.drawable.ic_control_play_small);
        }

        if (mSong != null) {
            mTxtSongName.setText(mSong.getName());
            mTxtSongAuthor.setText(mSong.getAuthor());
            mImageUtil.showSongImage(this, mSong, mImgSong);

            mSeekBar.setProgress(mSongCur);
            mSeekBar.setMax(mSongDur);
        }

    }

    private void handleAction() {
        mBtnSearch.setOnClickListener(v -> {
            mBtnSearch.setVisibility(View.INVISIBLE);
            mEdtSearch.setVisibility(View.VISIBLE);
            mEdtSearch.setText("");
            mEdtSearch.requestFocus();

            openKeyBoard();
        });

        mBtnDrawer.setOnClickListener(v -> {

        });

        mControllerView.setOnClickListener(v ->
                mainNavController.navigate(R.id.action_play_song));

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

    private void initSearchAdapter() {
        mSearchAdapter = new SearchAdapter(this, new ArrayList<>());
        mSearchRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        mSearchRecyclerView.setAdapter(mSearchAdapter);
    }

    private void doubleBackPress() {
        mBackCount++;
        if (mBackCount == 2) {
            super.onBackPressed();

        } else {
            Toast.makeText(this, "Press back again to close app", Toast.LENGTH_SHORT).show();
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    LogUtils.d("onFinish: ");
                    mBackCount = 0;
                }
            }.start();
        }
    }

    public void hideController() {
        mSeekBar.setVisibility(View.GONE);
        mControllerView.setVisibility(View.GONE);
    }

    public void showController() {
        mSeekBar.setVisibility(View.VISIBLE);
        mControllerView.setVisibility(View.VISIBLE);

        setFirstStateController();
    }

    private void initDAO() {
        providerDAO = new ProviderDAO(this);
    }

    private void handleSearching() {
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    mSearchAdapter.updateList(providerDAO.search(s.toString(), 10));
                    mSearchRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    mSearchRecyclerView.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void closeKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
    }

    private void openKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEdtSearch, InputMethodManager.SHOW_IMPLICIT);
    }

    public MusicService getBoundService() {
        return mMusicService;
    }
}
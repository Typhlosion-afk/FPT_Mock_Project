package com.dore.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.dore.myapplication.R;
import com.dore.myapplication.service.MusicService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private EditText mEdtSearch;

    private ImageView mBtnDrawer;

    private ImageView mBtnSearch;

    private Boolean isDrawerShowing = false;

    private NavController mNavController;

    private NavigationView mDrawerNavigationView;

    private DrawerLayout mDrawerLayout;

    private MusicService mMusicService;

    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        handleAction();
        initNav();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent iStartService = new Intent(this, MusicService.class);

        startService(iStartService);
        bindService(iStartService, connection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerNavigationView.isShown()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initView() {
        mEdtSearch = findViewById(R.id.edt_searcher);
        mBtnSearch = findViewById(R.id.btn_searcher);
        mBtnDrawer = findViewById(R.id.btn_nav_drawer);

        mBtnSearch.setVisibility(View.VISIBLE);
        mEdtSearch.setVisibility(View.INVISIBLE);

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

                    }
            );

            NavigationUI.setupWithNavController(bottomNavigationView, mNavController);
            NavigationUI.setupWithNavController(mDrawerNavigationView, mNavController);
        }
    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public MusicService getBoundService(){
        return mMusicService;
    }

    public boolean isBound(){
        return mBound;
    }

}
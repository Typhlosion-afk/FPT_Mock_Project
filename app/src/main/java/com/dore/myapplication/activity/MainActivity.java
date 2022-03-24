package com.dore.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.dore.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private EditText mEdtSearch;
    private ImageView mBtnDrawer;
    private ImageView mBtnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("TAG", "onCreate: ");

        initView();
        handleAction();
        initBottomNav();
    }

    private void initView() {
        mEdtSearch = findViewById(R.id.edt_searcher);
        mBtnSearch = findViewById(R.id.btn_searcher);
        mBtnDrawer = findViewById(R.id.btn_nav_drawer);

        mBtnSearch.setVisibility(View.VISIBLE);
        mEdtSearch.setVisibility(View.INVISIBLE);

    }

    private void handleAction(){
        mBtnSearch.setOnClickListener(v -> {
            mBtnSearch.setVisibility(View.INVISIBLE);
            mEdtSearch.setVisibility(View.VISIBLE);
        });

        mBtnDrawer.setOnClickListener(v -> {

        });
    }

    private void initBottomNav() {
        BottomNavigationView bottomNavigationView = this.findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            mEdtSearch.setVisibility(View.GONE);
            mBtnSearch.setVisibility(View.VISIBLE);
        });
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
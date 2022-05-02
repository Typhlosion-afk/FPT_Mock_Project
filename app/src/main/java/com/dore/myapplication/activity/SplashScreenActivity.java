package com.dore.myapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermission();

    }

    private void requestPermission(){
        String[] listPms = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };

        if(ContextCompat.checkSelfPermission(this, listPms[0]) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, listPms[1]) == PackageManager.PERMISSION_GRANTED){

            startActivity(new Intent(this, MainActivity.class));
            this.finish();

        }else {
            ActivityCompat.requestPermissions(this, listPms, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Please, Accept permission to continue", Toast.LENGTH_LONG).show();
            }else {
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
            }
        }
    }

}
package com.dore.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dore.myapplication.activity.MainActivity;
import com.dore.myapplication.model.ProviderDAO;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.Constants;
import com.dore.myapplication.utilities.LogUtils;

import java.sql.Connection;

public class MusicApplication extends Application {

    public static ProviderDAO providerDAO;

    @Override
    public void onCreate() {
        super.onCreate();
        createChannelNotification();
    }

    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    Constants.NOTIFICATION_CHANNEL_ID,
                    "Muzic Player",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel.setSound(null, null);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }

}

package com.dore.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

import com.dore.myapplication.activity.MainActivity;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.Constants;
import com.dore.myapplication.utilities.LogUtils;

import java.sql.Connection;

public class MusicApplication extends Application {

    private boolean mBound = false;

    private MusicService mMusicService;

    public final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            mBound = true;
            LogUtils.d("connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        createChannelNotification();
//        bindService();
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
//
//    private void bindService() {
//        Intent iStartService = new Intent(this, MusicService.class);
//        startService(iStartService);
//        bindService(iStartService, connection, Context.BIND_AUTO_CREATE);
//
//    }
//
//    public MusicService getBoundService(){
//        return mMusicService;
//    }
//
//    public ServiceConnection getConnection(){
//        return connection;
//    }
}

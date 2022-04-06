package com.dore.myapplication.service;

import static com.dore.myapplication.utilities.Constants.ACTION_CLOSE;
import static com.dore.myapplication.utilities.Constants.ACTION_NEXT;
import static com.dore.myapplication.utilities.Constants.ACTION_PLAY;
import static com.dore.myapplication.utilities.Constants.ACTION_PREV;
import static com.dore.myapplication.utilities.Constants.NOTIFICATION_DATA_ACTION;
import static com.dore.myapplication.utilities.Constants.ONGOING_NOTIFICATION_ID;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dore.myapplication.model.Song;
import com.dore.myapplication.notification.MusicNotificationManager;
import com.dore.myapplication.utilities.Constants;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

    private MusicNotificationManager mMusicNotiManager;

    private Notification mPlayerNotification;

    private MediaPlayer mMediaPlayer;

    private Song mSong;

    private final IBinder binder = new MusicBinder();

    private void initNotification(){
        mMusicNotiManager = new MusicNotificationManager(this);
        mPlayerNotification = mMusicNotiManager.getPlayerNotification();

    }

    private void handleActionNotification(int action) {
        switch (action) {
            case ACTION_PLAY: {
                Log.d("TAG", "onStartCommand: play");
                break;
            }
            case ACTION_CLOSE: {
                Log.d("TAG", "onStartCommand: close");
                stop();
                break;
            }
            case ACTION_NEXT: {
                Log.d("TAG", "onStartCommand: next");
                break;
            }
            case ACTION_PREV: {
                Log.d("TAG", "onStartCommand: prev");
                break;
            }
            default: {
                Log.d("TAG", "onStartCommand: unknown");

            }
        }
    }

    private void initMediaPlayer(){
        if(mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    private void playSong(){
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(this, mSong.getRes());
        mMediaPlayer.start();
    }

    private void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        stopSelf();

    }

    @Override
    public void onCreate() {
        super.onCreate();

        initMediaPlayer();
        initNotification();

        if(mSong != null){
            startForeground();
        }
    }

    private void startForeground(){
        startForeground(ONGOING_NOTIFICATION_ID, mPlayerNotification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "service onStartCommand");

        if (intent != null) {
            handleActionNotification(intent.getIntExtra(NOTIFICATION_DATA_ACTION, -1));

            if (intent.getSerializableExtra("song") != null) {
                mSong = (Song) intent.getSerializableExtra("song");
                playSong();
                mMusicNotiManager.updateViewNotification(mSong);
            }
        }

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.d("TAG", "service onDestroy");
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    public class MusicBinder extends Binder{

        public MusicService getService(){
             return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TAG", "service onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("TAG", "service onUnbind");
        return super.onUnbind(intent);
    }



}

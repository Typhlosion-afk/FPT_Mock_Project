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
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dore.myapplication.minterface.OnMediaRunning;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.notification.MusicNotificationManager;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

    private MusicNotificationManager mMusicNotiManager;

    private Notification mPlayerNotification;

    private MediaPlayer mMediaPlayer;

    private Song mSong;

    private final IBinder binder = new MusicBinder();

    public boolean isPlaying = false;

    private int mCurPosition = 0;

    private OnMediaRunning onMediaRunning;

    private Handler h = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();

        initMediaPlayer();
        initNotification();

        if (mSong != null) {
            startForeground(mSong);
        }


        Runnable r = new Runnable() {
            @Override
            public void run() {
//                Log.d("onMediaRunning", "run: " + onMediaRunning);
//                if(onMediaRunning != null){
//
//                    onMediaRunning.sendPos(mCurPosition);
//                    onMediaRunning.sendDur(1000);
//                    mCurPosition += 1;
//                }
//                h.postDelayed(this, 100);

                if(mMediaPlayer != null && onMediaRunning != null && isPlaying){

                    onMediaRunning.sendPos(mMediaPlayer.getCurrentPosition());
                    onMediaRunning.sendDur(mMediaPlayer.getDuration());
                }
                h.postDelayed(this, 100);
            }
        };
        new Thread(r).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "service onStartCommand");

        if (intent != null) {
            int action = intent.getIntExtra(NOTIFICATION_DATA_ACTION, -1);
            Log.d("TAG", "onStartCommand: " + action);
            switch (action) {
                case ACTION_PLAY: {
                    if (!isPlaying) {
                        resumeSong();
                        Log.d("TAG", "onStartCommand: resume");

                    } else {
                        pauseSong();
                        Log.d("TAG", "onStartCommand: pause");

                    }
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

            if (intent.getSerializableExtra("song") != null) {
                mSong = (Song) intent.getSerializableExtra("song");
                playSong();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("TAG", "service onDestroy");

        super.onDestroy();
    }


    private void initNotification() {
        mMusicNotiManager = new MusicNotificationManager(this);
        mPlayerNotification = mMusicNotiManager.getPlayerNotification();

    }

    private void handleActionNotification(int action) {

    }

    private void initMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    public void playSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = MediaPlayer.create(this, mSong.getRes());
            mMediaPlayer.start();
            isPlaying = true;
            updateNotification();
        }
    }

    public void pauseSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mCurPosition = mMediaPlayer.getCurrentPosition();
            isPlaying = false;
            updateNotification();
        }

    }

    public void resumeSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(mCurPosition);
            mMediaPlayer.start();
            isPlaying = true;
            updateNotification();
        }

    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mCurPosition = 0;
            isPlaying = false;

            onMediaRunning.sendPos(mMediaPlayer.getCurrentPosition());

        }
        mMusicNotiManager.setIsForeRunning(false);
        mMusicNotiManager.updateViewNotification(mSong);
        mMusicNotiManager.removeNotification();
        this.stopSelf();
    }

    private void updateNotification() {
        mMusicNotiManager.setIsMediaPlaying(mMediaPlayer.isPlaying());
        mMusicNotiManager.setIsMediaPlaying(isPlaying);
        mMusicNotiManager.updateViewNotification(mSong);
    }


    public void startForeground(Song song) {
        mSong = song;
        startForeground(ONGOING_NOTIFICATION_ID, mPlayerNotification);
    }

    public void setCurPosListener(OnMediaRunning listener){
        this.onMediaRunning = listener;
    }

    public int getSongDur(){
        return mMediaPlayer == null ? 0 : mMediaPlayer.getDuration();
    }

    public void seekTo(int mSes) {
        mMediaPlayer.seekTo(mSes);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
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

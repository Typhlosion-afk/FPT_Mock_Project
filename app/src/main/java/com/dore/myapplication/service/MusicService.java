package com.dore.myapplication.service;

import static com.dore.myapplication.utilities.Constants.ACTION_CLOSE;
import static com.dore.myapplication.utilities.Constants.ACTION_NEXT;
import static com.dore.myapplication.utilities.Constants.ACTION_PLAY;
import static com.dore.myapplication.utilities.Constants.ACTION_PREV;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;
import static com.dore.myapplication.utilities.Constants.MAX_SEEKBAR_VALUE;
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

import com.dore.myapplication.minterface.OnMediaStateController;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.notification.MusicNotificationManager;
import com.dore.myapplication.utilities.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener{

    private final IBinder binder = new MusicBinder();

    private MusicNotificationManager mMusicNotiManager;

    private Notification mPlayerNotification;

    private MediaPlayer mMediaPlayer;

    private Song mSong;

    private List<Song> mListSong = new ArrayList<>();

    private int mSongPos = 0;

    private int mCurrent = 0;

    public boolean isPlaying = false;

    private final List<OnMediaStateController> onMediaStateController = new ArrayList<>();

    private final Handler h = new Handler();

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
                if(mMediaPlayer != null && onMediaStateController.size() != 0 && isPlaying){
                    for (OnMediaStateController listen : onMediaStateController) {
                        listen.onRunning(mMediaPlayer.getCurrentPosition());
                    }
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

            handleActionNotification(action);

            if (intent.getSerializableExtra(KEY_SONG_POSITION) != null
                    && intent.getIntExtra(KEY_SONG_POSITION,-1) != -1) {

                mListSong.addAll((List<Song>) intent.getSerializableExtra(KEY_SONG_LIST));
                mSongPos = intent.getIntExtra(KEY_SONG_POSITION,-1);
                mSong = mListSong.get(mSongPos);
                playSong();
            }
        }
        return START_NOT_STICKY;
    }

    private void initNotification() {
        mMusicNotiManager = new MusicNotificationManager(this);
        mPlayerNotification = mMusicNotiManager.getPlayerNotification();

    }

    private void handleActionNotification(int action) {
        switch (action) {
            case ACTION_PLAY: {
                if (!isPlaying) {
                    resumeSong();
                } else {
                    pauseSong();
                }
                break;
            }
            case ACTION_CLOSE: {
                stopSong();
                break;
            }
            case ACTION_NEXT: {
                nextSong();
                break;
            }
            case ACTION_PREV: {
                prevSong();
                break;
            }
            default: {
                Log.d("TAG", "onStartCommand: unknown");
            }
        }
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
            isPlaying = false;
            mMediaPlayer.release();
            mMediaPlayer = MediaPlayer.create(this, mSong.getRes());

            initMediaPlayer();

            isPlaying = true;

            LogUtils.d("Song: " + mSongPos);
            LogUtils.d("Song: " + mSong.getName());

            for (OnMediaStateController listen : onMediaStateController) {
                listen.onPlayNewSong(mSong, mSongPos);
                listen.onPlayingStateChange(isPlaying);
                listen.onDurationChange(mMediaPlayer.getDuration());
                listen.onPlayingStateChange(isPlaying);
            }

            updateNotification();
        }
    }

    public void pauseSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mCurrent = mMediaPlayer.getCurrentPosition();
            isPlaying = false;

            for (OnMediaStateController listen : onMediaStateController) {
                listen.onPlayingStateChange(isPlaying);
            }

            updateNotification();
        }

    }

    public void resumeSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(mCurrent);
            mMediaPlayer.start();
            isPlaying = true;

            for (OnMediaStateController listen : onMediaStateController) {
                listen.onPlayingStateChange(isPlaying);
            }

            updateNotification();
        }

    }

    public void nextSong() {
        if(mSongPos < mListSong.size() - 1) {
            mSongPos++;
        }else {
            mSongPos = 0;
        }
        mSong = mListSong.get(mSongPos);
        playSong();
    }

    public void prevSong(){
        if(mSongPos > 0) {
            mSongPos --;
        }else {
            mSongPos = mListSong.size() - 1;
        }
        mSong = mListSong.get(mSongPos);
        playSong();
    }

    public void stopSong() {
        if (mMediaPlayer != null) {
            isPlaying = false;
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrent = 0;
        }
        mMusicNotiManager.setIsForeRunning(false);
        mMusicNotiManager.updateViewNotification(mSong);
        mMusicNotiManager.removeNotification();
        this.stopSelf();
    }

    public int getSongDur(){
        return mMediaPlayer == null ? MAX_SEEKBAR_VALUE : mMediaPlayer.getDuration();
    }

    public void seekTo(int mSes) {
        mMediaPlayer.seekTo(mSes);
        mCurrent = mSes;
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

    public void setCurPosListener(OnMediaStateController listener){
        this.onMediaStateController.add(listener);
    }

    public Song getPlayingSong(){
        return mSong;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TAG", "onBind: ");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("TAG", "reBind: ");

        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("TAG", "unBind: ");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        nextSong();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        LogUtils.d("on pre");
        mp.start();
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

}

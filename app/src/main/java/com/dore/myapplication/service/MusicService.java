package com.dore.myapplication.service;

import static com.dore.myapplication.utilities.Constants.ACTION_CLOSE;
import static com.dore.myapplication.utilities.Constants.ACTION_NEXT;
import static com.dore.myapplication.utilities.Constants.ACTION_PLAY;
import static com.dore.myapplication.utilities.Constants.ACTION_PREV;
import static com.dore.myapplication.utilities.Constants.GLOBAL_BROADCAST_RECEIVER;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;
import static com.dore.myapplication.utilities.Constants.LOCAL_BROADCAST_RECEIVER;
import static com.dore.myapplication.utilities.Constants.MAX_SEEKBAR_VALUE;
import static com.dore.myapplication.utilities.Constants.NOTIFICATION_DATA_ACTION;
import static com.dore.myapplication.utilities.Constants.ONGOING_NOTIFICATION_ID;
import static com.dore.myapplication.utilities.Constants.WIDGET_DATA_ACTION;

import android.app.Notification;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.notification.MusicNotificationManager;
import com.dore.myapplication.utilities.LogUtils;
import com.dore.myapplication.widget.MuzicAppWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private final Handler mHandler = new Handler();

    private Runnable mRunnable;

    private MuzicAppWidget mMuzicAppWidget;

    private LocalBroadcastManager mBroadcaster;

    private Intent mSongDataIntent = new Intent(LOCAL_BROADCAST_RECEIVER);

    private boolean isForegroundRunning = false;

    private boolean isBinding = false;


    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.d("On Create");

        mBroadcaster = LocalBroadcastManager.getInstance(this);

        initMediaPlayer();
        initNotification();

        if (mSong != null) {
            startForeground(mSong);
        }

        mRunnable = () -> {
            if (mMediaPlayer != null && isPlaying) {
                mSongDataIntent.putExtra("cur", mMediaPlayer.getCurrentPosition());
                mBroadcaster.sendBroadcast(mSongDataIntent);

            }
            mHandler.postDelayed(mRunnable, 1000);
        };

        startSongRunnable();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int notificationAction = intent.getIntExtra(NOTIFICATION_DATA_ACTION, -1);
            int widgetAction = intent.getIntExtra(WIDGET_DATA_ACTION, -1);

            if(notificationAction != - 1) {
                handleAction(notificationAction);
            }

            if(widgetAction != -1){
                handleAction(widgetAction);
            }

            if (intent.getSerializableExtra(KEY_SONG_LIST) != null
                    && intent.getIntExtra(KEY_SONG_POSITION,-1) != -1) {

                if(mListSong != intent.getSerializableExtra(KEY_SONG_LIST)) {
                    mListSong = (List<Song>) intent.getSerializableExtra(KEY_SONG_LIST);
                }

                mSongPos = intent.getIntExtra(KEY_SONG_POSITION,-1);
                mSong = mListSong.get(mSongPos);

                playSong();
            }
        }
        return START_NOT_STICKY;
    }

    private void sendSongData(){
        mSongDataIntent.putExtra("song", mSong);
        mSongDataIntent.putExtra("dur", mMediaPlayer.getDuration());
        mSongDataIntent.putExtra("playing", isPlaying);
        mSongDataIntent.putExtra("cur", mMediaPlayer.getCurrentPosition());

        mBroadcaster.sendBroadcast(mSongDataIntent);
    }

    private void initNotification() {
        mMusicNotiManager = new MusicNotificationManager(this);
        mPlayerNotification = mMusicNotiManager.getPlayerNotification();
    }

    private void handleAction(int action) {
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
                LogUtils.d("onStartCommand: unknown");
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
            mMediaPlayer = MediaPlayer.create(this, mSong.getUri());

            initMediaPlayer();

            isPlaying = true;

            LogUtils.d("Song: " + mSongPos);
            LogUtils.d("Song: " + mSong.getName());

            updateNotification();
            updateWidget();
            sendSongData();
        }
    }

    public void pauseSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mCurrent = mMediaPlayer.getCurrentPosition();
            isPlaying = false;

            updateNotification();
            updateWidget();
            sendSongData();
        }

    }

    public void resumeSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(mCurrent);
            mMediaPlayer.start();
            isPlaying = true;

            updateNotification();
            updateWidget();
            sendSongData();
        }
    }

    public void nextSong() {
        if(mSongPos < mListSong.size() - 1) {
            mSongPos++;
        }else {
            mSongPos = 0;
        }
        LogUtils.d("pos" + mSongPos);
        LogUtils.d("list size" + mListSong.size());

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
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mCurrent = 0;
        }
        mMusicNotiManager.setIsForeRunning(false);
        mMusicNotiManager.updateViewNotification(mSong);
        mMusicNotiManager.removeNotification();

        stopMuzicForeground();

        if(!isBinding){
            stopSelf();
        }else {
            mMediaPlayer = MediaPlayer.create(this, mSong.getUri());
            updateWidget();
            sendSongData();
        }

        stopSongRunnable();

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

    private void updateWidget(){
        Intent mSongDataWidgetIntent = new Intent(this, MuzicAppWidget.class);
        mSongDataWidgetIntent.setAction(GLOBAL_BROADCAST_RECEIVER);

        mSongDataWidgetIntent.putExtra("song", mSong);
        mSongDataWidgetIntent.putExtra("playing", isPlaying);

        int[] ids = AppWidgetManager.getInstance(getApplicationContext())
                .getAppWidgetIds(new ComponentName(getApplicationContext(), MuzicAppWidget.class));
        mSongDataWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        sendBroadcast(mSongDataWidgetIntent);
    }

    public void startForeground(Song song) {
        mSong = song;
        startForeground(ONGOING_NOTIFICATION_ID, mPlayerNotification);
        isForegroundRunning = true;
    }

    private void stopMuzicForeground(){
        isForegroundRunning = false;
        stopForeground(true);
    }

    public Song getPlayingSong(){
        return mSong;
    }

    public List<Song> getListSong(){
        return mListSong;
    }

    public int getPlayingSongPos(){
        return mSongPos;
    }

    private void startSongRunnable(){
        mRunnable.run();
    }

    private void stopSongRunnable(){
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onDestroy() {
        LogUtils.d("Service Destroy");
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        stopSongRunnable();

        super.onDestroy();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        nextSong();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        LogUtils.d("Prepared");
        mp.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
//        nextSong();
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
        LogUtils.d("onBind");
        isBinding = true;
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
        isBinding = false;
        return super.onUnbind(intent);
    }
}

package com.dore.myapplication.notification;

import static com.dore.myapplication.utilities.Constants.ACTION_NEXT;
import static com.dore.myapplication.utilities.Constants.ACTION_OPEN_APP;
import static com.dore.myapplication.utilities.Constants.ACTION_PLAY;
import static com.dore.myapplication.utilities.Constants.ACTION_PREV;
import static com.dore.myapplication.utilities.Constants.ACTION_CLOSE;
import static com.dore.myapplication.utilities.Constants.NOTIFICATION_CHANNEL_ID;
import static com.dore.myapplication.utilities.Constants.NOTIFICATION_DATA_ACTION;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dore.myapplication.R;
import com.dore.myapplication.activity.MainActivity;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MusicNotificationManager {

    private Context mContext;

    public NotificationManagerCompat mNotificationManagerCompat;

    private RemoteViews mLargeNotificationLayout;

    private RemoteViews mSmallNotificationLayout;

    private NotificationCompat.Builder builder;

    private Notification playerNotification;

    private boolean isMediaPlaying;
    private boolean mIsRunning = true;

    private NotificationTarget imgTarget;


    public MusicNotificationManager(Context context) {
        this.mContext = context;
        isMediaPlaying = true;
        mNotificationManagerCompat = NotificationManagerCompat.from(mContext);

        playerNotification = createPlayerNotification();
        playerNotification.sound = null;
    }

    public Notification getPlayerNotification() {
        return playerNotification;
    }

    public void setIsMediaPlaying(boolean isPlaying) {
        this.isMediaPlaying = isPlaying;
    }

    public void updateViewNotification(Song song) {
        if (song != null) {
            mLargeNotificationLayout.setTextViewText(R.id.txt_noti_song_name, song.getName());
            mLargeNotificationLayout.setTextViewText(R.id.txt_noti_author, song.getAuthor());
            if (isMediaPlaying) {
                mLargeNotificationLayout.setImageViewResource(R.id.btn_control_play, R.drawable.ic_control_pause);
            } else {
                mLargeNotificationLayout.setImageViewResource(R.id.btn_control_play, R.drawable.ic_control_play);
            }

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(mContext.getApplicationContext().getContentResolver(), Uri.parse(song.getImgPath()));
                mLargeNotificationLayout.setImageViewBitmap(R.id.notification_song_img, bm);
            } catch (IOException e) {
                mLargeNotificationLayout.setImageViewResource(R.id.notification_song_img, R.drawable.img_bg_playlist_default);

            } finally {
                mSmallNotificationLayout.setTextViewText(R.id.txt_noti_song_name, song.getName());
                mSmallNotificationLayout.setTextViewText(R.id.txt_noti_author, song.getAuthor());
                mNotificationManagerCompat.notify(1, builder.build());
            }
        }
    }

    private Notification createPlayerNotification() {
        mLargeNotificationLayout = new RemoteViews(mContext.getPackageName(),
                R.layout.notification_music_large);

        mSmallNotificationLayout = new RemoteViews(mContext.getPackageName(),
                R.layout.notification_music_small);

        mLargeNotificationLayout.setOnClickPendingIntent(R.id.btn_noti_close, getNotiPendingIntent(ACTION_CLOSE));
        mLargeNotificationLayout.setOnClickPendingIntent(R.id.btn_control_prev, getNotiPendingIntent(ACTION_PREV));
        mLargeNotificationLayout.setOnClickPendingIntent(R.id.btn_control_next, getNotiPendingIntent(ACTION_NEXT));
        mLargeNotificationLayout.setOnClickPendingIntent(R.id.btn_control_play, getNotiPendingIntent(ACTION_PLAY));
//        mLargeNotificationLayout.setOnClickPendingIntent(R.id.root, getOpenAppIntent());

        builder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti_small)
                .setCustomContentView(mSmallNotificationLayout)
                .setCustomBigContentView(mLargeNotificationLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(mIsRunning)
                .setOnlyAlertOnce(true);

        return builder.build();
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getNotiPendingIntent(int action) {
        Log.d("TAG", "getNotiPendingIntent: " + action);
        Intent i = new Intent(mContext, MusicService.class);
        i.putExtra(NOTIFICATION_DATA_ACTION, action);
        return PendingIntent.getService(mContext, action, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getOpenAppIntent() {
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mContext);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(new Intent());
//        taskStackBuilder.addNextIntent()
        return taskStackBuilder.getPendingIntent(ACTION_OPEN_APP, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void setIsForeRunning(boolean isForeRunning) {
        mIsRunning = isForeRunning;
    }

    public void removeNotification() {
        mNotificationManagerCompat.cancelAll();
    }
}

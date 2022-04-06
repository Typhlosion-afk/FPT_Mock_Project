package com.dore.myapplication.notification;

import static com.dore.myapplication.utilities.Constants.ACTION_NEXT;
import static com.dore.myapplication.utilities.Constants.ACTION_PLAY;
import static com.dore.myapplication.utilities.Constants.ACTION_PREV;
import static com.dore.myapplication.utilities.Constants.ACTION_CLOSE;
import static com.dore.myapplication.utilities.Constants.NOTIFICATION_CHANNEL_ID;
import static com.dore.myapplication.utilities.Constants.NOTIFICATION_DATA_ACTION;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

public class MusicNotificationManager {

    private Context mContext;

    public NotificationManagerCompat mNotificationManagerCompat;

    private RemoteViews mLargeNotificationLayout;

    private RemoteViews mSmallNotificationLayout;

    private NotificationCompat.Builder builder;

    private Notification playerNotification;

    private boolean isMediaPlaying = false;


    public MusicNotificationManager(Context context){
        this.mContext = context;
        isMediaPlaying = true;
        mNotificationManagerCompat = NotificationManagerCompat.from(mContext);

        playerNotification = createPlayerNotification();
    }

    public Notification getPlayerNotification(){
        return playerNotification;
    }

    public void setIsMediaPlaying(boolean isPlaying){
        this.isMediaPlaying = isPlaying;
    }

    public void updateViewNotification(Song song){
        mLargeNotificationLayout.setTextViewText(R.id.txt_noti_song_name, song.getName());
        mLargeNotificationLayout.setTextViewText(R.id.txt_noti_author, song.getAuthor());

        mSmallNotificationLayout.setTextViewText(R.id.txt_noti_song_name, song.getName());
        mSmallNotificationLayout.setTextViewText(R.id.txt_noti_author, song.getAuthor());

        mNotificationManagerCompat.notify(1, builder.build());
    }

    private Notification createPlayerNotification(){

        mLargeNotificationLayout = new RemoteViews(mContext.getPackageName(),
                R.layout.notification_music_large);

        mSmallNotificationLayout = new RemoteViews(mContext.getPackageName(),
                R.layout.notification_music_small);

        mLargeNotificationLayout.setOnClickPendingIntent(R.id.btn_noti_prev, getNotiPendingIntent(ACTION_PREV));
        mLargeNotificationLayout.setOnClickPendingIntent(R.id.btn_noti_next, getNotiPendingIntent(ACTION_NEXT));
        mLargeNotificationLayout.setOnClickPendingIntent(R.id.btn_noti_play, getNotiPendingIntent(ACTION_PLAY));
        mLargeNotificationLayout.setOnClickPendingIntent(R.id.btn_noti_close, getNotiPendingIntent(ACTION_CLOSE));


        builder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti_small)
                .setCustomContentView(mSmallNotificationLayout)
                .setCustomBigContentView(mLargeNotificationLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(isMediaPlaying)
                .setOnlyAlertOnce(true);

        return builder.build();
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getNotiPendingIntent(int action) {
        Intent i = new Intent(mContext, MusicService.class);
        i.putExtra(NOTIFICATION_DATA_ACTION, action);
        return PendingIntent.getService(mContext, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}

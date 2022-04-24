package com.dore.myapplication.widget;

import static com.dore.myapplication.utilities.Constants.ACTION_NEXT;
import static com.dore.myapplication.utilities.Constants.ACTION_PLAY;
import static com.dore.myapplication.utilities.Constants.ACTION_PREV;
import static com.dore.myapplication.utilities.Constants.WIDGET_DATA_ACTION;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.LogUtils;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MuzicAppWidget extends AppWidgetProvider {

    private RemoteViews mRemoteViews;

    private Context mContext;

    private boolean isEnabled = false;

    private Song mSong;

    private int[] mIds;

    private AppWidgetManager manager;

    private BroadcastReceiver broadcastReceiver;

    private boolean isPlaying = false;

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        mContext = context;

        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.muzic_app_widget);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_widget_prev, getNotiPendingIntent(ACTION_PREV));
        mRemoteViews.setOnClickPendingIntent(R.id.btn_widget_next, getNotiPendingIntent(ACTION_NEXT));
        mRemoteViews.setOnClickPendingIntent(R.id.btn_widget_play, getNotiPendingIntent(ACTION_PLAY));

        if(mSong != null) {
            mRemoteViews.setTextViewText(R.id.txt_widget_song_name, mSong.getName());
            mRemoteViews.setTextViewText(R.id.txt_widget_author, mSong.getAuthor());
            mRemoteViews.setImageViewResource(
                    R.id.btn_widget_play,
                    isPlaying ? R.drawable.ic_control_pause :  R.drawable.ic_control_play);
        }

        LogUtils.d("updateAppWidget ID: " + appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getNotiPendingIntent(int action) {
        Intent i = new Intent(mContext, MusicService.class);
        Log.d("TAG", "widget: " + action);
        i.putExtra(WIDGET_DATA_ACTION, action);
        return PendingIntent.getService(mContext, action, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        manager = appWidgetManager;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        LogUtils.d("on disable");

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        LogUtils.d("on disable");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mSong = (Song) intent.getSerializableExtra("song");
        mIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        isPlaying = intent.getBooleanExtra("playing", false);

        LogUtils.d((mSong == null) ? "null" : mSong.name);
        LogUtils.d((mIds == null) ? "null" : Arrays.toString(mIds));

        if(mIds != null) {
            for (int appWidgetId : mIds) {
                updateAppWidget(context, manager, appWidgetId);
            }
        }
    }
}
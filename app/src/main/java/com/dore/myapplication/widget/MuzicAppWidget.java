package com.dore.myapplication.widget;

import static com.dore.myapplication.utilities.Constants.ACTION_CLOSE;
import static com.dore.myapplication.utilities.Constants.ACTION_NEXT;
import static com.dore.myapplication.utilities.Constants.ACTION_PLAY;
import static com.dore.myapplication.utilities.Constants.ACTION_PREV;
import static com.dore.myapplication.utilities.Constants.NOTIFICATION_DATA_ACTION;
import static com.dore.myapplication.utilities.Constants.WIDGET_DATA_ACTION;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

import java.util.List;

public class MuzicAppWidget extends AppWidgetProvider {

    private RemoteViews mRemoteViews;

    private Context mContext;

    private boolean isEnabled = false;

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        mContext = context;

        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.muzic_app_widget);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_widget_prev, getNotiPendingIntent(ACTION_PREV));
        mRemoteViews.setOnClickPendingIntent(R.id.btn_widget_next, getNotiPendingIntent(ACTION_NEXT));
        mRemoteViews.setOnClickPendingIntent(R.id.btn_widget_play, getNotiPendingIntent(ACTION_PLAY));

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
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        isEnabled = true;
    }

    @Override
    public void onDisabled(Context context) {
        isEnabled = false;
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public void updateNewSong(Song song) {
//        mRemoteViews.setTextViewText(R.id.txt_widget_song_name, song.getName());
//        mRemoteViews.setTextViewText(R.id.txt_widget_author, song.getAuthor());
    }

    public void updateSongState(boolean isPlaying){
//        if(isPlaying) {
//            mRemoteViews.setImageViewResource(R.id.btn_widget_play, R.drawable.ic_control_pause);
//        }else {
//            mRemoteViews.setImageViewResource(R.id.btn_widget_play, R.drawable.ic_control_play);
//        }
    }
}
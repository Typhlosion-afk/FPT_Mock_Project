package com.dore.myapplication.utilities;

import android.net.Uri;

public class Constants {

    public static final String LOCAL_BROADCAST_RECEIVER = "com.dore.myapplication.service.RECEIVER";

    public static final String GLOBAL_BROADCAST_RECEIVER = "android.appwidget.action.APPWIDGET_UPDATE";

    public static final String NOTIFICATION_CHANNEL_ID = "muzic_app_channel_id";

    public static final Uri ALBUMART_URI = Uri.parse("content://media/external/audio/albumart");

    public static final int ONGOING_NOTIFICATION_ID = 99;

    public static final String NOTIFICATION_DATA_ACTION = "noti_event_action";

    public static final String WIDGET_DATA_ACTION = "widget_event_action";

    public static final int MAX_SEEKBAR_VALUE = 1000;

    public static final String KEY_SONG_POSITION = "key_for_song_pos";

    public static final String KEY_SONG_LIST = "key_for_list_song";

    public static final int ACTION_PLAY = 0;

    public static final int ACTION_PAUSE = 1;

    public static final int ACTION_CLOSE = 2;

    public static final int ACTION_NEXT = 3;

    public static final int ACTION_PREV = 4;

    public static final int ACTION_OPEN_APP = 5;

}

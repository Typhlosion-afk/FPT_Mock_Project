package com.dore.myapplication.utilities;

import com.dore.myapplication.model.Song;

public class TimeUtil {
    public String posToTime(int pos) {
        int m = pos / 1000 / 60;
        int s = pos / 1000 % 60;
        String min = m < 10 ? "0" + m : "" + m;
        String sec = s < 10 ? "0" + s : "" + s;
        return min + ":" + sec;
    }

    public String getSongTime(Song song) {
        int pos = Integer.parseInt(song.getDur());
        int m = pos / 1000 / 60;
        int s = pos / 1000 % 60;
        String min = m < 10 ? "0" + m : "" + m;
        String sec = s < 10 ? "0" + s : "" + s;
        return min + ":" + sec;
    }

}

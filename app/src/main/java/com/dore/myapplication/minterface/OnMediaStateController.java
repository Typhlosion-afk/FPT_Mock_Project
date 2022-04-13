package com.dore.myapplication.minterface;

import com.dore.myapplication.model.Song;

import java.util.List;

public interface OnMediaStateController {
    void onChangeListSong(List<Song> song);

    void onPlayNewSong(Song song, int pos);

    void onPlayingStateChange(boolean isPlaying);

    void onRunning(int cur);

    void onDurationChange(int duration);
}

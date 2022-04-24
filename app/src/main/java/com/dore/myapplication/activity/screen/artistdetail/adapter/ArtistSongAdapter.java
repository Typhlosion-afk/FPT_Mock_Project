package com.dore.myapplication.activity.screen.artistdetail.adapter;

import static com.dore.myapplication.activity.MainActivity.mainNavController;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

import java.util.ArrayList;
import java.util.List;

public class ArtistSongAdapter extends RecyclerView.Adapter<ArtistSongAdapter.ArtistHolder> {

    private Context mContext;

    private ArrayList<Song> mListSong;

    private View mRootView;

    public ArtistSongAdapter(Context context, List<Song> listSong) {
        this.mContext = context;
        mListSong = new ArrayList<>();
        this.mListSong.addAll(listSong);
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mRootView = LayoutInflater.from(mContext).inflate(R.layout.card_artist_songs, parent, false);

        return new ArtistHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        holder.txtName.setText(mListSong.get(position).getName());
        holder.txtTime.setText(mListSong.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mListSong.size();
    }


    class ArtistHolder extends RecyclerView.ViewHolder {

        TextView txtName;

        TextView txtTime;

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name_card);
            txtTime = itemView.findViewById(R.id.txt_time_card);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(mRootView.getContext(), MusicService.class);
                i.putExtra(KEY_SONG_LIST, mListSong);
                i.putExtra(KEY_SONG_POSITION, getAdapterPosition());

                mRootView.getContext().startService(i);

                mainNavController.navigate(R.id.action_play_song);
            });
        }
    }
}

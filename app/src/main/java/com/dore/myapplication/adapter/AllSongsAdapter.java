package com.dore.myapplication.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.SongHolder> {

    private View mRootView;

    private List<Song> mListSong = new ArrayList<>();

    public AllSongsAdapter(List<Song> songs) {
        mListSong = songs;
    }

    @NonNull
    @Override
    public AllSongsAdapter.SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_all_songs, parent, false);
        return new SongHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllSongsAdapter.SongHolder holder, int position) {
        holder.imgSong.setImageResource(R.drawable.img_bg_playlist_default);
        holder.txtSongName.setText(mListSong.get(position).name);
        holder.txtSongAuthor.setText(mListSong.get(position).author);
    }

    @Override
    public int getItemCount() {
        return mListSong.size();
    }

    class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgSong;

        TextView txtSongName;

        TextView txtSongAuthor;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.img_card_disc);
            txtSongName = itemView.findViewById(R.id.txt_name_song);
            txtSongAuthor = itemView.findViewById(R.id.txt_author);

            itemView.setOnClickListener(v -> {
                Bundle bSong = new Bundle();
                bSong.putSerializable("song", mListSong.get(getAdapterPosition()));
                Navigation.findNavController(mRootView).navigate(R.id.action_play_song, bSong);
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

}

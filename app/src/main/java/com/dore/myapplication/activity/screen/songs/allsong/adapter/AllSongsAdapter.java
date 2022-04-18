package com.dore.myapplication.activity.screen.songs.allsong.adapter;

import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

import java.util.ArrayList;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.SongHolder> {

    private View mRootView;

    private ArrayList<Song> mListSong;

    private Context mContext;

    public AllSongsAdapter(Context context, List<Song> songs) {
        this.mContext = context;
        mListSong = new ArrayList<>();
        mListSong.addAll(songs);
    }

    @NonNull
    @Override
    public AllSongsAdapter.SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.card_all_songs, parent, false);
        return new SongHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllSongsAdapter.SongHolder holder, int position) {
//        holder.imgSong.setImageResource(R.drawable.img_bg_playlist_default);
        holder.txtSongName.setText(mListSong.get(position).name);
        holder.txtSongAuthor.setText(mListSong.get(position).author);

        Glide
                .with(mContext)
                .load(mListSong.get(position).getImgPath())
                .centerCrop()
                .placeholder(R.drawable.img_bg_recommend_default)
                .error(R.drawable.img_bg_recommend_default)
                .centerCrop()
                .into(holder.imgSong);
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
                Intent i = new Intent(mRootView.getContext(), MusicService.class);
                i.putExtra(KEY_SONG_LIST, mListSong);
                i.putExtra(KEY_SONG_POSITION, getAdapterPosition());

                mRootView.getContext().startService(i);

                Navigation.findNavController(mRootView).navigate(R.id.action_play_song);
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

}

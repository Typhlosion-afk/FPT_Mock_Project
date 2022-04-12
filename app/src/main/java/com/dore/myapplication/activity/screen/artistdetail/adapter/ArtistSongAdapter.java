package com.dore.myapplication.activity.screen.artistdetail.adapter;

import android.content.Context;
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

import java.util.List;

public class ArtistSongAdapter extends RecyclerView.Adapter<ArtistSongAdapter.ArtistHolder> {

    private Context mContext;

    private List<Song> mListSong;

    private View mRootView;

    public ArtistSongAdapter(Context context, List<Song> listSong) {
        this.mContext = context;
        this.mListSong = listSong;
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
                Bundle bSong = new Bundle();
                bSong.putSerializable("song", mListSong.get(getAdapterPosition()));
                Navigation.findNavController(mRootView).navigate(R.id.action_play_song, bSong);
            });
        }
    }
}

package com.dore.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumDetailAdapter.AlbumHolder> {

    private View mRootView;

    private List<Song> mListSong;

    public AlbumDetailAdapter(List<Song> mListSong) {
        this.mListSong = mListSong;
    }

    private String strDetail(String year, int numSong, int time){
        return year + " . " + numSong + " Songs . " + time + " min" ;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_artist_songs, parent, false);
        return new AlbumHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        holder.txtName.setText(mListSong.get(position).getName());
        holder.txtTime.setText(mListSong.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mListSong.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {

        TextView txtName;

        TextView txtTime;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name_card);
            txtTime = itemView.findViewById(R.id.txt_time_card);
        }
    }
}

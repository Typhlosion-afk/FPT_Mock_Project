package com.dore.myapplication.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class HomeRecentlyAdapter extends RecyclerView.Adapter<HomeRecentlyAdapter.HomeHolder> {

    private View mRootView;

    private ArrayList<Song> mListSong;

    public HomeRecentlyAdapter(List<Song> listSong) {
        this.mListSong = new ArrayList<>();
        this.mListSong.addAll(listSong);
    }

    @NonNull
    @Override
    public HomeRecentlyAdapter.HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recently, parent, false);
        return new HomeHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecentlyAdapter.HomeHolder holder, int position) {
        holder.txtName.setText(mListSong.get(position).getName());
        holder.txtAuthor.setText(mListSong.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mListSong.size();
    }

    class HomeHolder extends RecyclerView.ViewHolder {

        private final TextView txtName;

        private final TextView txtAuthor;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_song_name_card);
            txtAuthor = itemView.findViewById(R.id.txt_author_card);

            itemView.setOnClickListener(v -> {
                Bundle bSong = new Bundle();
                bSong.putSerializable("song", mListSong.get(getAdapterPosition()));
                Navigation.findNavController(mRootView).navigate(R.id.action_play_song, bSong);
            });
        }

    }
}

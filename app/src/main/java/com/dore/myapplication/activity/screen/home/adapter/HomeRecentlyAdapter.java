package com.dore.myapplication.activity.screen.home.adapter;

import static com.dore.myapplication.activity.MainActivity.mainNavController;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;

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

import java.io.Serializable;
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

                Intent i = new Intent(mRootView.getContext(), MusicService.class);
                i.putExtra(KEY_SONG_LIST, mListSong);
                i.putExtra(KEY_SONG_POSITION, getAdapterPosition());

                mRootView.getContext().startService(i);

                mainNavController.navigate(R.id.action_play_song);
            });
        }

    }
}

package com.dore.myapplication.activity.screen.home.adapter;

import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;

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

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.HomeHolder> {

    private View mRootView;

    private ArrayList<Song> mListSong;

    public HomeRecommendAdapter(List<Song> listPlaylist) {
        this.mListSong = new ArrayList<>();
        this.mListSong.addAll(listPlaylist);
    }

    @NonNull
    @Override
    public HomeRecommendAdapter.HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hot_recommended, parent, false);
        return new HomeHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecommendAdapter.HomeHolder holder, int position) {

        holder.imgBackground.setImageResource(R.drawable.img_bg_recommend_default);

        holder.txtName.setText(mListSong.get(position).getName());
        holder.txtAuthor.setText(mListSong.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mListSong.size();
    }

    class HomeHolder extends RecyclerView.ViewHolder {

        private TextView txtName;

        private TextView txtAuthor;

        private ImageView imgBackground;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            txtName = itemView.findViewById(R.id.txt_name_card);
            txtAuthor = itemView.findViewById(R.id.txt_author_card);
            imgBackground = itemView.findViewById(R.id.img_card);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(mRootView.getContext(), MusicService.class);
                i.putExtra(KEY_SONG_LIST, (Serializable) mListSong);
                i.putExtra(KEY_SONG_POSITION, getAdapterPosition());

                mRootView.getContext().startService(i);

                Navigation.findNavController(mRootView).navigate(R.id.action_play_song);
            });
        }

    }
}

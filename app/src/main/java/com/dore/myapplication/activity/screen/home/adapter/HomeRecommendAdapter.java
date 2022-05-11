package com.dore.myapplication.activity.screen.home.adapter;

import static com.dore.myapplication.activity.MainActivity.mainNavController;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.dore.myapplication.utilities.ImageUtil;
import com.dore.myapplication.utilities.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.HomeHolder> {

    private View mRootView;

    private ArrayList<Song> mListSong;

    private Context mContext;

    public HomeRecommendAdapter(Context context, List<Song> listPlaylist) {
        this.mContext = context;
        this.mListSong = new ArrayList<>();
        this.mListSong.addAll(listPlaylist);
    }

    @NonNull
    @Override
    public HomeRecommendAdapter.HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.card_hot_recommended, parent, false);
        return new HomeHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecommendAdapter.HomeHolder holder, int position) {

        ImageUtil imageUtil = new ImageUtil();
        imageUtil.showSongImage(mContext, mListSong.get(position), holder.imgBackground);

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
                i.putExtra(KEY_SONG_LIST, mListSong);
                i.putExtra(KEY_SONG_POSITION, getAdapterPosition());

                mRootView.getContext().startService(i);

                mainNavController.navigate(R.id.action_play_song);
            });
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<Song> songs){
        if(mListSong != songs) {
            mListSong = new ArrayList<>(songs);
            notifyDataSetChanged();
            Log.d("song", "update list song");
        }
    }
}

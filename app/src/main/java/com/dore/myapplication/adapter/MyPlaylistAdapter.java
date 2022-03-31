package com.dore.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class MyPlaylistAdapter extends RecyclerView.Adapter<MyPlaylistAdapter.PlaylistHolder> {

    private View mRootView;

    private List<Playlist> mListPlaylist = new ArrayList<>();

    private Context mContext;

    public MyPlaylistAdapter(List<Playlist> ls, Context context) {
        this.mContext = context;
        if(ls.size()!=0) {
            mListPlaylist = ls;
        }
    }

    @NonNull
    @Override
    public PlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.card_my_playlist, parent, false);
        return new PlaylistHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHolder holder, int position) {
        holder.img.setImageResource(R.drawable.img_bg_playlist_default);
        holder.textView.setText(mListPlaylist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mListPlaylist.size();
    }

    class PlaylistHolder extends RecyclerView.ViewHolder {

        ImageView img;

        TextView textView;

        public PlaylistHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_card);
            textView = itemView.findViewById(R.id.txt_name_card);
        }
    }
}

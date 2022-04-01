package com.dore.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.MusicStyle;

import java.util.ArrayList;
import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresHolder> {

    private View mRootView;

    private List<MusicStyle> mMusicStyleList;

    public GenresAdapter(List<MusicStyle> mMusicStyleList) {
        this.mMusicStyleList = new ArrayList<>();
        this.mMusicStyleList = mMusicStyleList;
    }

    private String strNumSong(int num){
        return num + " Songs";
    }

    @NonNull
    @Override
    public GenresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_genres, parent, false);

        return new GenresHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresHolder holder, int position) {
        holder.img.setImageBitmap(mMusicStyleList.get(position).getTheme());
        holder.txtName.setText(mMusicStyleList.get(position).getName());
        holder.txtNumSong.setText(strNumSong(mMusicStyleList.get(position).getSongs().size()));
    }

    @Override
    public int getItemCount() {
        return mMusicStyleList.size();
    }

    class GenresHolder extends RecyclerView.ViewHolder {

        ImageView img;

        TextView txtName;

        TextView txtNumSong;

        public GenresHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_card);
            txtName = itemView.findViewById(R.id.txt_name_card);
            txtNumSong = itemView.findViewById(R.id.txt_num_song_card);

        }
    }
}

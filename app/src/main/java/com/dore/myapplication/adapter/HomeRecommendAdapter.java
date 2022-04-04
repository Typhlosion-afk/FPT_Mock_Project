package com.dore.myapplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.HomeHolder> {

    private View rootView;

    private ArrayList<Song> mListSong;

    public HomeRecommendAdapter(List<Song> listPlaylist) {
        this.mListSong = new ArrayList<>();
        this.mListSong.addAll(listPlaylist);
    }

    @NonNull
    @Override
    public HomeRecommendAdapter.HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hot_recommended, parent, false);
        return new HomeHolder(rootView);
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

    class HomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtName;

        private TextView txtAuthor;

        private ImageView imgBackground;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView(){

            imgBackground = itemView.findViewById(R.id.img_card);

            txtName = itemView.findViewById(R.id.txt_name_card);
            txtAuthor = itemView.findViewById(R.id.txt_author_card);
        }

        @Override
        public void onClick(View v) {
            Log.d("TAG", "Song name: " + mListSong.get(getAdapterPosition()));
        }
    }
}

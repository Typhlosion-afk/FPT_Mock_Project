package com.dore.myapplication.adapter;

import android.util.Log;
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
import java.util.zip.Inflater;

public class HomeRecentlyAdapter extends RecyclerView.Adapter<HomeRecentlyAdapter.HomeHolder> {

    private View rootView;

    private ArrayList<Song> mListSong;

    public HomeRecentlyAdapter(List<Song> listSong) {
        this.mListSong = new ArrayList<>();
        this.mListSong.addAll(listSong);
    }

    @NonNull
    @Override
    public HomeRecentlyAdapter.HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recently, parent, false);
        return new HomeHolder(rootView);
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

    class HomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txtName;

        private final TextView txtAuthor;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_song_name_card);
            txtAuthor = itemView.findViewById(R.id.txt_author_card);
        }

        @Override
        public void onClick(View v) {
            Log.d("TAG", "Song name: " + mListSong.get(getAdapterPosition()).name);
        }
    }
}

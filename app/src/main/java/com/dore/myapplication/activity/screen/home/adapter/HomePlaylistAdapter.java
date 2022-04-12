package com.dore.myapplication.activity.screen.home.adapter;

import android.util.Log;
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

public class HomePlaylistAdapter extends RecyclerView.Adapter<HomePlaylistAdapter.HomeHolder> {

    private final ArrayList<Playlist> mListPlaylist;

    public HomePlaylistAdapter(List<Playlist> listPlaylist) {
        this.mListPlaylist = new ArrayList<>();
        this.mListPlaylist.addAll(listPlaylist);
    }

    @NonNull
    @Override
    public HomePlaylistAdapter.HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_playlist, parent, false);
        return new HomeHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePlaylistAdapter.HomeHolder holder, int position) {

        holder.imgBackground.setImageResource(R.drawable.img_bg_playlist_default);

        holder.txtName.setText(mListPlaylist.get(position).getName());
        holder.txtAuthor.setText(getStrSize(mListPlaylist.get(position).getListSong().size()));
    }

    @Override
    public int getItemCount() {
        return mListPlaylist.size();
    }

    private String getStrSize(int size) {
        return "Total " + size;
    }

    class HomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtName;

        private TextView txtAuthor;

        private ImageView imgBackground;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);

            initView();

        }

        private void initView() {
            imgBackground = itemView.findViewById(R.id.img_card);
            txtName = itemView.findViewById(R.id.txt_name_card);
            txtAuthor = itemView.findViewById(R.id.txt_size);
        }

        @Override
        public void onClick(View v) {
            Log.d("TAG", "Song name: " + mListPlaylist.get(getAdapterPosition()));
        }
    }
}

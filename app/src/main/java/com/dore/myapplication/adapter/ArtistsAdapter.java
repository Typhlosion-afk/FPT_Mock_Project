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
import com.dore.myapplication.model.Author;

import java.util.ArrayList;
import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder> {

    private View mRootView;

    private List<Author> mListAuthor;

    public ArtistsAdapter(List<Author> mListAuthor) {
        this.mListAuthor = new ArrayList<>();
        this.mListAuthor = mListAuthor;
    }

    private String strNumSong(int num){
        return num + " Songs";
    }

    private String strNumAlbum(int num){
        return num + " Albums";
    }

    @NonNull
    @Override
    public ArtistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_artists, parent, false);

        return new ArtistsViewHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsViewHolder holder, int position) {
        Author author = mListAuthor.get(position);
        holder.img.setImageResource(R.drawable.img_bg_playlist_default);
        holder.txtName.setText(author.getName());
        holder.txtNumSong.setText(strNumSong(author.getSongs().size()));
        holder.txtNumAlbum.setText(strNumAlbum( author.getAlbums().size()));
    }

    @Override
    public int getItemCount() {
        return mListAuthor.size();
    }

    class ArtistsViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        TextView txtName;

        TextView txtNumAlbum;

        TextView txtNumSong;

        ImageView imgDotMenu;

        public ArtistsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_card);
            txtName = itemView.findViewById(R.id.txt_name_card);
            txtNumSong = itemView.findViewById(R.id.txt_num_song_card);
            txtNumAlbum = itemView.findViewById(R.id.txt_num_album_card);
            imgDotMenu = itemView.findViewById(R.id.img_menu);

            imgDotMenu.setOnClickListener(v -> {
                Log.d("TAG", "open Menu");
            });

            itemView.setOnClickListener(v -> {
                Log.d("TAG", "ArtistsViewHolder: " + mListAuthor.get(getAdapterPosition()).getName());
            });
        }
    }
}

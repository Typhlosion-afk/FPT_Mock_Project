package com.dore.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Author;

import java.util.ArrayList;
import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder> {

    private View mRootView;

    private List<Author> mListAuthor;

    private Context mContext;


    public ArtistsAdapter(List<Author> mListAuthor, Context context) {
        this.mListAuthor = new ArrayList<>();
        this.mListAuthor = mListAuthor;
        this.mContext = context;
    }

    private String strNumSong(int num) {
        return num + " Songs";
    }

    private String strNumAlbum(int num) {
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
        holder.txtNumAlbum.setText(strNumAlbum(author.getAlbums().size()));

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

            handleMenu();

            itemView.setOnClickListener(v -> {
                Bundle authorBundle = new Bundle();
                authorBundle.putSerializable("author", mListAuthor.get(getAdapterPosition()));
                Navigation.findNavController(mRootView).navigate(R.id.action_show_detail_artist, authorBundle);
            });
        }

        @SuppressLint("NonConstantResourceId")
        private void handleMenu() {
            imgDotMenu.setOnClickListener(v -> {
                Author author = mListAuthor.get(getAdapterPosition());

                Context wrapper = new ContextThemeWrapper(mContext, R.style.PopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, imgDotMenu);
                popupMenu.getMenuInflater().inflate(R.menu.popup_album, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.pop_play: {
                            play(author);
                            break;
                        }
                        case R.id.pop_play_next: {
                            playNext(author);
                            break;
                        }
                        case R.id.pop_add_to_playing_queue: {
                            addToPlayingQueue(author);
                            break;
                        }
                        case R.id.pop_add_to_playlist: {
                            addToPlaylist(author);
                            break;
                        }
                        case R.id.pop_rename: {
                            rename(author);
                            break;
                        }
                        case R.id.pop_tag_editor: {
                            tagEditor(author);
                            break;
                        }
                        case R.id.pop_go_to_artist: {
                            goToArtist(author);
                            break;
                        }
                        case R.id.pop_delete_from_device: {
                            deleteFromDevice(author);
                            break;
                        }
                        case R.id.pop_details: {
                            details(author);
                            break;
                        }
                    }
                    return true;
                });

                popupMenu.show();

            });
        }

        private void play(Author author) {

        }

        private void playNext(Author author) {

        }

        private void addToPlayingQueue(Author author) {

        }

        private void addToPlaylist(Author author) {

        }

        private void rename(Author author) {
        }

        private void tagEditor(Author author) {
        }

        private void goToArtist(Author author) {

        }

        private void deleteFromDevice(Author author) {

        }

        private void details(Author author) {

        }
    }

}

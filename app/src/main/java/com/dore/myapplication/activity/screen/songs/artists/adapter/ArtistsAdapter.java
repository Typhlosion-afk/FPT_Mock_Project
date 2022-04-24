package com.dore.myapplication.activity.screen.songs.artists.adapter;

import static com.dore.myapplication.activity.MainActivity.mainNavController;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
import com.dore.myapplication.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder> {

    private View mRootView;

    private List<Artist> mListArtist;

    private Context mContext;


    public ArtistsAdapter(List<Artist> mListArtist, Context context) {
        this.mListArtist = new ArrayList<>();
        this.mListArtist = mListArtist;
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
        Artist artist = mListArtist.get(position);
        holder.img.setImageResource(R.drawable.img_bg_playlist_default);
        holder.txtName.setText(artist.getName());
//        holder.txtNumSong.setText(strNumSong(artist.getSongs().size()));
        holder.txtNumAlbum.setText(strNumAlbum(artist.getAlbums().size()));

    }

    @Override
    public int getItemCount() {
        return mListArtist.size();
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
                Bundle artistBundle = new Bundle();
                artistBundle.putSerializable("artist", mListArtist.get(getAdapterPosition()));
                mainNavController.navigate(R.id.action_show_detail_artist, artistBundle);
            });
        }

        @SuppressLint("NonConstantResourceId")
        private void handleMenu() {
            imgDotMenu.setOnClickListener(v -> {
                Artist artist = mListArtist.get(getAdapterPosition());

                Context wrapper = new ContextThemeWrapper(mContext, R.style.PopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, imgDotMenu);
                popupMenu.getMenuInflater().inflate(R.menu.popup_album, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.pop_play: {
                            play(artist);
                            break;
                        }
                        case R.id.pop_play_next: {
                            playNext(artist);
                            break;
                        }
                        case R.id.pop_add_to_playing_queue: {
                            addToPlayingQueue(artist);
                            break;
                        }
                        case R.id.pop_add_to_playlist: {
                            addToPlaylist(artist);
                            break;
                        }
                        case R.id.pop_rename: {
                            rename(artist);
                            break;
                        }
                        case R.id.pop_tag_editor: {
                            tagEditor(artist);
                            break;
                        }
                        case R.id.pop_go_to_artist: {
                            goToArtist(artist);
                            break;
                        }
                        case R.id.pop_delete_from_device: {
                            deleteFromDevice(artist);
                            break;
                        }
                        case R.id.pop_details: {
                            details(artist);
                            break;
                        }
                    }
                    return true;
                });

                popupMenu.show();

            });
        }

        private void play(Artist artist) {

        }

        private void playNext(Artist artist) {

        }

        private void addToPlayingQueue(Artist artist) {

        }

        private void addToPlaylist(Artist artist) {

        }

        private void rename(Artist artist) {
        }

        private void tagEditor(Artist artist) {
        }

        private void goToArtist(Artist artist) {

        }

        private void deleteFromDevice(Artist artist) {

        }

        private void details(Artist artist) {

        }
    }

}

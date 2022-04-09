package com.dore.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Album;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsHolder> {

    private View mRootView;

    private List<Album> mAlbumList;

    private Context mContext;

    private final String TAG = "album";

    public AlbumsAdapter(List<Album> albumList, Context context) {

        this.mContext = context;
        this.mAlbumList = new ArrayList<>();
        this.mAlbumList = albumList;

    }

    private String getStrSizeAlbum(int num) {
        return num + " Song(s)";
    }

    @NonNull
    @Override
    public AlbumsAdapter.AlbumsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.card_albums, parent, false);
        return new AlbumsHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsHolder holder, int position) {
        Album album = mAlbumList.get(position);

        holder.img.setImageResource(R.drawable.img_bg_playlist_default);
        holder.txtName.setText(album.getName());
        holder.txtAuthor.setText(album.getAuthor());
        holder.txtNumSong.setText(getStrSizeAlbum(album.getListSong().size()));
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    class AlbumsHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txtName;
        TextView txtAuthor;
        TextView txtNumSong;
        ImageView imgDotMenu;

        public AlbumsHolder(@NonNull View itemView) {
            super(itemView);

            initView();
            handleAction();
            handleMenu();

            itemView.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putSerializable("album", mAlbumList.get(getAdapterPosition()));
                Navigation.findNavController(mRootView).navigate(R.id.action_show_detail_album, b);
            });
        }

        private void initView() {
            img = itemView.findViewById(R.id.img_card);
            txtName = itemView.findViewById(R.id.txt_name_card);
            txtAuthor = itemView.findViewById(R.id.txt_author_card);
            txtNumSong = itemView.findViewById(R.id.txt_num_song_card);
            imgDotMenu = itemView.findViewById(R.id.btn_card_menu);

        }

        private void handleAction() {

        }


        @SuppressLint("NonConstantResourceId")
        private void handleMenu() {
            imgDotMenu.setOnClickListener(v -> {
                Log.d(TAG, "handleMenu: click");
                Album album = mAlbumList.get(getAdapterPosition());

                Context wrapper = new ContextThemeWrapper(mContext, R.style.PopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, imgDotMenu);
                popupMenu.getMenuInflater().inflate(R.menu.popup_album, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.pop_play: {
                            play(album);
                            break;
                        }
                        case R.id.pop_play_next: {
                            playNext(album);
                            break;
                        }
                        case R.id.pop_add_to_playing_queue: {
                            addToPlayingQueue(album);
                            break;
                        }
                        case R.id.pop_add_to_playlist: {
                            addToPlaylist(album);
                            break;
                        }
                        case R.id.pop_rename: {
                            rename(album);
                            break;
                        }
                        case R.id.pop_tag_editor: {
                            tagEditor(album);
                            break;
                        }
                        case R.id.pop_go_to_artist: {
                            goToArtist(album);
                            break;
                        }
                        case R.id.pop_delete_from_device: {
                            deleteFromDevice(album);
                            break;
                        }
                        case R.id.pop_details: {
                            details(album);
                            break;
                        }
                    }
                    return true;
                });

                popupMenu.show();

            });
        }

        private void play(Album album) {

        }

        private void playNext(Album album) {

        }

        private void addToPlayingQueue(Album album) {

        }

        private void addToPlaylist(Album album) {

        }

        private void rename(Album album) {
        }

        private void tagEditor(Album album) {
        }

        private void goToArtist(Album album) {

        }

        private void deleteFromDevice(Album album) {

        }

        private void details(Album album) {

        }
    }
}

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
import com.dore.myapplication.model.Album;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsHolder> {

    private View mRootView;

    private List<Album> mAlbumList = new ArrayList<>();

    public AlbumsAdapter(List<Album> albumList) {
        this.mAlbumList = albumList;

    }

    private String getStrSizeAlbum(int num){
        return num + " Song(s)";
    }

    @NonNull
    @Override
    public AlbumsAdapter.AlbumsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_albums, parent, false);
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
            handleMenu();
        }

        private void initView(){
            img = itemView.findViewById(R.id.img_card);
            txtName = itemView.findViewById(R.id.txt_name_card);
            txtAuthor = itemView.findViewById(R.id.txt_author_card);
            txtNumSong = itemView.findViewById(R.id.txt_num_song_card);
            imgDotMenu = itemView.findViewById(R.id.btn_card_menu);

        }

        private void handleMenu(){
            imgDotMenu.setOnClickListener(v -> {
                Log.d("TAG", "onClick: ");
            });
        }
    }
}

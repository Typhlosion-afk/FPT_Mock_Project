package com.dore.myapplication.utilities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dore.myapplication.R;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Song;

public class ImageUtil {
    public void showSongImage(Context context, Song song, ImageView viewTarget){
        Glide
                .with(context)
                .load(song.getImgPath())
                .placeholder(R.drawable.img_bg_recommend_default)
                .error(R.drawable.img_bg_recommend_default)
                .centerCrop()
                .into(viewTarget);
    }

    public void showAlbumImage(Context context, Album album, ImageView viewTarget){
        Glide
                .with(context)
                .load(album.getListSong().get(0).getImgPath())
                .placeholder(R.drawable.img_bg_recommend_default)
                .error(R.drawable.img_bg_recommend_default)
                .centerCrop()
                .into(viewTarget);
    }

}

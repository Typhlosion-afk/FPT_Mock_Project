package com.dore.myapplication.activity.screen.songs.allsong.adapter;

import static com.dore.myapplication.activity.MainActivity.mainNavController;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.SongHolder> {

    private View mRootView;

    private final ArrayList<Song> mListSong;

    private final Context mContext;

    private final ImageUtil mImageUtil = new ImageUtil();

    public AllSongsAdapter(Context context, List<Song> songs) {
        mContext = context;
        mListSong = new ArrayList<>();
        mListSong.addAll(songs);
    }

    @NonNull
    @Override
    public AllSongsAdapter.SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.card_all_songs, parent, false);
        return new SongHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllSongsAdapter.SongHolder holder, int position) {
        holder.txtSongName.setText(mListSong.get(position).name);
        holder.txtSongAuthor.setText(mListSong.get(position).author);

        mImageUtil.showSongImage(mContext, mListSong.get(position), holder.imgSong);
    }

    @Override
    public int getItemCount() {
        return mListSong.size();
    }

    class SongHolder extends RecyclerView.ViewHolder {

        ImageView imgSong;

        TextView txtSongName;

        TextView txtSongAuthor;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.img_card_disc);
            txtSongName = itemView.findViewById(R.id.txt_name_song);
            txtSongAuthor = itemView.findViewById(R.id.txt_author);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(mRootView.getContext(), MusicService.class);
                i.putExtra(KEY_SONG_LIST, mListSong);
                i.putExtra(KEY_SONG_POSITION, getAdapterPosition());

                mRootView.getContext().startService(i);

                mainNavController.navigate(R.id.action_play_song);
            });
        }

    }

}

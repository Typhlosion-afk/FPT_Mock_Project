package com.dore.myapplication.activity;

import static com.dore.myapplication.activity.MainActivity.mainNavController;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_LIST;
import static com.dore.myapplication.utilities.Constants.KEY_SONG_POSITION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Song;
import com.dore.myapplication.service.MusicService;
import com.dore.myapplication.utilities.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private View mRootView;

    private Context mContext;

    private List<Song> mSongList;

    public SearchAdapter(Context context, List<Song> songList) {
        mSongList = new ArrayList<>();
        mSongList.addAll(songList);
        mContext = context;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search, parent, false);

        return new SearchHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        if(mSongList.size() == 0){
            holder.txtSongName.setText(getEmpty());
        }
        holder.txtSongName.setText(mSongList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mSongList == null ? 1 : mSongList.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {

        TextView txtSongName;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);

            initView();
            initAction();
        }

        private void initView(){
            txtSongName = itemView.findViewById(R.id.txt_song_name_card);
        }

        private void initAction(){
            itemView.setOnClickListener(v -> {
                if (mSongList.size() != 0){
                    ArrayList<Song> list = new ArrayList<>();
                    list.add(mSongList.get(getAdapterPosition()));
                    Intent i = new Intent(mRootView.getContext(), MusicService.class);
                    i.putExtra(KEY_SONG_LIST, list);
                    i.putExtra(KEY_SONG_POSITION, 0);

                    mRootView.getContext().startService(i);

                    mainNavController.navigate(R.id.action_play_song);
                }
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Song> list){
        LogUtils.d( "" + list.size());
        mSongList = list;
        notifyDataSetChanged();
    }

    private String getEmpty(){
        return "<Empty>";
    }
}

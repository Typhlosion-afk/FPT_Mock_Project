package com.dore.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMenuAdapter extends RecyclerView.Adapter<PlaylistMenuAdapter.PlaylistHolder> {

    private List<String> mListItem = new ArrayList<>();

    private Context mContext;

    private View mRootView;

    public PlaylistMenuAdapter(Context context){

        this.mContext = context;

        if(mListItem.size() != 0){
            mListItem.clear();
        }
        mListItem.add("My Top Tracks");
        mListItem.add("Latest Added");
        mListItem.add("History");
        mListItem.add("Favourites");
    }


    @NonNull
    @Override
    public PlaylistMenuAdapter.PlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.item_gird_menu, parent, false);

        return new PlaylistHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistMenuAdapter.PlaylistHolder holder, int position) {
        holder.txtNum.setText(mListItem.get(position));
//        holder.txtNum.setText(mListItem.get);

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    class PlaylistHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName;
        TextView txtNum;
        ImageView imgPlay;
        public PlaylistHolder(@NonNull View itemView) {
            super(itemView);
            img = mRootView.findViewById(R.id.item_bg);
            txtName = mRootView.findViewById(R.id.item_name);
            txtNum = mRootView.findViewById(R.id.item_number);
            imgPlay = mRootView.findViewById(R.id.btn_item_play);
        }
    }
}

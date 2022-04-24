package com.dore.myapplication.activity.screen.artistdetail.adapter;

import static com.dore.myapplication.activity.MainActivity.mainNavController;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dore.myapplication.R;
import com.dore.myapplication.model.Album;

import java.util.ArrayList;
import java.util.List;

public class ArtistAlbumAdapter extends RecyclerView.Adapter<ArtistAlbumAdapter.ArtistHolder> {

    private ArrayList<Album> mAlbumList;

    private View mRootView;

    private Context mContext;

    public ArtistAlbumAdapter(Context context, List<Album> mAlbumList) {
        this.mAlbumList = new ArrayList<>();
        this.mAlbumList.addAll(mAlbumList);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.card_artist_album, parent, false);
        return new ArtistHolder(mRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        holder.img.setImageResource(R.drawable.img_bg_playlist_default);
        holder.txtName.setText(mAlbumList.get(position).getName());
        holder.txtYear.setText(mAlbumList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    public class ArtistHolder extends RecyclerView.ViewHolder {

        ImageView img;

        TextView txtName;

        TextView txtYear;

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_card);
            txtName = itemView.findViewById(R.id.txt_name_card);
            txtYear = itemView.findViewById(R.id.txt_year_card);

            itemView.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putSerializable("album", mAlbumList.get(getAdapterPosition()));
                mainNavController.navigate(R.id.action_show_detail_album, b);
            });
        }
    }
}

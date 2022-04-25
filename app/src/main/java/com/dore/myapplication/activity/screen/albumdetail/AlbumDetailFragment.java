package com.dore.myapplication.activity.screen.albumdetail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dore.myapplication.R;
import com.dore.myapplication.activity.screen.albumdetail.adapter.AlbumDetailAdapter;
import com.dore.myapplication.base.BaseFragment;
import com.dore.myapplication.model.Album;
import com.dore.myapplication.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailFragment extends BaseFragment {

    private View mRootView;

    private ImageView mImgAvatar;

    private ImageView mImgCover;

    private TextView mTxtName;

    private TextView mTxtAuthor;

    private TextView mTxtDetail;

    private Button mBtnFollow;

    private RecyclerView mRecyclerView;

    private AlbumDetailAdapter mAlbumDetailAdapter;

    private Album album;

    private List<Album> mListAlbums = new ArrayList<>();

    private List<Song> mListSong = new ArrayList<>();

    @Override
    public int getLayoutID() {
        return R.layout.fragment_album_detail;
    }

    @Override
    public void onViewReady(View rootView) {
        mRootView = rootView;

        initData();
        initView();
        initAdapter();
    }

    private void initData() {

        if(getArguments() != null) {
            album = (Album) getArguments().getSerializable("album");
            mListSong = album.getListSong();
        }

    }

    private void initView() {
        mTxtName = mRootView.findViewById(R.id.txt_artist_name);
        mTxtAuthor = mRootView.findViewById(R.id.txt_album_author);
        mTxtDetail = mRootView.findViewById(R.id.txt_album_detail);

        mImgAvatar = mRootView.findViewById(R.id.img_album_avatar);
        mImgCover = mRootView.findViewById(R.id.img_album_cover);

        int time = 32;

        mTxtName.setText(album.getName());
        mTxtAuthor.setText(album.getAuthor());
        mTxtDetail.setText(strAlbumDetails(album.getYear(), album.getListSong().size(), time));
        Glide
                .with(mRootView.getContext())
                .load(album.getListSong().get(0).getImgPath())
                .placeholder(R.drawable.img_bg_recommend_default)
                .error(R.drawable.img_bg_recommend_default)
                .centerCrop()
                .into(mImgAvatar);

        Glide
                .with(mRootView.getContext())
                .load(album.getListSong().get(0).getImgPath())
                .placeholder(R.drawable.img_bg_recommend_default)
                .error(R.drawable.img_bg_recommend_default)
                .centerCrop()
                .into(mImgCover);

    }

    private void initAdapter() {
        mRecyclerView = mRootView.findViewById(R.id.album_songs_container);
        mAlbumDetailAdapter = new AlbumDetailAdapter(mRootView.getContext(), mListSong);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAlbumDetailAdapter);
    }

    private String strAlbumDetails(String year, int sizeAlbum, int timeAlbum) {
        return year + " . " + sizeAlbum + " Songs . " + timeAlbum + " min";
    }


}
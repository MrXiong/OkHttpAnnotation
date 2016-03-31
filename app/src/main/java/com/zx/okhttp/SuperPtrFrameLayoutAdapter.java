package com.zx.okhttp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zx.okhttp.result.model.GameInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zx on 2016/1/29.
 */
public class SuperPtrFrameLayoutAdapter extends RecyclerView.Adapter<SuperPtrFrameLayoutAdapter.ViewHolder> {

    private List<GameInfo> mDatas;

    public void setList(List<GameInfo> datas) {
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_new_game_list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GameInfo gameInfo = mDatas.get(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoader.getInstance().displayImage(gameInfo.getIconUrl(), holder.ivGameIcon, options);
        holder.tvGameName.setText(gameInfo.getName());
        holder.tvPlayingManNumber.setText(String.valueOf(gameInfo.getCommentCount()));
        if(gameInfo.getOpenState() == 0 || gameInfo.getOpenState() == 21) {
            holder.tvGameSocre.setVisibility(View.INVISIBLE);
        } else {
            holder.tvGameSocre.setText(gameInfo.getTotalSocreV() + "分");
            holder.tvGameSocre.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_game_icon)
        ImageView ivGameIcon;
        @Bind(R.id.tv_game_name)
        TextView tvGameName;
        @Bind(R.id.tv_game_socre)
        TextView tvGameSocre;
        @Bind(R.id.tv_playing_man_number)
        TextView tvPlayingManNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }


}
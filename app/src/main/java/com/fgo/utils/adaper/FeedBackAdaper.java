package com.fgo.utils.adaper;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fgo.utils.R;
import com.fgo.utils.activity.ServantActivity;
import com.fgo.utils.base.CircleImageView;
import com.fgo.utils.bean.FeedBackListBean;
import com.fgo.utils.bean.ServantListNBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfu on 2018/8/27
 */

public class FeedBackAdaper extends RecyclerView.Adapter {

    List<FeedBackListBean> feedBackList = new ArrayList<>();
    private Context mContext;

    public FeedBackAdaper(Context mContext, List<FeedBackListBean> list) {
        feedBackList = list;
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new FeedBackAdaper.FeedBackViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.feed_back_fragment_item, parent,
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FeedBackAdaper.FeedBackViewHolder FeedBackholder = (FeedBackAdaper.FeedBackViewHolder) holder;

        FeedBackListBean feedBackListBean = feedBackList.get(position);

        FeedBackholder.nameTv.setText(feedBackListBean.getName());
        FeedBackholder.contentTv.setText(feedBackListBean.getContent());
        FeedBackholder.timeTv.setText(feedBackListBean.getTime());


    }

    @Override
    public int getItemCount() {

        return feedBackList.size();
    }


    public class FeedBackViewHolder extends RecyclerView.ViewHolder {


        private final TextView nameTv;
        private final CircleImageView iconCIV;
        private final TextView contentTv;
        private final TextView timeTv;

        public FeedBackViewHolder(View itemView) {

            super(itemView);
            iconCIV = itemView.findViewById(R.id.feed_back_icon_iv);
            nameTv = itemView.findViewById(R.id.feed_backo_item_name);
            contentTv = itemView.findViewById(R.id.feed_backo_item_content);
            timeTv = itemView.findViewById(R.id.feed_backo_item_time);

        }
    }
}

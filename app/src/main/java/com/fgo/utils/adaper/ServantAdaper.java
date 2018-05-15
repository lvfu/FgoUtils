package com.fgo.utils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fgo.utils.R;
import com.fgo.utils.bean.CardNumBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfu on 2018/4/11.
 */

public class ServantAdaper extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mCardList = new ArrayList<>();

    public ServantAdaper(Context context, List<String> mCardList) {
        this.mContext = context;
        this.mCardList.addAll(mCardList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new ServantAdaper.ServantHolder(LayoutInflater.from(
                mContext).inflate(R.layout.servant_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServantAdaper.ServantHolder servantHolder = (ServantAdaper.ServantHolder) holder;
        String s = mCardList.get(position);
        switch (s) {
            case "b":
                servantHolder.mCardIv.setImageResource(R.mipmap.buster);
                break;
            case "a":
                servantHolder.mCardIv.setImageResource(R.mipmap.arts);
                break;
            case "q":
                servantHolder.mCardIv.setImageResource(R.mipmap.quick);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ServantHolder extends RecyclerView.ViewHolder {


        private final ImageView mCardIv;

        public ServantHolder(View itemView) {
            super(itemView);

            mCardIv = (ImageView) itemView.findViewById(R.id.iicm_iv_cards);
        }
    }
}

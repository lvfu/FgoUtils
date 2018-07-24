package com.fgo.utils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fgo.utils.R;
import com.fgo.utils.bean.SourceDropBean;

import java.util.ArrayList;
import java.util.List;

public class SourceDropAdaper extends RecyclerView.Adapter {
    private Context mContext;
    private List<SourceDropBean.DropInfo> dropInfoList = new ArrayList<>();

    public SourceDropAdaper(Context mContext, List<SourceDropBean.DropInfo> dropInfoList) {
        this.mContext = mContext;
        this.dropInfoList = dropInfoList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new SourceDropHolder(LayoutInflater.from(
                mContext).inflate(R.layout.source_drop_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SourceDropHolder sourceDropHolder = (SourceDropHolder) holder;

        SourceDropBean.DropInfo dropInfo = dropInfoList.get(position);

        if (position == 0) {
            sourceDropHolder.address.setTextColor(mContext.getResources().getColor(R.color.google_red));
            sourceDropHolder.location.setTextColor(mContext.getResources().getColor(R.color.google_red));
        }else {
            sourceDropHolder.address.setTextColor(mContext.getResources().getColor(R.color.google_black));
            sourceDropHolder.location.setTextColor(mContext.getResources().getColor(R.color.google_black));
        }
        sourceDropHolder.address.setText(dropInfo.getAddress());
        sourceDropHolder.location.setText("(" + dropInfo.getLocation() + ")");
        sourceDropHolder.ap.setText(dropInfo.getAp() + "AP/次");
        sourceDropHolder.apone.setText(dropInfo.getDropRate() + "AP/个");

    }

    @Override
    public int getItemCount() {
        return dropInfoList.size();
    }


    public class SourceDropHolder extends RecyclerView.ViewHolder {

        private final TextView address;
        private final TextView location;
        private final TextView ap;
        private final TextView apone;

        public SourceDropHolder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.source_drop_item_address);
            location = itemView.findViewById(R.id.source_drop_item_location);
            ap = itemView.findViewById(R.id.source_drop_item_ap);
            apone = itemView.findViewById(R.id.source_drop_item_ap_one);


        }
    }
}

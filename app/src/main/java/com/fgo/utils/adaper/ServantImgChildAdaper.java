package com.fgo.utils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fgo.utils.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lvfu on 2018/4/12.
 */

public class ServantImgChildAdaper extends RecyclerView.Adapter {
    private List<String> imgurlList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private final String url;

    public ServantImgChildAdaper(Context context, List<String> list, int posi) {

        this.imgurlList.addAll(list) ;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        url = imgurlList.get(posi);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SkillSourceChildHolder(inflater.inflate(R.layout.servant_img_child_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SkillSourceChildHolder skillSourceChildHolder = (SkillSourceChildHolder) holder;

        Glide.with(mContext.getApplicationContext()).load(url).error(R.mipmap.ic_launcher).into(skillSourceChildHolder.mSkillChildBac);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class SkillSourceChildHolder extends RecyclerView.ViewHolder {


        private final ImageView mSkillChildBac;

        public SkillSourceChildHolder(View itemView) {
            super(itemView);

            mSkillChildBac = itemView.findViewById(R.id.servant_img_child_bac_iv);
        }
    }

}

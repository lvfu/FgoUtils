package com.fgo.utils.adaper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fgo.utils.R;
import com.fgo.utils.bean.SkillSourceBean;

import java.util.List;


/**
 * Created by lvfu on 2018/4/12.
 */

public class ServantImgAdaper extends RecyclerView.Adapter {
    private List<String> imgurlList;
    private Context mContext;
    private LayoutInflater inflater;
    private ServantImgChildAdaper skillEffectValueAdaper;
    private int positions;
    private String isAct;

    public ServantImgAdaper(List<String> imgurl, Context context, int posi, String isAct) {
        positions = posi;
        this.imgurlList = imgurl;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.isAct = isAct;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SkillSourceNormalHolder(inflater.inflate(R.layout.servant_img_normal_item, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final SkillSourceNormalHolder skillSourceNormalHolder = (SkillSourceNormalHolder) holder;


        GridLayoutManager ll = new GridLayoutManager(mContext, 3);
        skillSourceNormalHolder.mSkillSourceRv.setLayoutManager(ll);

        skillEffectValueAdaper = new ServantImgChildAdaper(mContext, imgurlList, positions);
        skillSourceNormalHolder.mSkillSourceRv.setAdapter(skillEffectValueAdaper);

        skillSourceNormalHolder.mSkillSourceLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("true".equals(isAct)) {
                    if (positions == 1) {
                        positions = 0;
                        notifyDataSetChanged();
                    } else {
                        positions = positions + 1;
                        notifyDataSetChanged();
                    }
                } else {
                    if (positions == 3) {
                        positions = 0;
                        notifyDataSetChanged();
                    } else {
                        positions = positions + 1;
                        notifyDataSetChanged();
                    }
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class SkillSourceNormalHolder extends RecyclerView.ViewHolder {


        private final RecyclerView mSkillSourceRv;
        private final Button mSkillSourceLast;

        public SkillSourceNormalHolder(View itemView) {
            super(itemView);

            mSkillSourceRv = itemView.findViewById(R.id.servant_img_source_rv);
            mSkillSourceLast = itemView.findViewById(R.id.servant_img_source_last_btn);

        }
    }

    private NextOnClickListener nextOnClickListener;

    public void setNextOnClickListener(NextOnClickListener nextOnClickListener) {
        this.nextOnClickListener = nextOnClickListener;
    }

    public interface NextOnClickListener {
        void onClickListener(int posi);
    }

}

package com.fgo.utils.adaper;

import android.content.Context;
import android.content.Intent;
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
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfu on 2018/4/10.
 */

public class HeroFragmentAdaper extends RecyclerView.Adapter {

    private List<ServantItem> servantList = new ArrayList<>();
    private List<ServantSkill> servantSkillList = new ArrayList<>();
    private Context mContext;

    public HeroFragmentAdaper(List<ServantItem> list, List<ServantSkill> skillList,Context mContext) {
        servantList.addAll(list);
        servantSkillList.addAll(skillList);
        this.mContext = mContext;
    }

    public void setEmptyData() {
        servantList=null;
        servantSkillList=null;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new HeroFragmentAdaper.HeroFragmentHolder(LayoutInflater.from(
                mContext).inflate(R.layout.hero_fragment_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeroFragmentAdaper.HeroFragmentHolder holder1 = (HeroFragmentAdaper.HeroFragmentHolder) holder;
        if (servantList==null||servantSkillList==null){
            return;
        }

        final ServantItem item = servantList.get(position);
        final ServantSkill servantSkill = servantSkillList.get(position);
        int resId = mContext.getResources().getIdentifier("image" + item.getId(), "mipmap", mContext.getPackageName());
        if (resId != 0) {
            holder1.item_hero_icon.setImageResource(resId);
        } else {
            String num = "";
            if (item.getId() > 0 && item.getId() < 10) {
                num = new StringBuilder().append("00").append(item.getId()).toString();
            } else if (item.getId() >= 10 && item.getId() < 100) {
                num = new StringBuilder().append("0").append(item.getId()).toString();
            } else {
                num = new StringBuilder().append(item.getId()).toString();
            }
            //从fgowiki获取头像
            String url = new StringBuilder().append("http://file.fgowiki.fgowiki.com/fgo/head/").append(num).append(".jpg").toString();
            Glide.with(mContext.getApplicationContext()).load(url).error(R.mipmap.ic_launcher).into(holder1.item_hero_icon);

        }

        holder1.item_hero_name.setText(item.getName());
        holder1.item_hero_classType.setText(item.getClass_type());

        holder1.item_hero_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(mContext, ServantActivity.class);
                sIntent.putExtra("servants", item);
                sIntent.putExtra("servantskill", servantSkill);
                mContext.startActivity(sIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (servantList==null){
            return 0;
        }
        return servantList.size();
    }




    public class HeroFragmentHolder extends RecyclerView.ViewHolder {

        private final ImageView item_hero_icon;
        private final RelativeLayout item_hero_rl;
        private final TextView item_hero_name;
        private final TextView item_hero_classType;

        public HeroFragmentHolder(View itemView) {
            super(itemView);

            item_hero_rl = (RelativeLayout) itemView.findViewById(R.id.hero_item_ll);
            item_hero_icon = (ImageView) itemView.findViewById(R.id.hero_item_iv);
            item_hero_name = (TextView) itemView.findViewById(R.id.hero_item_name);
            item_hero_classType = (TextView) itemView.findViewById(R.id.hero_item_class_type);
        }
    }
}

package com.fgo.utils.adaper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgo.utils.R;
import com.fgo.utils.bean.SkillSourceBean;
import com.fgo.utils.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lvfu on 2018/4/12.
 */

public class SkillSourceDetailAdaper extends RecyclerView.Adapter {
    private List<SkillSourceBean> skillSourceList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private final List<String> skill_material_img;
    private final List<String> skill_material_num;

    public SkillSourceDetailAdaper(Context context,List<SkillSourceBean> skillSourceList,int posi) {

        this.skillSourceList.addAll(skillSourceList) ;
        mContext = context;
        inflater = LayoutInflater.from(mContext);

        skill_material_img = skillSourceList.get(posi).getSkill_material_img();
        skill_material_num = skillSourceList.get(posi).getSkill_material_num();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SkillSourceChildHolder(inflater.inflate(R.layout.skill_source_child_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SkillSourceChildHolder skillSourceChildHolder = (SkillSourceChildHolder) holder;

        String servant_treasure = subString(skill_material_img.get(position).toLowerCase());
        skillSourceChildHolder.mSkillChildCunt.setText(skill_material_num.get(position)+ "");
        skillSourceChildHolder.mSkillChildBac.setImageResource(CommonUtils.getResourceId(servant_treasure, mContext));


    }
    public String subString(String str) {
        String[] split = str.split(".png");
        return split[0];
    }
    @Override
    public int getItemCount() {
        return skill_material_img.size();
    }


    public class SkillSourceChildHolder extends RecyclerView.ViewHolder {


        private final ImageView mSkillChildBac;
        private final TextView mSkillChildCunt;

        public SkillSourceChildHolder(View itemView) {
            super(itemView);

            mSkillChildBac = itemView.findViewById(R.id.skill_child_bac_iv);
            mSkillChildCunt = itemView.findViewById(R.id.skill_child_cunt_tv);
        }
    }

}

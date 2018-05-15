package com.fgo.utils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fgo.utils.R;
import com.fgo.utils.bean.SkillSmallBean;

import java.util.List;

/**
 * Created by lvfu on 2018/4/12.
 */

public class SkillEffectValueAdaper extends RecyclerView.Adapter {
    private Context mContext;
    private String skillValue;
    private LayoutInflater inflater;

    private final String[] skillValues;

    public SkillEffectValueAdaper(Context mContext, String skillValue) {
        this.mContext = mContext;
        this.skillValue = skillValue;
        skillValues = skillValue.split("\\|");
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SkillValueMoreHolder(inflater.inflate(R.layout.skill_value_item, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SkillEffectValueAdaper.SkillValueMoreHolder skillValueMoreHolder = (SkillEffectValueAdaper.SkillValueMoreHolder) holder;

        skillValueMoreHolder.mAttributeValueBody.setText(skillValues[position]);
    }


    @Override
    public int getItemCount() {
        return skillValues.length;
    }


    public class SkillValueMoreHolder extends RecyclerView.ViewHolder {


        private final TextView mAttributeValueBody;

        public SkillValueMoreHolder(View itemView) {
            super(itemView);

            mAttributeValueBody = itemView.findViewById(R.id.skill_attribute_value_title);
        }
    }
}

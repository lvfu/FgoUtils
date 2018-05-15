package com.fgo.utils.adaper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fgo.utils.R;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.bean.SkillSmallBean;

import java.util.List;


/**
 * Created by lvfu on 2018/4/12.
 */

public class SkillAdaper extends RecyclerView.Adapter {
    private ServantSkill servantSkillItem;
    private SkillSmallBean skillBean;
    private Context mContext;
    private final List<String> skillEffect;
    private final List<String> skillEffectValue;
    private int VIEW_MORE = 0;

    private int VIEW_NORMAL = 1;
    private LayoutInflater inflater;

    public SkillAdaper(ServantSkill servantSkillItem, SkillSmallBean skillbean, Context context) {
        this.servantSkillItem = servantSkillItem;
        this.skillBean = skillbean;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        skillEffect = skillBean.getSkillEffect();
        skillEffectValue = skillBean.getSkillEffectValue();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_NORMAL) {
            return new SkillValueNormalHolder(inflater.inflate(R.layout.skill_normal_item, parent, false));
        } else if (viewType == VIEW_MORE) {
            return new SkillValueMoreHolder(inflater.inflate(R.layout.skill_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SkillValueNormalHolder) {
            SkillAdaper.SkillValueNormalHolder skillHolder = (SkillAdaper.SkillValueNormalHolder) holder;
            skillHolder.mAttributeNormalTitle.setText(skillEffect.get(position));

            skillHolder.mAttributeNormalBody.setText(skillEffectValue.get(position));

        } else if (holder instanceof SkillValueMoreHolder) {
            SkillAdaper.SkillValueMoreHolder skillHolder = (SkillAdaper.SkillValueMoreHolder) holder;
            skillHolder.mAttributeTitle.setText(skillEffect.get(position));

            //ll
            GridLayoutManager ll = new GridLayoutManager(mContext, 5);
            skillHolder.mAttributeRv.setLayoutManager(ll);

            SkillEffectValueAdaper skillEffectValueAdaper = new SkillEffectValueAdaper(mContext, skillEffectValue.get(position));
            skillHolder.mAttributeRv.setAdapter(skillEffectValueAdaper);
        }

    }

    @Override
    public int getItemCount() {
        return skillEffect.size();
    }

    public class SkillValueMoreHolder extends RecyclerView.ViewHolder {

        private final TextView mAttributeTitle;
        private final RecyclerView mAttributeRv;

        public SkillValueMoreHolder(View itemView) {
            super(itemView);

            mAttributeTitle = itemView.findViewById(R.id.skill_attribute_title);
            mAttributeRv = itemView.findViewById(R.id.skill_attribute_rv);
        }
    }

    public class SkillValueNormalHolder extends RecyclerView.ViewHolder {


        private final TextView mAttributeNormalTitle;
        private final TextView mAttributeNormalBody;

        public SkillValueNormalHolder(View itemView) {
            super(itemView);

            mAttributeNormalTitle = itemView.findViewById(R.id.skill_attribute_normal_title);
            mAttributeNormalBody = itemView.findViewById(R.id.skill_attribute_normal_value);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String s = skillEffectValue.get(position);
        String[] split = s.split("\\|");
        if (split.length == 1) {
            return 1;
        } else {
            return 0;
        }

    }

}

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
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.bean.SkillSmallBean;
import com.fgo.utils.bean.SkillSourceBean;

import java.util.List;


/**
 * Created by lvfu on 2018/4/12.
 */

public class SkillSourceAdaper extends RecyclerView.Adapter {
    private List<SkillSourceBean> skillSourceList;
    private Context mContext;
    private LayoutInflater inflater;
    private SkillSourceDetailAdaper skillEffectValueAdaper;
    private int positions;
    private String isCome;
    private boolean isMaXiu;

    public SkillSourceAdaper(List<SkillSourceBean> skillSourceList, Context context, int posi, String isCome) {
        positions = posi;
        this.skillSourceList = skillSourceList;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.isCome = isCome;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SkillSourceNormalHolder(inflater.inflate(R.layout.skill_source_normal_item, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final SkillSourceNormalHolder skillSourceNormalHolder = (SkillSourceNormalHolder) holder;

        skillSourceNormalHolder.mSkillSourceQp.setText(skillSourceList.get(positions).getSkill_cost());
        skillSourceNormalHolder.mSkillSourceLv.setText(skillSourceList.get(positions).getSkill_lv());

        GridLayoutManager ll = new GridLayoutManager(mContext, 3);
        skillSourceNormalHolder.mSkillSourceRv.setLayoutManager(ll);

        skillEffectValueAdaper = new SkillSourceDetailAdaper(mContext, skillSourceList, positions);
        skillSourceNormalHolder.mSkillSourceRv.setAdapter(skillEffectValueAdaper);

        skillSourceNormalHolder.mSkillSourceLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("skill".equals(isCome)) {
                    if (positions == 8) {
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
        private final TextView mSkillSourceQp;
        private final TextView mSkillSourceLv;
        private final Button mSkillSourceLast;
        private final LinearLayout mSkillSourceParentLl;

        public SkillSourceNormalHolder(View itemView) {
            super(itemView);

            mSkillSourceRv = itemView.findViewById(R.id.skill_source_rv);
            mSkillSourceQp = itemView.findViewById(R.id.skill_source_qp);
            mSkillSourceLv = itemView.findViewById(R.id.skill_source_lv);
            mSkillSourceLast = itemView.findViewById(R.id.skill_source_last_btn);
            mSkillSourceParentLl = itemView.findViewById(R.id.skill_source_parent_ll);

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

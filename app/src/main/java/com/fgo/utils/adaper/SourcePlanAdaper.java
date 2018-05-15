package com.fgo.utils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgo.utils.R;
import com.fgo.utils.bean.SourcePlanBean;
import com.fgo.utils.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfu on 2018/4/16.
 */

public class SourcePlanAdaper extends RecyclerView.Adapter {

    private Context mContext;
    private List<SourcePlanBean> sourcePlanList = new ArrayList<>();
    private LayoutInflater inflater;


    public SourcePlanAdaper(Context context, List<SourcePlanBean> sourcePlan) {
        mContext = context;
        sourcePlanList.addAll(sourcePlan);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SourcePlanHolder(inflater.inflate(R.layout.source_plan_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SourcePlanHolder sourcePlanHolder = (SourcePlanHolder) holder;

        SourcePlanBean sourcePlanBean = sourcePlanList.get(position);
        int resourceId = CommonUtils.getResourceId(sourcePlanBean.getImg(), mContext);
        sourcePlanHolder.mSourcePlanImg.setImageResource(resourceId);
        sourcePlanHolder.mSourcePlanName.setText(sourcePlanBean.getName());

        //计算素材
        //需求
        sourcePlanHolder.mSourcePlanNeed.setText("需求: " + sourcePlanBean.getNeed() + "");
        //持有
        sourcePlanHolder.mSourcePlanParrent.setText("持有: " + sourcePlanBean.getHave() + "");
        //剩余
        int surplus = sourcePlanBean.getHave() - sourcePlanBean.getNeed();
        if (surplus >= 0) {
            sourcePlanHolder.mSourcePlanSurplus.setText("剩余: " + surplus);
        } else {
            sourcePlanHolder.mSourcePlanSurplus.setText("缺口: " + surplus);
        }


    }

    @Override
    public int getItemCount() {
        return sourcePlanList.size();
    }


    public class SourcePlanHolder extends RecyclerView.ViewHolder {


        private final ImageView mSourcePlanImg;
        private final TextView mSourcePlanName;
        private final TextView mSourcePlanNeed;
        private final TextView mSourcePlanParrent;
        private final TextView mSourcePlanSurplus;

        public SourcePlanHolder(View itemView) {
            super(itemView);

            mSourcePlanImg = itemView.findViewById(R.id.source_plan_item_iv);
            mSourcePlanName = itemView.findViewById(R.id.source_plan_item_name);

            mSourcePlanNeed = itemView.findViewById(R.id.source_plan_item_need);
            mSourcePlanParrent = itemView.findViewById(R.id.source_plan_item_parent);
            mSourcePlanSurplus = itemView.findViewById(R.id.source_plan_item_surplus);
        }
    }
}

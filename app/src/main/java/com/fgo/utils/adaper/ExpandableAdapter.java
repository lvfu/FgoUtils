package com.fgo.utils.adaper;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fgo.utils.R;
import com.fgo.utils.bean.SourcePlanBean;
import com.fgo.utils.bean.SourcesPlanBean;
import com.fgo.utils.ui.view.stickead.BaseViewHolder;
import com.fgo.utils.ui.view.stickead.GroupedRecyclerViewAdapter;
import com.fgo.utils.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ExpandableAdapter extends GroupedRecyclerViewAdapter {
    private Context mContext;
    private List<SourcesPlanBean> sourcePlanList = new ArrayList<>();

    public ExpandableAdapter(Context context, List<SourcesPlanBean> sourcePlan) {
        super(context);
        mContext = context;
        sourcePlanList.addAll(sourcePlan);
    }

    public void setData(List<SourcesPlanBean> sourcePlan, int pos) {

        SourcesPlanBean sourcePlanBean = sourcePlanList.get(pos);
        //如果是打开的就让他继续打开
        if (sourcePlanBean.isExpand()) {

            sourcePlanBean.setExpand(true);
            sourcePlan.get(pos).setExpand(true);
        } else {
            sourcePlanBean.setExpand(false);
            sourcePlan.get(pos).setExpand(false);
        }

        //数据替换
        sourcePlanList.clear();
        sourcePlanList.addAll(sourcePlan);

        changeGroup(pos);
    }

    @Override
    public int getGroupCount() {
        return sourcePlanList == null ? 0 : sourcePlanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //如果当前组收起，就直接返回0，否则才返回子项数。这是实现列表展开和收起的关键。
        if (!isExpand(groupPosition)) {
            return 0;
        }
        return 1;
    }


    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.source_plan_item;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.foot;
    }


    @Override
    public int getChildLayout(int viewType) {
        return R.layout.child;
    }

    @Override
    public int getTypeEmpty(int viewType) {
        return R.layout.empty_layout;
    }


    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, final int groupPosition) {

        SourcesPlanBean sourcePlanBean = sourcePlanList.get(groupPosition);
        ImageView mSourcePlanImg = holder.get(R.id.source_plan_item_iv);
        TextView mSourcePlanName = holder.get(R.id.source_plan_item_name);
        ImageView ivState = holder.get(R.id.sample_activity_list_group_expanded_image);
        TextView mSourcePlanNeed = holder.get(R.id.source_plan_item_need);


        int surplus = sourcePlanBean.getSourceCountHave() - sourcePlanBean.getSourceCount();
        if (surplus >= 0) {
            mSourcePlanNeed.setVisibility(View.GONE);
        } else {
            mSourcePlanNeed.setVisibility(View.VISIBLE);
            mSourcePlanNeed.setText("缺口: " + surplus);
        }


        if (sourcePlanBean.isExpand()) {
            ivState.setImageResource(R.mipmap.product_manger_pointer);
        } else {
            ivState.setImageResource(R.mipmap.product_manger_pointer_show);
        }

        //设置数据
        int resourceId = CommonUtils.getResourceId(sourcePlanBean.getSourceImage(), mContext);
        mSourcePlanImg.setImageResource(resourceId);
        mSourcePlanName.setText(sourcePlanBean.getSourceName());

        ivState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headImageClickListener.clickHeadListener(groupPosition);
            }
        });
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition, boolean flag) {

    }


    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, final int groupPosition, int childPosition) {
        final SourcesPlanBean sourcePlanBean = sourcePlanList.get(groupPosition);

        TextView mSourcePlanNeed = holder.get(R.id.source_plan_item_need);
        final TextView mSourcePlanParrent = holder.get(R.id.source_plan_item_parent);
        final TextView mSourcePlanSurplus = holder.get(R.id.source_plan_item_surplus);

        Button mSourcePlanSetting = holder.get(R.id.source_plan_item_bt_source_setting);

        Button mSourcePlanSearch = holder.get(R.id.source_plan_item_bt_source_search);

        //计算素材
        //需求
        mSourcePlanNeed.setText("需求: " + sourcePlanBean.getSourceCount() + "");
        //持有
        mSourcePlanParrent.setText("持有: " + sourcePlanBean.getSourceCountHave() + "");
        //剩余
        int surplus = sourcePlanBean.getSourceCountHave() - sourcePlanBean.getSourceCount();
        if (surplus >= 0) {
            mSourcePlanSurplus.setText("剩余: " + surplus);
        } else {
            mSourcePlanSurplus.setText("缺口: " + surplus);
        }

        mSourcePlanSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mContext)
                        .title(R.string.input)
                        .content(R.string.input_content)
                        .inputType(
                                InputType.TYPE_NUMBER_VARIATION_NORMAL)
                        .inputRange(1, 16)
                        .positiveText(R.string.submit)
                        .input(
                                R.string.input_hint,
                                R.string.input_hint,
                                false,
                                new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        mSourcePlanParrent.setText("持有: " + input + "");
                                        //剩余
                                        int surplus = Integer.parseInt(input + "") - sourcePlanBean.getSourceCount();
                                        if (surplus >= 0) {
                                            mSourcePlanSurplus.setText("剩余: " + surplus);
                                        } else {
                                            mSourcePlanSurplus.setText("缺口: " + surplus);
                                        }

                                        sourceInputListener.inputListener(input, groupPosition);
                                    }
                                })
                        .show();
            }
        });

    }


    @Override
    public void onBindEmptyViewHolder(BaseViewHolder holder, int groupPosition) {

    }


    /**
     * 判断当前组是否展开
     *
     * @param groupPosition
     * @return
     */
    public boolean isExpand(int groupPosition) {
        SourcesPlanBean sourcePlanBean = sourcePlanList.get(groupPosition);
        return sourcePlanBean.isExpand();
    }

    /**
     * 展开一个组
     *
     * @param groupPosition
     */
    public void expandGroup(int groupPosition) {
        expandGroup(groupPosition, false);
    }

    /**
     * 展开一个组
     *
     * @param groupPosition
     * @param animate
     */
    public void expandGroup(int groupPosition, boolean animate) {
        SourcesPlanBean sourcePlanBean = sourcePlanList.get(groupPosition);
        sourcePlanBean.setExpand(true);
        if (animate) {
            insertChildren(groupPosition);
        } else {
            changeDataSet();

        }
    }

    /**
     * 收起一个组
     *
     * @param groupPosition
     */
    public void collapseGroup(int groupPosition) {
        collapseGroup(groupPosition, false);
    }

    /**
     * 收起一个组
     *
     * @param groupPosition
     * @param animate
     */
    public void collapseGroup(int groupPosition, boolean animate) {
        SourcesPlanBean sourcePlanBean = sourcePlanList.get(groupPosition);
        sourcePlanBean.setExpand(false);
        if (animate) {
            removeChildren(groupPosition);
        } else {
            changeDataSet();
        }
    }

    public void toChangeGroup(int groupPosition) {
        changeGroup(groupPosition);
    }

    //按钮点击回调，不用他们组头的点击回调
    private HeadImageClickListener headImageClickListener;

    public void setHeadImageClickListener(HeadImageClickListener listener) {
        this.headImageClickListener = listener;
    }


    public interface HeadImageClickListener {
        void clickHeadListener(int groupPos);
    }

    //素材输入回调
    private SourceInputListener sourceInputListener;

    public void setSourceInputListener(SourceInputListener sourceInputListener) {
        this.sourceInputListener = sourceInputListener;
    }

    public interface SourceInputListener {
        void inputListener(CharSequence input, int pos);
    }
}

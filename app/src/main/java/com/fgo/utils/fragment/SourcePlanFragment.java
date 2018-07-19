package com.fgo.utils.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.king.frame.mvp.base.QuickFragment;
import com.fgo.utils.R;
import com.fgo.utils.adaper.ExpandableAdapter;

import com.fgo.utils.bean.MessageEvent;
import com.fgo.utils.bean.SourcePlanBean;
import com.fgo.utils.mvp.presenter.SourcePlanPresenter;
import com.fgo.utils.mvp.view.SourcePlanView;
import com.fgo.utils.ui.view.stickead.StickyHeaderLayout;
import com.fgo.utils.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfu on 2018/3/26.
 */

public class SourcePlanFragment extends QuickFragment<SourcePlanView, SourcePlanPresenter> implements SourcePlanView {

    private List<SourcePlanBean> sourcePlan = new ArrayList<>();
    private RecyclerView mSourcePlanRv;
    private ExpandableAdapter sourcePlanAdaper;
    private StickyHeaderLayout stickyHeaderLayout;

    private String msg;
    private SourcePlanPresenter sourcePlanPresenter;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_source_plan;
    }

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);



        mSourcePlanRv = getRootView().findViewById(R.id.plan_fragment_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mSourcePlanRv.setLayoutManager(linearLayoutManager);

        //吸顶布局
        stickyHeaderLayout = getRootView().findViewById(R.id.sticky_layout);
        stickyHeaderLayout.setSticky(true);
    }

    @Override
    public void initData() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("refresh")) {
//            getPresenter().getSourceData();
        }
    }


    @Override
    public SourcePlanPresenter createPresenter() {
        sourcePlanPresenter = new SourcePlanPresenter();
        return sourcePlanPresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setSourcePlanData(ArrayList<SourcePlanBean> list) {
        sourcePlan.clear();
        this.sourcePlan.addAll(list);


        sourcePlanAdaper = new ExpandableAdapter(getContext(), sourcePlan);


        sourcePlanAdaper.setHeadImageClickListener(new ExpandableAdapter.HeadImageClickListener() {
            @Override
            public void clickHeadListener(int groupPosition) {
                if (sourcePlanAdaper.isExpand(groupPosition)) {
                    sourcePlanAdaper.collapseGroup(groupPosition);
                    //头部位于最上边
                    mSourcePlanRv.smoothScrollBy(0, -1);

                } else {
                    sourcePlanAdaper.expandGroup(groupPosition);
                    mSourcePlanRv.smoothScrollBy(0, -1);


                }
            }
        });

        //输入资源后回调
        sourcePlanAdaper.setSourceInputListener(new ExpandableAdapter.SourceInputListener() {
            @Override
            public void inputListener(CharSequence input, int pos) {
//                String name = sourcePlan.get(pos).getName();
//                //把素材写入数据库
//                setDataToDb(name, input + "");
//
//                //从数据库读取
////                Cursor cur;
////                cur = dbManager.database.rawQuery("SELECT * FROM Materials WHERE " +
////                                "name LIKE ?",
////                        new String[]{"%" + name + "%"});
//
//                //模糊查询
//                Cursor cur = DbUtils.searchData(dbManager, getContext(), "Materials", name, "", false);
//                ArrayList<SourcePlanBean> sourcePlanBeanList = CommonUtils.getSkillList(cur);
//
//                if (cur != null) {
//                    cur.close();
//                }
//                dbManager.closeDatabase();
//
//                //获取对应位置bean替换，局部刷新
//                SourcePlanBean sourcePlanBean = sourcePlanBeanList.get(0);
//                sourcePlan.set(pos, sourcePlanBean);
//
//                sourcePlanAdaper.setData(sourcePlan, pos);


            }
        });

        mSourcePlanRv.setAdapter(sourcePlanAdaper);
    }


    private void setDataToDb(String name, String num) {
        //qi 1
//        dbManager.getDatabase();
//
//
//        int value = Integer.parseInt(num);
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("have", value);
//
//        String[] args = {String.valueOf(name)};
//        dbManager.database.update("Materials", contentValues, " name LIKE ?", args);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

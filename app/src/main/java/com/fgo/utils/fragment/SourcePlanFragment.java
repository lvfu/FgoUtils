package com.fgo.utils.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.fgo.utils.activity.SourceDropActivity;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.SourcesPlanBean;
import com.fgo.utils.utils.SharedPreferencesUtils;
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

    private RecyclerView mSourcePlanRv;
    private ExpandableAdapter sourcePlanAdaper;
    private StickyHeaderLayout stickyHeaderLayout;

    private String msg;
    private SourcePlanPresenter sourcePlanPresenter;
    private int userId;
    private List<SourcesPlanBean> sourcePlan;
    private int updatePosition;

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
        userId = (int) SharedPreferencesUtils.getParam(getContext(), "userId", 0);

        sourcePlanPresenter.initSourceList(userId);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("refresh")) {

            userId = (int) SharedPreferencesUtils.getParam(getContext(), "userId", 0);
            sourcePlanPresenter.initSourceList(userId);
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


    public void initAdaper(List<SourcesPlanBean> list) {

        sourcePlanAdaper = new ExpandableAdapter(getContext(), list);
        mSourcePlanRv.setAdapter(sourcePlanAdaper);

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
            public void inputListener(CharSequence input, String name, int posi) {
                userId = (int) SharedPreferencesUtils.getParam(getContext(), "userId", 0);
                if (userId != 0) {
                    int count = Integer.parseInt(input + "");
                    updatePosition = posi;
                    sourcePlanPresenter.insertSourceCount(userId, count, name);
                } else {
                    Toast.makeText(getContext(), "请登陆账号", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //掉落
        sourcePlanAdaper.setSourceDropListener(new ExpandableAdapter.SourceDropListener() {
            @Override
            public void dropListener(int pos) {
                Intent intent = new Intent(getContext(), SourceDropActivity.class);
                intent.putExtra("position", pos);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void parseSouceListData(BaseCommonBean body) {
        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();
        if ("success".equals(respCode)) {
            sourcePlan = data.getList();
            initAdaper(sourcePlan);
        } else {
            Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void parseInsertData(BaseCommonBean body) {
        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();
        if ("success".equals(respCode)) {
            SourcesPlanBean sourcesPlanBean = (SourcesPlanBean) data.getModel();

            //获取对应位置bean替换，局部刷新
            sourcePlan.set(updatePosition, sourcesPlanBean);

            sourcePlanAdaper.setData(sourcePlan, updatePosition);

        } else {
            Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();

        }
    }
}

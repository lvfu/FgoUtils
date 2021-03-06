package com.fgo.utils.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fgo.utils.R;
import com.fgo.utils.adaper.HeroFragmentAdaper;
import com.fgo.utils.adaper.SourceDropAdaper;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.bean.SourceDropBean;
import com.fgo.utils.mvp.presenter.ServantPresenter;
import com.fgo.utils.mvp.presenter.SourceDropPresenter;
import com.fgo.utils.mvp.view.ServantView;
import com.fgo.utils.mvp.view.SourceDropView;
import com.fgo.utils.utils.CommonUtils;
import com.fgo.utils.utils.StatusBarUtil;
import com.king.frame.mvp.base.QuickActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class SourceDropActivity extends QuickActivity<SourceDropView, SourceDropPresenter> implements SourceDropView {

    ActivityHandler handler = new ActivityHandler(this);

    private SourceDropPresenter sourceDropPresenter;
    private ImageView mSourceImg;
    private TextView mSourceName;
    private RecyclerView mSourceRv;
    private List<SourceDropBean.DropInfo> dropInfoList;
    private LinearLayout ll_empty;

    @NonNull
    @Override
    public SourceDropPresenter createPresenter() {

        sourceDropPresenter = new SourceDropPresenter();
        return sourceDropPresenter;
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_source_drop;
    }

    @Override
    public void initUI() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);

        mSourceImg = findViewById(R.id.hero_item_iv);
        mSourceName = findViewById(R.id.source_drop_source_name_tv);
        mSourceRv = findViewById(R.id.source_drop_rv);

        ll_empty = findView(R.id.empty_data_normal);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mSourceRv.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void initData() {
        int position = getIntent().getIntExtra("position", -1);

        sourceDropPresenter.getSourceDropData(position + "");

    }


    @Override
    public void parseSouceDropData(BaseCommonBean body) {
        handler.sendEmptyMessageDelayed(1, 200);
        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();
        if ("success".equals(respCode)) {
            SourceDropBean model = (SourceDropBean) data.getModel();
            String name = model.getName();
            String img = model.getImg();
            dropInfoList = model.getDropInfoList();

            //设置name,img
            mSourceName.setText(name);
            int resourceId = CommonUtils.getResourceId(img, this);
            mSourceImg.setImageResource(resourceId);

            if (dropInfoList.size() == 0) {
                handler.sendEmptyMessageDelayed(2, 200);
                return;
            }
            //设置adaper
            SourceDropAdaper sourceDropAdaper = new SourceDropAdaper(this, dropInfoList);
            mSourceRv.setAdapter(sourceDropAdaper);

        } else {
            Toast.makeText(this, respMsg, Toast.LENGTH_SHORT).show();
        }
    }


    private class ActivityHandler extends Handler {

        WeakReference<SourceDropActivity> outerClass;

        ActivityHandler(SourceDropActivity activity) {
            outerClass = new WeakReference<SourceDropActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SourceDropActivity theClass = outerClass.get();
            switch (msg.what) {
                //正常
                case 1:
                    mSourceRv.setVisibility(View.VISIBLE);
                    ll_empty.setVisibility(View.GONE);
                    break;
                //空数据
                case 2:
                    mSourceRv.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                    break;
                //网络错误
                case 3:

                    break;
                default: {

                }
            }

        }
    }
}

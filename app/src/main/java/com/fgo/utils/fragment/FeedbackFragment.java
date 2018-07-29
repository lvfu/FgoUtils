package com.fgo.utils.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fgo.utils.activity.SearchActivity;
import com.fgo.utils.adaper.FeedBackAdaper;
import com.fgo.utils.adaper.HeroFragmentAdaper;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.FeedBackListBean;
import com.fgo.utils.bean.MessageEvent;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.utils.CommonUtils;
import com.fgo.utils.utils.SharedPreferencesUtils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.king.frame.mvp.base.QuickFragment;
import com.fgo.utils.R;
import com.fgo.utils.mvp.presenter.FeedbackPresenter;
import com.fgo.utils.mvp.view.FeedbackView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 反馈区
 * Created by lvfu on 2018/3/26.
 */

public class FeedbackFragment extends QuickFragment<FeedbackView, FeedbackPresenter> implements FeedbackView, View.OnClickListener {

    private RecyclerView feedbackRv;
    private FeedbackPresenter feedbackPresenter;
    private FloatingActionMenu menuRed;
    private FloatingActionButton mHeroSearch;
    private int userId;
    private String nickname;
    private String inputStr;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_feedback;
    }

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);

        feedbackRv = findView(R.id.feedback_list);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        feedbackRv.setLayoutManager(linearLayoutManager);

        //fly button
        menuRed = getRootView().findViewById(R.id.menu_red);
        mHeroSearch = getRootView().findViewById(R.id.fab0);
        mHeroSearch.setOnClickListener(this);


    }

    @Override
    public void initData() {
        feedbackPresenter.getFeedBackData();
    }

    @Override
    public FeedbackPresenter createPresenter() {
        feedbackPresenter = new FeedbackPresenter();
        return feedbackPresenter;
    }


    @Override
    public void showFeedBackData(BaseCommonBean<FeedBackListBean> body) {

        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();

        if ("success".equals(respCode)) {
            List<FeedBackListBean> feedBackList = data.getList();
            if (feedBackList != null & feedBackList.size() > 0) {

                FeedBackAdaper feedBackAdaper = new FeedBackAdaper(getContext(), feedBackList);
                feedbackRv.setAdapter(feedBackAdaper);

            }

        } else {

            Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void showInsertResult(BaseCommonBean body) {
        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();

        if ("success".equals(respCode)) {
            String result = data.getResult();
            if ("True".equals(result)) {

                feedbackPresenter.getFeedBackData();

            } else {
                Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();
            }


        } else {

            Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fab0:
                //search
                menuRed.close(true);
                userId = (int) SharedPreferencesUtils.getParam(getContext(), "userId", 0);
                nickname = (String) SharedPreferencesUtils.getParam(getContext(), "nickname", "");

                if (userId == 0) {
                    Toast.makeText(getContext(), "登陆以后才可以发表信息！", Toast.LENGTH_SHORT).show();
                    return;
                }

                new MaterialDialog.Builder(getContext())
                        .title(R.string.input_tiele)
                        .content(R.string.input_content_feedback)
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .positiveText(R.string.submit)
                        .alwaysCallInputCallback()
                        .input(R.string.input_hint,
                                0,
                                false, new MaterialDialog.InputCallback() {

                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        inputStr = input.toString();

                                    }
                                }
                        )

                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                insertFeedBack(inputStr.toString().trim());
                            }
                        })
                        .show();
                break;
        }
    }


    private void insertFeedBack(String content) {

        String time = CommonUtils.getTime();
        feedbackPresenter.insertFeedBackData(userId, nickname, content, time);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {

        if (messageEvent.getMessage().equals("FeedBackRefresh")) {

            feedbackPresenter.getFeedBackData();

        }
    }
}

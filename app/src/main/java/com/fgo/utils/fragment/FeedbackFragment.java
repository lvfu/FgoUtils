package com.fgo.utils.fragment;

import com.king.frame.mvp.base.QuickFragment;
import com.fgo.utils.R;
import com.fgo.utils.mvp.presenter.FeedbackPresenter;
import com.fgo.utils.mvp.view.FeedbackView;

/**
 * 反馈区
 * Created by lvfu on 2018/3/26.
 */

public class FeedbackFragment extends QuickFragment<FeedbackView, FeedbackPresenter> {

    @Override
    public int getRootViewId() {
        return R.layout.fragment_feedback;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

    }

    @Override
    public FeedbackPresenter createPresenter() {
        FeedbackPresenter feedbackPresenter = new FeedbackPresenter();
        return feedbackPresenter;
    }
}

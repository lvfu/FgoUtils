package com.fgo.utils.fragment;

import com.king.frame.mvp.base.QuickFragment;
import com.fgo.utils.R;
import com.fgo.utils.mvp.presenter.SourcePresenter;
import com.fgo.utils.mvp.view.SourceView;

/**
 * Created by lvfu on 2018/3/26.
 */

public class SourceFragment extends QuickFragment<SourceView,SourcePresenter> {
    @Override
    public int getRootViewId() {
        return R.layout.fragment_source;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

    }

    @Override
    public SourcePresenter createPresenter() {
        return new SourcePresenter();
    }
}

package com.fgo.utils.mvp.view;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.FeedBackListBean;
import com.king.frame.mvp.base.BaseView;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public interface FeedbackView extends BaseView {


    void showFeedBackData(BaseCommonBean<FeedBackListBean> body);

    void showInsertResult(BaseCommonBean body);
}

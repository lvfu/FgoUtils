package com.fgo.utils.mvp.view;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.LoginBean;
import com.fgo.utils.bean.userBean;
import com.king.frame.mvp.base.BaseView;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public interface PersonView extends BaseView {

    void showRegeistData(BaseCommonBean body);

    void showLoginData(BaseCommonBean<LoginBean> body);
}

package com.fgo.utils.mvp.view;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantListBean;
import com.fgo.utils.bean.ServantListNBean;
import com.king.frame.mvp.base.BaseView;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public interface HeroView extends BaseView {

    void showServantList(BaseCommonBean<ServantListNBean> body);
}

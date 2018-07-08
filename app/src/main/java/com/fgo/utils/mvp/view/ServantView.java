package com.fgo.utils.mvp.view;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantDetailBean;
import com.king.frame.mvp.base.BaseView;

/**
 * Created by lvfu on 2018/4/11.
 */

public interface ServantView extends BaseView{
    void showServantData(BaseCommonBean<ServantDetailBean> body);
}

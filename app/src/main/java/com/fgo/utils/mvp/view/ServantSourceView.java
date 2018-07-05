package com.fgo.utils.mvp.view;

import com.fgo.utils.bean.ServantAdvancedBean;
import com.king.frame.mvp.base.BaseView;

/**
 * Created by lvfu on 2018/4/16.
 */

public interface ServantSourceView extends BaseView {
    void showServantAdvancedData(ServantAdvancedBean body);
}

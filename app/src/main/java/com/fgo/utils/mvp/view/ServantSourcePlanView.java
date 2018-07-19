package com.fgo.utils.mvp.view;

import com.fgo.utils.bean.BaseCommonBean;
import com.king.frame.mvp.base.BaseView;

/**
 * Created by lvfu on 2018/4/16.
 */

public interface ServantSourcePlanView extends BaseView {
    void showServantsSouceData(BaseCommonBean body);

    void showInsertSouceData(BaseCommonBean body);
}

package com.fgo.utils.mvp.view;

import com.fgo.utils.bean.ServantDetailBean;
import com.fgo.utils.bean.ServantSkillBean;
import com.king.frame.mvp.base.BaseView;

/**
 * Created by lvfu on 2018/4/12.
 */

public interface SkillView extends BaseView{
    void showServantSkillData(ServantSkillBean body);
}

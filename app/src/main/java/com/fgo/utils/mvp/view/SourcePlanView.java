package com.fgo.utils.mvp.view;

import com.king.frame.mvp.base.BaseView;
import com.fgo.utils.bean.SourcePlanBean;

import java.util.ArrayList;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public interface SourcePlanView extends BaseView {


    void setSourcePlanData(ArrayList<SourcePlanBean> sourcePlan);
}

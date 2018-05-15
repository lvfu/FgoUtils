package com.fgo.utils.mvp.view;

import com.king.frame.mvp.base.BaseView;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;

import java.util.List;

public interface SearchView extends BaseView {
    void setServantList(List<ServantItem> servantList,List<ServantSkill> servantSkillList);
}

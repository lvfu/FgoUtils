package com.fgo.utils.mvp.view;

import com.king.frame.mvp.base.BaseView;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;

import java.util.List;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public interface IIPAddrView extends BaseView {

    void showCharacter(String string);

    void setServantList(List<ServantItem> servantList,List<ServantSkill> servantSkillList );
}

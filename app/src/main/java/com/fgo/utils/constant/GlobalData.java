package com.fgo.utils.constant;

import com.fgo.utils.bean.ServantSkill;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    public static GlobalData globalData;

    public static GlobalData getInstance() {
        if (globalData == null) {
            return globalData = new GlobalData();
        }

        return globalData;
    }

    public List<ServantSkill> servantSkillsList = new ArrayList<>();
}

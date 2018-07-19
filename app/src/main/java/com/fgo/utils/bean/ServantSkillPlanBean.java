package com.fgo.utils.bean;

import java.util.List;

public class ServantSkillPlanBean {

    private String skillLv;

    private String skillNameOne;
    private String skillNameTwo;
    private String skillNameThree;

    private List<SkillSourceBean> skillSourceList;
    private List<SkillSourceBean> servantSourceList;

    public String getSkillLv() {
        return skillLv;
    }

    public void setSkillLv(String skillLv) {
        this.skillLv = skillLv;
    }

    public String getSkillNameOne() {
        return skillNameOne;
    }

    public void setSkillNameOne(String skillNameOne) {
        this.skillNameOne = skillNameOne;
    }

    public String getSkillNameTwo() {
        return skillNameTwo;
    }

    public void setSkillNameTwo(String skillNameTwo) {
        this.skillNameTwo = skillNameTwo;
    }

    public String getSkillNameThree() {
        return skillNameThree;
    }

    public void setSkillNameThree(String skillNameThree) {
        this.skillNameThree = skillNameThree;
    }

    public List<SkillSourceBean> getSkillSourceList() {
        return skillSourceList;
    }

    public void setSkillSourceList(List<SkillSourceBean> skillSourceList) {
        this.skillSourceList = skillSourceList;
    }

    public List<SkillSourceBean> getServantSourceList() {
        return servantSourceList;
    }

    public void setServantSourceList(List<SkillSourceBean> servantSourceList) {
        this.servantSourceList = servantSourceList;
    }
}

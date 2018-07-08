package com.fgo.utils.bean;

import java.util.List;

public class ServantAdvancedBean {


    /**
     * breakMaterialArr :
     * breakMaterialNumArr :
     * breakMaterialImgArr :
     * breakCostArr :
     * servantImgUrl : ["https://fatego.oss-cn-beijing.aliyuncs.com/fate/001-a1.jpg","https://fatego.oss-cn-beijing.aliyuncs.com/fate/001-b2.jpg","https://fatego.oss-cn-beijing.aliyuncs.com/fate/001-c3.jpg","https://fatego.oss-cn-beijing.aliyuncs.com/fate/001-d3.jpg"]
     * activityFollower : false
     */

    private String breakMaterialArr;
    private String breakMaterialNumArr;
    private String breakMaterialImgArr;
    private String breakCostArr;
    private boolean activityFollower;
    private List<String> servantImgUrl;


    public String getBreakMaterialArr() {
        return breakMaterialArr;
    }

    public void setBreakMaterialArr(String breakMaterialArr) {
        this.breakMaterialArr = breakMaterialArr;
    }

    public String getBreakMaterialNumArr() {
        return breakMaterialNumArr;
    }

    public void setBreakMaterialNumArr(String breakMaterialNumArr) {
        this.breakMaterialNumArr = breakMaterialNumArr;
    }

    public String getBreakMaterialImgArr() {
        return breakMaterialImgArr;
    }

    public void setBreakMaterialImgArr(String breakMaterialImgArr) {
        this.breakMaterialImgArr = breakMaterialImgArr;
    }

    public String getBreakCostArr() {
        return breakCostArr;
    }

    public void setBreakCostArr(String breakCostArr) {
        this.breakCostArr = breakCostArr;
    }

    public boolean isActivityFollower() {
        return activityFollower;
    }

    public void setActivityFollower(boolean activityFollower) {
        this.activityFollower = activityFollower;
    }

    public List<String> getServantImgUrl() {
        return servantImgUrl;
    }

    public void setServantImgUrl(List<String> servantImgUrl) {
        this.servantImgUrl = servantImgUrl;
    }

}

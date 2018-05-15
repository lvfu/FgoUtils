package com.fgo.utils.bean;

/**
 * Created by Administrator on 2017/6/15.
 */

public class ProductCategroyBean {

    private int CategoryId;//分类id
    private String CategoryName;//分类名称

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int CategoryId) {
        this.CategoryId = CategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    @Override
    public String toString() {
        return "ProductCategroyData{" +
                "CategoryId=" + CategoryId +
                ", CategoryName='" + CategoryName + '\'' +
                '}';
    }


}

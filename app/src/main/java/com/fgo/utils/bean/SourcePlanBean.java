package com.fgo.utils.bean;

import java.io.Serializable;

/**
 * Created by lvfu on 2018/4/16.
 */

public class SourcePlanBean implements Serializable {

    private int id,
            rarity,
            type,
            have,
            need;


    private String name_en,
            name,
            img,
            dropinfo;

    private boolean isExpand;

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHave() {
        return have;
    }

    public void setHave(int have) {
        this.have = have;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDropinfo() {
        return dropinfo;
    }

    public void setDropinfo(String dropinfo) {
        this.dropinfo = dropinfo;
    }
}

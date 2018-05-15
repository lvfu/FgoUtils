package com.fgo.utils.bean;

import java.util.List;

/**
 * Created by lvfu on 2018/4/13.
 */

public class SkillSourceBean {

    private List<String> skill_material;
    private List<String> skill_material_num;
    private List<String> skill_material_img;
    private String skill_cost;
    private String skill_lv;

    public String getSkill_lv() {
        return skill_lv;
    }

    public void setSkill_lv(String skill_lv) {
        this.skill_lv = skill_lv;
    }

    public List<String> getSkill_material() {
        return skill_material;
    }

    public void setSkill_material(List<String> skill_material) {
        this.skill_material = skill_material;
    }

    public List<String> getSkill_material_num() {
        return skill_material_num;
    }

    public void setSkill_material_num(List<String> skill_material_num) {
        this.skill_material_num = skill_material_num;
    }

    public List<String> getSkill_material_img() {
        return skill_material_img;
    }

    public void setSkill_material_img(List<String> skill_material_img) {
        this.skill_material_img = skill_material_img;
    }

    public String getSkill_cost() {
        return skill_cost;
    }

    public void setSkill_cost(String skill_cost) {
        this.skill_cost = skill_cost;
    }
}

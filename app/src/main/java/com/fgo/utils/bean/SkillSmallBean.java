package com.fgo.utils.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lvfu on 2018/4/12.
 */

public class SkillSmallBean implements Serializable {

    private String skillName;
    private String skillLevel;
    private String skillCoolDown;
    private List<String> skillEffect;
    private String skillImage;
    private List<String> skillEffectValue;


    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getSkillCoolDown() {
        return skillCoolDown;
    }

    public void setSkillCoolDown(String skillCoolDown) {
        this.skillCoolDown = skillCoolDown;
    }



    public String getSkillImage() {
        return skillImage;
    }

    public void setSkillImage(String skillImage) {
        this.skillImage = skillImage;
    }

    public List<String> getSkillEffect() {
        return skillEffect;
    }

    public void setSkillEffect(List<String> skillEffect) {
        this.skillEffect = skillEffect;
    }

    public List<String> getSkillEffectValue() {
        return skillEffectValue;
    }

    public void setSkillEffectValue(List<String> skillEffectValue) {
        this.skillEffectValue = skillEffectValue;
    }
}

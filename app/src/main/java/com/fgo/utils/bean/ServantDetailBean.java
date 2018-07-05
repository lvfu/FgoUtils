package com.fgo.utils.bean;

public class ServantDetailBean {


    /**
     * data : {"id":0,"buster_num":2,"arts_num":2,"quick_num":1,"treasure_color":2,"attribute":1,"arts_hit":2,"buster_hit":1,"quick_hit":2,"ex_hit":3,"name":"玛修・基列莱特","nickname":"女主,盾娘,学妹,C奶,盾子","treasure":"如今仍是遥远的理想之城","class_type":"Shielder","skill_one_name":"荣光不败的雪花之壁","skill_one_img":"Def_Up.png","skill_two_name":"时为朦胧的白垩之壁","skill_two_img":"Duang.png","skill_three_name":"决意奋起之盾","skill_three_img":"Fuck_me.png"}
     * code : success
     * msg : 数据获取成功
     */

    private DataBean data;
    private String code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * id : 0
         * buster_num : 2
         * arts_num : 2
         * quick_num : 1
         * treasure_color : 2
         * attribute : 1
         * arts_hit : 2
         * buster_hit : 1
         * quick_hit : 2
         * ex_hit : 3
         * name : 玛修・基列莱特
         * nickname : 女主,盾娘,学妹,C奶,盾子
         * treasure : 如今仍是遥远的理想之城
         * class_type : Shielder
         * skill_one_name : 荣光不败的雪花之壁
         * skill_one_img : Def_Up.png
         * skill_two_name : 时为朦胧的白垩之壁
         * skill_two_img : Duang.png
         * skill_three_name : 决意奋起之盾
         * skill_three_img : Fuck_me.png
         */

        private int id;
        private int buster_num;
        private int arts_num;
        private int quick_num;
        private int treasure_color;
        private int attribute;
        private int arts_hit;
        private int buster_hit;
        private int quick_hit;
        private int ex_hit;
        private String name;
        private String nickname;
        private String treasure;
        private String class_type;
        private String skill_one_name;
        private String skill_one_img;
        private String skill_two_name;
        private String skill_two_img;
        private String skill_three_name;
        private String skill_three_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBuster_num() {
            return buster_num;
        }

        public void setBuster_num(int buster_num) {
            this.buster_num = buster_num;
        }

        public int getArts_num() {
            return arts_num;
        }

        public void setArts_num(int arts_num) {
            this.arts_num = arts_num;
        }

        public int getQuick_num() {
            return quick_num;
        }

        public void setQuick_num(int quick_num) {
            this.quick_num = quick_num;
        }

        public int getTreasure_color() {
            return treasure_color;
        }

        public void setTreasure_color(int treasure_color) {
            this.treasure_color = treasure_color;
        }

        public int getAttribute() {
            return attribute;
        }

        public void setAttribute(int attribute) {
            this.attribute = attribute;
        }

        public int getArts_hit() {
            return arts_hit;
        }

        public void setArts_hit(int arts_hit) {
            this.arts_hit = arts_hit;
        }

        public int getBuster_hit() {
            return buster_hit;
        }

        public void setBuster_hit(int buster_hit) {
            this.buster_hit = buster_hit;
        }

        public int getQuick_hit() {
            return quick_hit;
        }

        public void setQuick_hit(int quick_hit) {
            this.quick_hit = quick_hit;
        }

        public int getEx_hit() {
            return ex_hit;
        }

        public void setEx_hit(int ex_hit) {
            this.ex_hit = ex_hit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTreasure() {
            return treasure;
        }

        public void setTreasure(String treasure) {
            this.treasure = treasure;
        }

        public String getClass_type() {
            return class_type;
        }

        public void setClass_type(String class_type) {
            this.class_type = class_type;
        }

        public String getSkill_one_name() {
            return skill_one_name;
        }

        public void setSkill_one_name(String skill_one_name) {
            this.skill_one_name = skill_one_name;
        }

        public String getSkill_one_img() {
            return skill_one_img;
        }

        public void setSkill_one_img(String skill_one_img) {
            this.skill_one_img = skill_one_img;
        }

        public String getSkill_two_name() {
            return skill_two_name;
        }

        public void setSkill_two_name(String skill_two_name) {
            this.skill_two_name = skill_two_name;
        }

        public String getSkill_two_img() {
            return skill_two_img;
        }

        public void setSkill_two_img(String skill_two_img) {
            this.skill_two_img = skill_two_img;
        }

        public String getSkill_three_name() {
            return skill_three_name;
        }

        public void setSkill_three_name(String skill_three_name) {
            this.skill_three_name = skill_three_name;
        }

        public String getSkill_three_img() {
            return skill_three_img;
        }

        public void setSkill_three_img(String skill_three_img) {
            this.skill_three_img = skill_three_img;
        }
    }
}

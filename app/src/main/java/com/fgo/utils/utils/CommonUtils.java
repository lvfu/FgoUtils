package com.fgo.utils.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.bean.SkillSourceBean;
import com.fgo.utils.bean.SourcePlanBean;
import com.spreada.utils.chinese.ZHConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by lvfu on 2018/4/13.
 */

public class CommonUtils {

    //通过图片名获取id
    public static int getResourceId(String name, Context context) {
        int id = context.getResources().getIdentifier(name, "mipmap", context.getPackageName());
        return id;
    }

    //补0
    public static String getId(int id) {

        String num = "";
        if (id > 0 && id < 10) {
            num = new StringBuilder().append("00").append(id).toString();
        } else if (id >= 10 && id < 100) {
            num = new StringBuilder().append("0").append(id).toString();
        } else {
            num = new StringBuilder().append(id).toString();
        }
        return num;
    }

    //检测网络是否是wifi
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    //繁体转简体
    public static String tc2sc(@NonNull String str) {
        ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        String simplifiedStr = converter.convert(str);
        return simplifiedStr;
    }


    public static Map<String, String> setValue(String[] args) {
        Map<String, String> m = new HashMap<String, String>();
//        String[] str = {"hello:1", "java:1", "hello:1"};
        for (String s : args) {
            int i = s.indexOf("|");
            String key = s.substring(0, i).trim();
            String value = s.substring(i + 1).trim();
            if (m.containsKey(key)) {
                int val = (Integer.parseInt(m.get(key)) + Integer.parseInt(value));
                m.put(key, val + "");
            } else {
                m.put(key, value);
            }
        }
        for (String key : m.keySet()) {
            System.out.println("key= " + key + " and value= " + m.get(key));
        }

        return m;

    }

    //map to string
    public static String transMapToString(Map map) {
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (java.util.Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("'").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }

    //string to map
    public static Map transStringToMap(String mapString) {
        Map map = new HashMap();
        java.util.StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }

    //统计资源数据
    public static List<SkillSourceBean> getSourceList(List<SkillSourceBean> list, String material_arr, String material_num_arr, String material_img_arr, String cost_arr) {
        boolean isCanNewList = true;
        List<String> splitMaterialList = new ArrayList<>();
        List<String> splitMaterialNumList = new ArrayList<>();
        List<String> splitMaterialImgList = new ArrayList<>();

        String[] split_material = material_arr.split("\\|");
        String[] split_material_num = material_num_arr.split("\\|");
        String[] split_material_img = material_img_arr.split("\\|");
        String[] split_cost = cost_arr.split("\\|");

        for (int i = 0; i < split_material.length; i++) {
            SkillSourceBean skillSourceBean = new SkillSourceBean();

            String[] splitChildMaterial = split_material[i].split("\\,");
            String[] splitChildMaterialNum = split_material_num[i].split("\\,");
            String[] splitChildMaterialImg = split_material_img[i].split("\\,");
            for (int j = 0; j < splitChildMaterial.length; j++) {
                if (splitChildMaterial.length > 1) {

                    if (isCanNewList) {
                        splitMaterialList = new ArrayList<>();
                        splitMaterialNumList = new ArrayList<>();
                        splitMaterialImgList = new ArrayList<>();
                        isCanNewList = false;
                    }
                    splitMaterialList.add(splitChildMaterial[j]);
                    splitMaterialNumList.add(splitChildMaterialNum[j]);
                    splitMaterialImgList.add(splitChildMaterialImg[j]);


                } else {
                    splitMaterialList = new ArrayList<>();
                    splitMaterialNumList = new ArrayList<>();
                    splitMaterialImgList = new ArrayList<>();

                    splitMaterialList.add(splitChildMaterial[j]);
                    splitMaterialNumList.add(splitChildMaterialNum[j]);
                    splitMaterialImgList.add(splitChildMaterialImg[j]);
                }

            }

            isCanNewList = true;
            skillSourceBean.setSkill_material(splitMaterialList);
            skillSourceBean.setSkill_material_num(splitMaterialNumList);
            skillSourceBean.setSkill_material_img(splitMaterialImgList);
            skillSourceBean.setSkill_cost(split_cost[i]);
            skillSourceBean.setSkill_lv(i + 1 + "");

            list.add(skillSourceBean);

        }

        return list;


    }

    //数据库设置英灵model
    public static ArrayList<ServantItem> getServantList(Cursor cur) {
        if (cur != null) {
            int NUM_SERVANT = cur.getCount();
            ArrayList<ServantItem> cache = new ArrayList<>();
            while (cur.moveToNext()) {
                int id = cur.getInt(0);
                String name = cur.getString(1);
                String nickname = cur.getString(2);
                String class_type = cur.getString(3);
                int star = cur.getInt(4);
                int arts_hit = cur.getInt(5);
                int buster_hit = cur.getInt(6);
                int quick_hit = cur.getInt(7);
                int ex_hit = cur.getInt(8);
                double quick_na = cur.getDouble(9);
                double arts_na = cur.getDouble(10);
                double buster_na = cur.getDouble(11);
                double ex_na = cur.getDouble(12);
                double trump_na = cur.getDouble(13);
                double nd = cur.getDouble(14);
                double arts_buff = cur.getDouble(15);
                double buster_buff = cur.getDouble(16);
                double quick_buff = cur.getDouble(17);
                double atk_buff = cur.getDouble(18);
                double special_buff = cur.getDouble(19);
                double critical_buff = cur.getDouble(20);
                int solid_buff = cur.getInt(21);
                int buster_num = cur.getInt(22);
                int arts_num = cur.getInt(23);
                int quick_num = cur.getInt(24);
                double star_occur = cur.getDouble(25);
                double trump_lv1 = cur.getDouble(26);
                double trump_lv2 = cur.getDouble(27);
                double trump_lv3 = cur.getDouble(28);
                double trump_lv4 = cur.getDouble(29);
                double trump_lv5 = cur.getDouble(30);
                String trump_color = cur.getString(31);
                int default_hp = cur.getInt(32);
                int default_atk = cur.getInt(33);
                double star_occur_extra = cur.getDouble(34);
                double trump_lv1_before = cur.getDouble(35);
                double trump_lv2_before = cur.getDouble(36);
                double trump_lv3_before = cur.getDouble(37);
                double trump_lv4_before = cur.getDouble(38);
                double trump_lv5_before = cur.getDouble(39);
                int trump_upgraded = cur.getInt(40);
                int attribute = cur.getInt(41);
                int np_hit = cur.getInt(42);
                String treasure = cur.getString(43);
                String treasure_coloe = cur.getString(44);

                ServantItem servantItem = new ServantItem();
                servantItem.setId(id);
                servantItem.setName(name);
                servantItem.setNickname(nickname);
                servantItem.setClass_type(class_type);
                servantItem.setStar(star);
                servantItem.setArts_hit(arts_hit);
                servantItem.setBuster_hit(buster_hit);
                servantItem.setQuick_hit(quick_hit);
                servantItem.setEx_hit(ex_hit);
                servantItem.setQuick_na(quick_na);
                servantItem.setArts_na(arts_na);
                servantItem.setBuster_na(buster_na);
                servantItem.setEx_na(ex_na);
                servantItem.setTrump_na(trump_na);
                servantItem.setNd(nd);
                servantItem.setArts_buff(arts_buff);
                servantItem.setBuster_buff(buster_buff);
                servantItem.setQuick_buff(quick_buff);
                servantItem.setAtk_buff(atk_buff);
                servantItem.setSpecial_buff(special_buff);
                servantItem.setCritical_buff(critical_buff);
                servantItem.setSolid_buff(solid_buff);
                servantItem.setBuster_num(buster_num);
                servantItem.setArts_num(arts_num);
                servantItem.setQuick_num(quick_num);
                servantItem.setStar_occur(star_occur);
                servantItem.setTrump_lv1(trump_lv1);
                servantItem.setTrump_lv2(trump_lv2);
                servantItem.setTrump_lv3(trump_lv3);
                servantItem.setTrump_lv4(trump_lv4);
                servantItem.setTrump_lv5(trump_lv5);
                servantItem.setTrump_color(trump_color);
                servantItem.setDefault_hp(default_hp);
                servantItem.setDefault_atk(default_atk);
                servantItem.setStar_occur_extra(star_occur_extra);
                servantItem.setTrump_lv1_before(trump_lv1_before);
                servantItem.setTrump_lv2_before(trump_lv2_before);
                servantItem.setTrump_lv3_before(trump_lv3_before);
                servantItem.setTrump_lv4_before(trump_lv4_before);
                servantItem.setTrump_lv5_before(trump_lv5_before);
                servantItem.setTrump_upgraded(trump_upgraded);
                servantItem.setAttribute(attribute);
                servantItem.setNp_hit(np_hit);
                servantItem.setTreasure(treasure);
                servantItem.setTreasure_coloe(treasure_coloe);
                cache.add(servantItem);
            }
            return cache;
        } else {
            return null;
        }
    }

    //数据库设置技能model

    public static List<ServantSkill> getServantSkillData(Cursor cur1) {
        if (cur1 != null) {
            int NUM_SERVANT = cur1.getCount();
            ArrayList<ServantSkill> cache = new ArrayList<>();
            while (cur1.moveToNext()) {
                int id = cur1.getInt(0);
                String head_img_id = cur1.getString(1);
                String name_en = cur1.getString(2);
                String name_jp = cur1.getString(3);

                String break_material_arr = cur1.getString(4);
                String break_material_num_arr = cur1.getString(5);
                String break_material_img_arr = cur1.getString(6);
                String break_material_border_arr = cur1.getString(7);
                String break_cost_arr = cur1.getString(8);

                String skill_material_arr = cur1.getString(9);
                String skill_material_num_arr = cur1.getString(10);
                String skill_material_img_arr = cur1.getString(11);
                String skill_material_border_arr = cur1.getString(12);
                String skill_cost_arr = cur1.getString(13);
                String skill_one_name = cur1.getString(14);
                String skill_one_level = cur1.getString(15);
                String skill_one_cool_down = cur1.getString(16);
                String skill_one_effect = cur1.getString(17);
                String skill_one_effect_value = cur1.getString(18);
                String skill_one_img = cur1.getString(19);
                String skill_two_name = cur1.getString(20);
                String skill_two_level = cur1.getString(21);
                String skill_two_cool_down = cur1.getString(22);
                String skill_two_effect = cur1.getString(23);
                String skill_two_effect_value = cur1.getString(24);
                String skill_two_img = cur1.getString(25);
                String skill_three_name = cur1.getString(26);
                String skill_three_level = cur1.getString(27);
                String skill_three_cool_down = cur1.getString(28);
                String skill_three_effect = cur1.getString(29);
                String skill_three_effect_value = cur1.getString(30);
                String skill_three_img = cur1.getString(31);

                ServantSkill servantSkill = new ServantSkill();

                servantSkill.setId(id);
                servantSkill.setHead_img_id(head_img_id);
                servantSkill.setName_en(name_en);
                servantSkill.setName_jp(name_jp);

                servantSkill.setBreak_material_arr(break_material_arr);
                servantSkill.setBreak_material_num_arr(break_material_num_arr);
                servantSkill.setBreak_material_img_arr(break_material_img_arr);
                servantSkill.setBreak_material_border_arr(break_material_border_arr);
                servantSkill.setBreak_cost_arr(break_cost_arr);

                servantSkill.setSkill_material_arr(skill_material_arr);
                servantSkill.setSkill_material_num_arr(skill_material_num_arr);
                servantSkill.setSkill_material_img_arr(skill_material_img_arr);
                servantSkill.setSkill_material_border_arr(skill_material_border_arr);
                servantSkill.setSkill_cost_arr(skill_cost_arr);

                servantSkill.setSkill_one_name(skill_one_name);
                servantSkill.setSkill_one_level(skill_one_level);
                servantSkill.setSkill_one_cool_down(skill_one_cool_down);
                servantSkill.setSkill_one_effect(skill_one_effect);
                servantSkill.setSkill_one_effect_value(skill_one_effect_value);
                servantSkill.setSkill_one_img(skill_one_img);

                servantSkill.setSkill_two_name(skill_two_name);
                servantSkill.setSkill_two_level(skill_two_level);
                servantSkill.setSkill_two_cool_down(skill_two_cool_down);
                servantSkill.setSkill_two_effect(skill_two_effect);
                servantSkill.setSkill_two_effect_value(skill_two_effect_value);
                servantSkill.setSkill_two_img(skill_two_img);

                servantSkill.setSkill_three_name(skill_three_name);
                servantSkill.setSkill_three_level(skill_three_level);
                servantSkill.setSkill_three_cool_down(skill_three_cool_down);
                servantSkill.setSkill_three_effect(skill_three_effect);
                servantSkill.setSkill_three_effect_value(skill_three_effect_value);
                servantSkill.setSkill_three_img(skill_three_img);

                cache.add(servantSkill);
            }
            return cache;
        } else {
            return null;
        }
    }


    //数据库 技能
    public static ArrayList<SourcePlanBean> getSkillList (Cursor cur1){
        if (cur1 != null) {
            int NUM_SERVANT = cur1.getCount();
            ArrayList<SourcePlanBean> cache = new ArrayList<>();
            while (cur1.moveToNext()) {
                int id = cur1.getInt(0);
                String name_en = cur1.getString(1);
                String name = cur1.getString(2);
                String img = cur1.getString(3);
                int type = cur1.getInt(4);
                int rarity = cur1.getInt(5);
                String dropinfo = cur1.getString(6);
                int need = cur1.getInt(7);
                int have = cur1.getInt(8);

                SourcePlanBean servantPlan = new SourcePlanBean();

                servantPlan.setId(id);
                servantPlan.setName_en(name_en);
                servantPlan.setName(name);
                servantPlan.setImg(img);
                servantPlan.setType(type);
                servantPlan.setRarity(rarity);
                servantPlan.setDropinfo(dropinfo);
                servantPlan.setHave(have);
                servantPlan.setNeed(need);
                cache.add(servantPlan);
            }
            return cache;
        } else {
            return null;
        }
    }

}

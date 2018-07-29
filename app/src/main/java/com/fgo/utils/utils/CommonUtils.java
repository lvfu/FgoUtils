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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    public static String getTime() {
        long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        String t1 = format.format(d1);

        return t1;

    }


}

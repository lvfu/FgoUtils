package com.fgo.utils.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * 判断首页跳转工具类
 * Created by jack on 2016/4/1.
 */
public class PrefUtil {
    private static final String PREF_NAME="config";
    public static boolean getBoolean(Context context, String key, boolean defaultValue){
        SharedPreferences sp=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean value){
        SharedPreferences sp=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }



}

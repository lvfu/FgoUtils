package com.king.frame.mvp.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * acticity的退出工具类
 *
 * @author 王文涛
 *         Created by jack on 2016/4/7.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    //添加一个活动
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //清除一个活动
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //销毁所有活动
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.show(fragment);
        transaction.commit();
    }

    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.replace(frameId, fragment);
        }
        transaction.commit();
    }

}

package com.fgo.utils.base;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.fgo.utils.R;


/**
 *
 * 状态栏公共方法
 * Created by jack on 2016/6/8.
 */
public class SytemTar {

    private  static  SytemTar sytemTar;
    public static  SytemTar getInstance(){

        if(sytemTar==null){
            sytemTar=new SytemTar();

        }
        return sytemTar;
    }

    /**
     * Apply KitKat specific translucency.
     */
    public void applyKitKatTranslucency(Activity activity) {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,activity);
            SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);

            mTintManager.setStatusBarTintResource(R.color.topTitleColor);//通知栏所需颜色
        }

    }


    /**
     * Apply KitKat specific translucency.
     */
    public void applyKitKatTranslucency(Activity activity, int res) {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,activity);
            SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);

            mTintManager.setStatusBarTintResource(res);//通知栏所需颜色
        }

    }


    /**
     *
     * 透明色
     * Apply KitKat specific translucency.
     */
    public void applyKitKatTranslucencyTran(Activity activity) {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,activity);
            SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);

            mTintManager.setStatusBarTintResource(R.color.top_bg_colort);//通知栏所需颜色
        }

    }
    /**
     *
     * 透明色
     * Apply KitKat specific translucency.
     */
    public void applyKitKatTranslucencyTranGray(Activity activity) {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,activity);
            SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);

            mTintManager.setStatusBarTintResource(R.color.title_stuats);//通知栏所需颜色
        }

    }


    private void setTranslucentStatus(boolean on,Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
                winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}

package com.fgo.utils;

import android.app.Application;
import android.widget.Toast;


import com.fgo.utils.utils.SharedPreferencesUtils;
import com.king.frame.api.ApiManager;
import com.king.frame.mvp.activity.ActivityCollector;
import com.fgo.utils.utils.UnCentHandler;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

public class App extends Application {

    private static final String TAG = "Jenly";

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        //程序默认UnCentHandler为异常处理器
        UnCentHandler catchException = new UnCentHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchException);
    }



    private void init(){
        ApiManager.init(Constants.BASE_URL);

        if(BuildConfig.DEBUG){
            Logger.init(TAG);
        }else{
            Logger.init(TAG).logLevel( LogLevel.NONE );
        }
    }

    /**
     * 关闭所有的activity
     */
    public void finishActivity() {
        ActivityCollector.finishAll();
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }




}

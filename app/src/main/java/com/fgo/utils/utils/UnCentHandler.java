package com.fgo.utils.utils;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.fgo.utils.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Created by jack on 2016/5/4.
 *
 *
 */
public class UnCentHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public static final String TAG = "CatchException";
    private DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");  //用于格式化日期,作为日志文件名的一部分
    private App baseApp;
    public UnCentHandler(App baseApp) {
        //获取系统自带的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.baseApp = baseApp;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {

            mDefaultHandler.uncaughtException(thread, ex);
        } else {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

//            Intent intent = new Intent(baseApp.getApplicationContext(), NavigationActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent restartIntent = PendingIntent.getActivity(
//                    baseApp.getApplicationContext(), 0, intent, 0);
//            //退出程序
//            AlarmManager mgr = (AlarmManager) baseApp.getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
//                    restartIntent); // 1秒钟后重启应用

            baseApp.finishActivity(); //自己处理

        }
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        ex.printStackTrace();
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(baseApp.getApplicationContext(), "似乎出了点问题，稍后再尝试",
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //保存到sd卡里
        saveCrashInfo2File(ex);

        return true;
    }

    /**
     * 保存错误信息到文件中  /storage/emulated/0/Android/data/com.hsyg.heshangyungou/files/crash_log
     *
     * @param ex
     */
    private void saveCrashInfo2File(Throwable ex) {

        StringBuilder sb = new StringBuilder();
        sb.append(getHandSetInfo());             //添加信息
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        try {
            String fileName = "/hsyg_crash.log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  //判断是否挂载
                File path = baseApp.getExternalFilesDir("crash_log");    //创建应用私有文件(app卸载 文件也删除) /storage/emulated/0/Android/data/com.hsyg.heshangyungou/files/crash_log
                if (path!=null&&!path.exists()) {
                    path.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(path + fileName,true);
                fos.write(sb.toString().getBytes());
                fos.close();
                SharedPreferencesUtils.setParam(baseApp, "isCrash", true);  //新崩溃日志产生  加个标识

            }

        } catch (Exception e) {
//            LogUtil.e(TAG, "an error occured while writing file...", e);

        }

    }

    /**
     * 拿到手机型号 版本  时间  APP版本
     * @return
     */
    private String getHandSetInfo(){

        String time = formatter.format(new Date());  //时间
        String handSetInfo= "手机型号:" + android.os.Build.MODEL + " 系统版本:" + android.os.Build.VERSION.RELEASE+"\n"; //型号 版本

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n \n crash time:"+time);


        PackageManager packageManager = baseApp.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(baseApp.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo!=null){
                stringBuilder.append("app版本:"+packageInfo.versionName+" ");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        stringBuilder.append(handSetInfo);

        return stringBuilder.toString();
    }

    }


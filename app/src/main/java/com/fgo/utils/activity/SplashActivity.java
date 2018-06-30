package com.fgo.utils.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.fgo.utils.MainActivity;
import com.fgo.utils.R;
import com.fgo.utils.utils.PermissionUtils;
import com.fgo.utils.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    private ImageView mSplashIv;
    HashMap<String, String> map = new HashMap<>();
    String url = "https://fatego.oss-cn-beijing.aliyuncs.com/fatesplash/splash.jpg";
    private String[] permissionArray = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String key;
    private String value;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_splash);

        handler = new Handler(Looper.getMainLooper());
        initView();
        initPermission();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            Glide.with(getApplicationContext()).load(url).error(R.mipmap.ic_launcher).into(mSplashIv);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        } else {

            map.put(permissionArray[0], "存储空间,");
            map.put(permissionArray[1], "读写权限,");

            if (PermissionUtils.checkPermissionArray(this, permissionArray)) {
                Glide.with(getApplicationContext()).load(url).error(R.mipmap.ic_launcher).into(mSplashIv);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            } else {
                PermissionUtils.checkPermissionArray(SplashActivity.this, permissionArray, 2);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.PERMISSION_REQUEST_CODE:
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    // Permission Granted
                    // do you action
                    Glide.with(getApplicationContext()).load(url).error(R.mipmap.ic_launcher).into(mSplashIv);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);


                } else {

                    //用户不同意，向用户展示该权限作用
                    if (PermissionUtils.shouldShowRequestPermissionRationaleWrapper(this, permissions)) {
                        String message = "请赋予" + getNotPermissionArray(PermissionUtils.verifyPermissionsData(permissionArray, this)) + "功能的权限，不开启将无法正常工作！";
                        ShowRequestPermissionDialog(message);
                    } else {
                        String content = "请赋予" + getNotPermissionArray() + "功能的权限，不开启将无法正常工作！";
                        ShowRequestPermissionDialog(content);
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private String getNotPermissionArray(List<String> permissionArray) {


        StringBuffer str = new StringBuffer();
        for (int i = 0; i < permissionArray.size(); i++) {
            for (Map.Entry<String, String> entry : map.entrySet()) {

                if (entry.getKey().equals(permissionArray.get(i))) {

                    str.append(entry.getValue());
                }
            }

        }
        return str.toString();
    }

    private String getNotPermissionArray() {

        StringBuffer str = new StringBuffer();

        for (Map.Entry<String, String> entry : map.entrySet()) {


            str.append(entry.getValue());


        }
        return str.toString();
    }

    private void initView() {
        mSplashIv = findViewById(R.id.splash);

    }


    private void ShowRequestPermissionDialog(String message) {

        new MaterialDialog.Builder(this)
                .title(R.string.person_init_db_title)
                .titleColor(getResources().getColor(R.color.colorAccent))
                .content(message)
                .positiveText(R.string.person_init_db_ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startAppSettings();
                    }
                })
                .show();

        new MaterialDialog.Builder(this).keyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialogInterface.dismiss();
                    finish();
                }
                return false;
            }
        });

    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
        finish();
    }
}

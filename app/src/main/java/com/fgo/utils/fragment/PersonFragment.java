package com.fgo.utils.fragment;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.ocr.sdk.utils.LogUtil;
import com.fgo.utils.utils.SharedPreferencesUtils;
import com.king.frame.mvp.base.QuickFragment;
import com.fgo.utils.MainActivity;
import com.fgo.utils.R;
import com.fgo.utils.db.DBManager;
import com.fgo.utils.mvp.presenter.PersonPresenter;
import com.fgo.utils.mvp.view.PersonView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.fgo.utils.db.DBManager.DB_NAME;
import static com.fgo.utils.utils.UnCentHandler.TAG;

/**
 * Created by lvfu on 2018/3/26.
 */

public class PersonFragment extends QuickFragment<PersonView, PersonPresenter> implements View.OnClickListener {
    private static final int REQUEST_CODE_GENERAL_WEBIMAGE = 110;
    private static final int RESULT_OK = -1;
    private TextView mInitDb, upDateDao;
    public static final String PACKAGE_NAME = "com.fgo.utils";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;  //在手机里存放数据库的位置
    private final int BUFFER_SIZE = 400000;
    private boolean isUpdateDatabase = false;//是否是更新数据库
    private boolean isReceiverRegister;
    private String databaseExtraUrl = "https://fatego.oss-cn-beijing.aliyuncs.com/fatadb/servants.db";
    private Context context;
    private int maxImgCount = 1;
    private String path;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_person;
    }

    @Override
    public void initUI() {

        context = getContext();
        mInitDb = (TextView) getRootView().findViewById(R.id.init_greendao);
        upDateDao = getRootView().findViewById(R.id.update_greendao);
        TextView in = getRootView().findViewById(R.id.data_in);
        mInitDb.setOnClickListener(this);
        upDateDao.setOnClickListener(this);
        in.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }


    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.init_greendao:
                cleanDbFile();
                break;

            case R.id.update_greendao:
                UpdateDbFile();

            case R.id.data_in:
//                updateImageData();
                break;
        }
    }

    private void updateImageData() {
        if (!checkTokenStatus()) {
            return;
        }


        Matisse.from(PersonFragment.this)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(1) // 图片选择的最多数量
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.99f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_GENERAL_WEBIMAGE); // 设置作为标记的请求码
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GENERAL_WEBIMAGE && resultCode == RESULT_OK) {
            String token = (String) SharedPreferencesUtils.getParam(getContext(), "token", "");

            List<Uri> uris = Matisse.obtainResult(data);
            String realFilePath = getRealFilePath(getContext(), uris.get(0));

            try {
                RecognizeService.recReceipt(realFilePath, token);

            } catch (Exception e) {
                e.toString();
            }

            RecognizeService.onPostResult(new RecognizeService.OnListener() {
                @Override
                public void onSuccessListener(List<String> list) {

                    LogUtil.e("abc", list.get(0).toString() + "&" + list.get(1).toString() + "&" + list.get(2).toString() + "&" + list.get(3).toString() + "&" + list.get(4).toString() + "&" + list.get(5).toString());
                }
            });
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private boolean checkTokenStatus() {
        String token = (String) SharedPreferencesUtils.getParam(getContext(), "token", "");
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(getContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void UpdateDbFile() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.person_init_db_title)
                .titleColor(getResources().getColor(R.color.colorAccent))
                .content(R.string.person_update_db_content)
                .positiveText(R.string.person_init_db_ok)
                .negativeText(R.string.person_init_db_cancle)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        File configFile = new File(Environment.getExternalStoragePublicDirectory("Download") + "/servants.db");
                        if (configFile.exists()) {
                            configFile.delete();
                            Log.d("", "数据库文件存在，删除配置文件");
                        }
                        //注册广播接收器
                        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                        getContext().registerReceiver(receiver, filter);
                        isReceiverRegister = true;
                        //创建下载任务,downloadUrl就是下载链接
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(databaseExtraUrl));
                        //指定下载路径和下载文件名
                        request.setDestinationInExternalPublicDir("Download", "/servants.db");
                        //获取下载管理器
                        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        //将下载任务加入下载队列，否则不会进行下载
                        downloadManager.enqueue(request);

                    }
                })
                .show();


    }

    //用来监听下载事件的
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Log.d(TAG, "外置数据库下载完成");
                isUpdateDatabase = true;
                loadDatabaseExtra();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null && isReceiverRegister) {
            getContext().unregisterReceiver(receiver);
        }
    }

    public void loadDatabaseExtra() {
        File dbFile = new File(DBManager.DB_PATH + "/" + DBManager.DB_NAME);
        dbFile.delete();
        String dbfile = DB_PATH + "/" + DB_NAME;
        try {
            updateDb(dbfile);
            //这里有两个重启
            restartApp2();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cleanDbFile() {
        final String dbfile = DB_PATH + "/" + DB_NAME;
        try {
            if (!(new File(dbfile).exists())) {//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                //不存在，导入
                initDb(dbfile);
            } else {
                final boolean delete = delete(dbfile);

                new MaterialDialog.Builder(getContext())
                        .title(R.string.person_init_db_title)
                        .titleColor(getResources().getColor(R.color.colorAccent))
                        .content(R.string.person_init_db_content)
                        .positiveText(R.string.person_init_db_ok)
                        .negativeText(R.string.person_init_db_cancle)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (delete) {
                                    try {
                                        initDb(dbfile);
                                        //这里有两个重启
                                        restartApp2();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    Toast.makeText(getContext(), "删除文件失败:", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .show();
            }
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
    }

    public boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            Toast.makeText(getContext(), "删除文件失败:" + fileName + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                Toast.makeText(getContext(), "删除文件夹失败:" + fileName + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }


    private void initDb(String dbfile) throws IOException {
        InputStream is = getResources().openRawResource(
                R.raw.servants); //欲导入的数据库
        FileOutputStream fos = new FileOutputStream(dbfile);
        byte[] buffer = new byte[BUFFER_SIZE];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
        }
        fos.close();
        is.close();
    }

    private void updateDb(String dbfile) throws IOException {
        String extra = Environment.getExternalStoragePublicDirectory("Download") + "/servants.db";
        InputStream is = new FileInputStream(extra); //欲导入的数据库
        FileOutputStream fos = new FileOutputStream(dbfile);
        byte[] buffer = new byte[BUFFER_SIZE];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
        }
        fos.close();
        is.close();
    }

    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */

    public void restartApp() {
        //启动页
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 重新启动App -> 不杀进程,缓存的东西不清除,启动快
     */
    public void restartApp2() {
        final Intent intent = getContext().getPackageManager()
                .getLaunchIntentForPackage(getContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(intent);
    }
}

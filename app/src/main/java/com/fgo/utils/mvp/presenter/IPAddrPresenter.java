package com.fgo.utils.mvp.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.R;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.db.DBManager;
import com.fgo.utils.mvp.view.IIPAddrView;
import com.fgo.utils.utils.CommonUtils;
import com.fgo.utils.utils.SharedPreferencesUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public class IPAddrPresenter extends BasePresenter<IIPAddrView> {

    private Integer curDbVersion;
    private Context mContext;
    private int preDbVersion;
    private DBManager dbManager;
    private boolean isExtra = false;//是否加载外置数据库
    private List<ServantItem> servantList = new ArrayList<>();
    private List<ServantSkill> servantSkillList = new ArrayList<>();

    public IPAddrPresenter(Context context) {
        mContext = context;
    }

    //获取sevent数据
    public void getSeventData() {

        preDbVersion = (int) SharedPreferencesUtils.getParam(mContext, "dbVersion", 1);

        //判断数据库有无更新
        if (checkDatabase(mContext)) {
            File dbFile = new File(DBManager.DB_PATH + "/" + DBManager.DB_NAME);
            dbFile.delete();
            getView().showCharacter(mContext.getResources().getString(R.string.database_upgrade_done));
        }

        dbManager = new DBManager(mContext);
        getAllServants();
    }

    public void getAllServants() {
        try {

            if (dbManager == null) {
                dbManager = new DBManager(mContext);
            }
            //打开数据库
            if (isExtra) {
                dbManager.getDatabaseExtra();
                isExtra = false;
            } else {
                dbManager.getDatabase();
            }
            //servants 表
            Cursor cur;
            cur = dbManager.database.rawQuery("SELECT * FROM servants", null);
            servantList = getServants(cur);

            if (cur != null) {
                cur.close();
            }


            //技能素材表
            Cursor cur1;
            cur1 = dbManager.database.rawQuery("SELECT * FROM ServantsSkill", null);
            servantSkillList = getServantSkillData(cur1);

            if (cur1 != null) {
                cur1.close();
            }
            dbManager.closeDatabase();

            getView().setServantList(servantList,servantSkillList);
        } catch (SecurityException e) {
            getView().showCharacter(mContext.getString(R.string.permission_error));
        } catch (Exception e) {
            e.printStackTrace();
            getView().showCharacter(mContext.getString(R.string.database_error));
        }
    }



    //检查数据库版本
    public boolean checkDatabase(Context ctx) {
        InputStream in = null;
        try {
            in = ctx.getResources()
                    .getAssets().open("database.xml");
        } catch (IOException e) {
            throw new SQLiteException("database.xml is not exist");
        }
        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(in, "UTF-8");
            int evtType = xpp.getEventType();
            String dbName = "";
            // 一直循环，直到文档结束
            while (evtType != XmlPullParser.END_DOCUMENT) {
                switch (evtType) {
                    case XmlPullParser.START_TAG:
                        String tag = xpp.getName();
                        if (tag.equals("dbname")) {
                            dbName = xpp.getAttributeValue(0);
                        } else if (tag.equals("version")) {
                            curDbVersion = Integer.valueOf(xpp.getAttributeValue(0));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                //获得下一个节点的信息
                evtType = xpp.next();
            }
            if (preDbVersion != curDbVersion) {
                SharedPreferencesUtils.setParam(ctx, "dbVersion", curDbVersion);
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return false;
    }

    private List<ServantSkill> getServantSkillData(Cursor cur1) {
        return CommonUtils.getServantSkillData(cur1);
    }

    public ArrayList<ServantItem> getServants(Cursor cur) {
       return CommonUtils.getServantList(cur);
    }


}

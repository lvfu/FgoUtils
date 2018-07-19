package com.fgo.utils.mvp.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.R;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;
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
    private boolean isExtra = false;//是否加载外置数据库
    private List<ServantItem> servantList = new ArrayList<>();
    private List<ServantSkill> servantSkillList = new ArrayList<>();

    public IPAddrPresenter(Context context) {
        mContext = context;
    }





}

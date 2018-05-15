package com.fgo.utils.utils;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.widget.TextView;

import com.fgo.utils.db.DBManager;

/**
 * Created by lvfu on 2018/4/18.
 */

public class DbUtils {

    //模糊查询(根据name)
    public static Cursor searchData(DBManager dbManager,Context context, String table, String keyword, String keywordTwo, boolean twoIsInt) {

        Cursor cur;
        if (!TextUtils.isEmpty(keywordTwo)) {
            if (twoIsInt) {
                cur = dbManager.database.rawQuery("SELECT * FROM " + table + "WHERE " +
                                "name LIKE ?",
                        new String[]{"%" + keyword + "%", keywordTwo + ""});
            } else {
                cur = dbManager.database.rawQuery("SELECT * FROM " + table + " WHERE " +
                                "name LIKE ?",
                        new String[]{"%" + keyword + "%", "%" + keywordTwo + "%"});
            }
        } else {
            cur = dbManager.database.rawQuery("SELECT * FROM " + table + " WHERE " +
                            "name LIKE ?",
                    new String[]{"%" + keyword + "%"});
        }
        return cur;
    }
}

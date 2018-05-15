package com.fgo.utils.mvp.presenter;

import android.content.Context;
import android.database.Cursor;

import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.Constants;
import com.fgo.utils.bean.SourcePlanBean;
import com.fgo.utils.db.DBManager;
import com.fgo.utils.mvp.view.SourcePlanView;
import com.fgo.utils.utils.CommonUtils;

import java.util.ArrayList;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public class SourcePlanPresenter extends BasePresenter<SourcePlanView> {

    private DBManager dbManager;
    private Context mContext;

    public SourcePlanPresenter(Context context) {
        this.mContext = context;
        dbManager = new DBManager(mContext);
    }

    public void getSourceData() {
        dbManager.getDatabase();

        Cursor cur;
        cur = dbManager.database.rawQuery("SELECT * FROM Materials", null);
        ArrayList<SourcePlanBean> sourcePlan = getSourcePlan(cur);

        if (cur != null) {
            cur.close();
        }
        dbManager.closeDatabase();

        getView().setSourcePlanData(sourcePlan);
    }


    public ArrayList<SourcePlanBean> getSourcePlan(Cursor cur1) {
        if (cur1 != null) {
            int NUM_SERVANT = cur1.getCount();
            ArrayList<SourcePlanBean> cache = new ArrayList<>();
            while (cur1.moveToNext()) {
                int id = cur1.getInt(0);
                String name_en = cur1.getString(1);
                String name = cur1.getString(2);
                String img = cur1.getString(3);
                int type = cur1.getInt(4);
                int rarity = cur1.getInt(5);
                String dropinfo = cur1.getString(6);
                int need = cur1.getInt(7);
                int have = cur1.getInt(8);

                SourcePlanBean servantPlan = new SourcePlanBean();

                servantPlan.setId(id);
                servantPlan.setName_en(name_en);
                servantPlan.setName(name);
                servantPlan.setImg(img);
                servantPlan.setType(type);
                servantPlan.setRarity(rarity);
                servantPlan.setDropinfo(dropinfo);
                servantPlan.setHave(have);
                servantPlan.setNeed(need);
                cache.add(servantPlan);
            }
            return cache;
        } else {
            return null;
        }
    }
}

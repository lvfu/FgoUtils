package com.fgo.utils.mvp.presenter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.widget.TextView;

import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.R;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.db.DBManager;
import com.fgo.utils.mvp.view.SearchView;
import com.fgo.utils.utils.CommonUtils;
import com.fgo.utils.utils.DbUtils;
import com.fgo.utils.utils.SharedPreferencesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchPresenter extends BasePresenter<SearchView> {
    private DBManager dbManager;
    private Context mContext;
    private String keyWord;
    private List<ServantItem> servantList = new ArrayList<>();
    private List<ServantSkill> servantSkillList = new ArrayList<>();

    public SearchPresenter(Context context) {
        this.mContext = context;
        dbManager = new DBManager(mContext);
    }

    public void searchServantsByKeyword(String keyWord) {
        try {
            dbManager.getDatabase();
            Cursor cur;
            keyWord = CommonUtils.tc2sc(keyWord);

            if (!TextUtils.isEmpty(keyWord)) {
                cur = dbManager.database.rawQuery("SELECT * FROM servants WHERE" +
                                " name LIKE ? OR nickname LIKE ? ORDER BY CAST(id AS SIGNED) ASC",
                        new String[]{"%" + keyWord + "%", "%" + keyWord + "%"});
            } else {
                cur = dbManager.database.rawQuery("SELECT * FROM servants ", null);

            }

            servantList = CommonUtils.getServantList(cur);

            Cursor cur1 = null;
            servantSkillList.clear();

            for (int i = 0; i < servantList.size(); i++) {

                int servantId = servantList.get(i).getId();

                cur1 = dbManager.database.rawQuery("SELECT * FROM ServantsSkill WHERE "+
                                " id = ? ORDER BY CAST(id AS SIGNED) ASC",

                        new String[]{servantId+""});

                List<ServantSkill> servantSkillData = CommonUtils.getServantSkillData(cur1);

                servantSkillList.add(servantSkillData.get(0));

            }
            //技能素材表

            if (cur != null) {
                cur.close();
            }
            if (cur1 != null) {
                cur1.close();
            }

            dbManager.closeDatabase();
            getView().setServantList(servantList, servantSkillList);

        } catch (Exception e) {
            e.printStackTrace();
//            getView().showCharacter(ctx.getString(R.string.database_error),R.mipmap.altria_alter_b);
        }
    }



    public void searchServantsByCondition(final String classType,final int star,final String orderType) {
        try{
            if (!TextUtils.isEmpty(classType)) {

                dbManager.getDatabase();
                Cursor cur;
                String[] order = orderType.split(",");
                boolean ifAllClass = false;//是否职阶不限
                boolean ifAllStar = false;//是否星数不限
                boolean ifMultiplier = false;//是否需要计算系数
                //判断职阶
                if (classType.equals("All")) {
                    ifAllClass = true;
                }
                //判断星数
                if (star == -1) {
                    ifAllStar = true;
                }
                //判断排序方式
                if (order.length == 3) {
                    ifMultiplier = true;
                }
                //判断使用哪种sql语句
                if (ifAllClass && ifAllStar && ifMultiplier) {
                    cur = dbManager.database.rawQuery("SELECT a.*,(a.default_atk * b.multiplier) new_atk " +
                                    " FROM servants a" +
                                    " LEFT JOIN class b ON a.class_type = b.class_type" +
                                    " ORDER BY CAST( new_atk AS SIGNED)" + order[1],
                            null);
                }else if (ifAllClass && ifMultiplier) {
                    cur = dbManager.database.rawQuery("SELECT a.*,(a.default_atk * b.multiplier) new_atk " +
                                    " FROM servants a" +
                                    " LEFT JOIN class b ON a.class_type = b.class_type" +
                                    " WHERE a.star = ?" +
                                    " ORDER BY CAST( new_atk AS SIGNED)" + order[1],
                            new String[]{star + ""});
                }else if (ifAllStar && ifMultiplier) {
                    cur = dbManager.database.rawQuery("SELECT a.*,(a.default_atk * b.multiplier) new_atk " +
                                    " FROM servants a" +
                                    " LEFT JOIN class b ON a.class_type = b.class_type" +
                                    " WHERE a.class_type = ?" +
                                    " ORDER BY CAST( new_atk AS SIGNED)" + order[1],
                            new String[]{classType});
                }else if (ifAllClass && ifAllStar) {
                    cur = dbManager.database.rawQuery("SELECT * FROM servants ORDER BY CAST(" + order[0] +" AS SIGNED)" + order[1],
                            null);
                }else if (ifMultiplier) {
                    cur = dbManager.database.rawQuery("SELECT a.*,(a.default_atk * b.multiplier) new_atk " +
                                    " FROM servants a" +
                                    " LEFT JOIN class b ON a.class_type = b.class_type" +
                                    " WHERE a.class_type = ? AND star = ?" +
                                    " ORDER BY CAST( new_atk AS SIGNED)" + order[1],
                            new String[]{classType,star + ""});
                }else if (ifAllStar) {
                    cur = dbManager.database.rawQuery("SELECT * FROM servants WHERE class_type = ? ORDER BY CAST(" + order[0] +" AS SIGNED)" + order[1],
                            new String[]{classType});
                }else if (ifAllClass) {
                    cur = dbManager.database.rawQuery("SELECT * FROM servants WHERE star = ? ORDER BY CAST(" + order[0] +" AS SIGNED)" + order[1],
                            new String[]{star + ""});
                }else{
                    cur = dbManager.database.rawQuery("SELECT * FROM servants WHERE class_type = ? AND star = ? ORDER BY CAST(" + order[0] + " AS SIGNED)" + order[1],
                            new String[]{classType, star + ""});
                }
                servantList =  CommonUtils.getServantList(cur);

                Cursor cur1 = null;
                servantSkillList.clear();

                for (int i = 0; i < servantList.size(); i++) {

                    int servantId = servantList.get(i).getId();

                    cur1 = dbManager.database.rawQuery("SELECT * FROM ServantsSkill WHERE "+
                                    " id = ? ORDER BY CAST(id AS SIGNED) ASC",

                            new String[]{servantId+""});

                    List<ServantSkill> servantSkillData = CommonUtils.getServantSkillData(cur1);

                    servantSkillList.add(servantSkillData.get(0));

                }

                if (cur1 != null) {
                    cur1.close();
                }

                if (cur != null) {
                    cur.close();
                }
                dbManager.closeDatabase();
                getView().setServantList(servantList,servantSkillList);
            }else{
                servantList = null;
                getView().setServantList(servantList,servantSkillList);
            }
        }catch (Exception e){
            e.printStackTrace();
//            mView.showCharacter(ctx.getString(R.string.database_error),R.mipmap.altria_alter_b);
        }
    }

}

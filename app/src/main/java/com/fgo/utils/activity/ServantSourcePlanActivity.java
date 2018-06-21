package com.fgo.utils.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.king.frame.mvp.base.QuickActivity;

import com.fgo.utils.R;
import com.fgo.utils.bean.MessageEvent;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.bean.SkillSourceBean;
import com.fgo.utils.bean.SourcePlanBean;
import com.fgo.utils.db.DBManager;
import com.fgo.utils.mvp.presenter.ServantSourcePlanPresenter;
import com.fgo.utils.mvp.view.ServantSourcePlanView;
import com.fgo.utils.ui.view.CustomPopWindow;
import com.fgo.utils.utils.CommonUtils;
import com.fgo.utils.utils.DbUtils;
import com.fgo.utils.utils.SharedPreferencesUtils;
import com.fgo.utils.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServantSourcePlanActivity extends QuickActivity<ServantSourcePlanView, ServantSourcePlanPresenter> implements ServantSourcePlanView, View.OnClickListener {

    private boolean isMaXiu;
    private ServantSkill servantSkillItem;
    private TextView mServantSpiritDq, mServantSpiritMb, mServantSkillOneName, mServantSkillOneDq, mServantSkillOneMb, mServantSkillTwoDq,
            mServantSkillTwoName, mServantSkillTwoMb, mServantSkillThreeDq, mServantSkillThreeName, mServantSkillThreeMb;

    private List<SkillSourceBean> skillSourceList;
    private List<SkillSourceBean> servantSourceList;
    private RelativeLayout mServantSkillSelect;
    private DBManager dbManager;
    private CustomPopWindow mCustomPopWindow;
    private LinearLayout mServantSpiritLL;
    private int id;

    @NonNull
    @Override
    public ServantSourcePlanPresenter createPresenter() {

        return new ServantSourcePlanPresenter();
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_servant_source_plan;
    }


    @Override
    public void initUI() {


        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);

        dbManager = new DBManager(this);


        //获取数据源
        servantSkillItem = (ServantSkill) getIntent().getSerializableExtra("servantSkillItem");
        isMaXiu = getIntent().getBooleanExtra("isMaXiu", false);

        mServantSpiritLL = findViewById(R.id.servant_spirit_based_return_ll);

        //当前
        mServantSpiritDq = findViewById(R.id.servant_spirit_based_return_dq_et);
        //目标
        mServantSpiritMb = findViewById(R.id.servant_spirit_based_return_mb_et);


        //技能1 name
        mServantSkillOneName = findViewById(R.id.servant_skill_intensify_one_name);
        //技能1 当前
        mServantSkillOneDq = findViewById(R.id.servant_skill_intensify_one_dq_ed);
        //技能1 目标
        mServantSkillOneMb = findViewById(R.id.servant_skill_intensify_one_mb_ed);

        //技能2 name
        mServantSkillTwoName = findViewById(R.id.servant_skill_intensify_two_name);
        //技能2 当前
        mServantSkillTwoDq = findViewById(R.id.servant_skill_intensify_two_dq_ed);
        //技能2 目标
        mServantSkillTwoMb = findViewById(R.id.servant_skill_intensify_two_mb_ed);

        //技能3 name
        mServantSkillThreeName = findViewById(R.id.servant_skill_intensify_three_name);
        //技能3 当前
        mServantSkillThreeDq = findViewById(R.id.servant_skill_intensify_three_dq_ed);
        //技能3 目标
        mServantSkillThreeMb = findViewById(R.id.servant_skill_intensify_three_mb_ed);

        //提交
        mServantSkillSelect = findViewById(R.id.servant_skill_intensify_select);

        if (isMaXiu) {
            mServantSpiritLL.setVisibility(View.GONE);
        } else {
            mServantSpiritLL.setVisibility(View.VISIBLE);
        }


        mServantSkillSelect.setOnClickListener(this);

        mServantSpiritDq.setOnClickListener(this);
        mServantSpiritMb.setOnClickListener(this);
        mServantSkillOneDq.setOnClickListener(this);
        mServantSkillOneMb.setOnClickListener(this);
        mServantSkillTwoDq.setOnClickListener(this);
        mServantSkillTwoMb.setOnClickListener(this);
        mServantSkillThreeDq.setOnClickListener(this);
        mServantSkillThreeMb.setOnClickListener(this);
    }


    @Override
    public void initData() {

        setEtData();

        //设置技能,进阶资源数据
        setSkillAndServantSourdeData();

        mServantSkillOneName.setText(servantSkillItem.getSkill_one_name());
        mServantSkillTwoName.setText(servantSkillItem.getSkill_two_name());
        mServantSkillThreeName.setText(servantSkillItem.getSkill_three_name());
    }

    private void setEtData() {

        id = servantSkillItem.getId();
        String param = (String) SharedPreferencesUtils.getParam(this, "" + id, "0|0|1|1|1|1|1|1");

        String[] split = param.split("\\|");
        mServantSpiritDq.setText(split[0]);
        mServantSpiritMb.setText(split[1]);
        mServantSkillOneDq.setText(split[2]);
        mServantSkillOneMb.setText(split[3]);
        mServantSkillTwoDq.setText(split[4]);
        mServantSkillTwoMb.setText(split[5]);
        mServantSkillThreeDq.setText(split[6]);
        mServantSkillThreeMb.setText(split[7]);
    }

    private void setSkillAndServantSourdeData() {
        skillSourceList = new ArrayList<>();
        servantSourceList = new ArrayList<>();

        //素材  剑之辉石|剑之辉石|剑之魔石|剑之魔石,凤凰的羽毛|剑之秘石,凤凰的羽毛|剑之秘石,八连双晶|八连双晶,龙牙|龙牙,无间的齿车|传承结晶
        String skill_material_arr = servantSkillItem.getSkill_material_arr();
        //数量  4|8|4|8, 4|4, 7|8, 10|20, 4|12, 10|1
        String skill_material_num_arr = servantSkillItem.getSkill_material_num_arr();
        //图片  sphere01.png|sphere01.png|ruby01.png|ruby01.png,gear.png|star01.png,gear.png|star01.png,plate.png|plate.png,clevis.png|clevis.png,claw.png|cubes.png
        String skill_material_img_arr = servantSkillItem.getSkill_material_img_arr();
        //qp   50000|100000|300000|400000|1000000|1250000|2500000|3000000|5000000
        String skill_cost_arr = servantSkillItem.getSkill_cost_arr();

        //素材  剑之辉石|剑之辉石|剑之魔石|剑之魔石,凤凰的羽毛|剑之秘石,凤凰的羽毛|剑之秘石,八连双晶|八连双晶,龙牙|龙牙,无间的齿车|传承结晶
        String servant_material_arr = servantSkillItem.getBreak_material_arr();
        //数量  4|8|4|8, 4|4, 7|8, 10|20, 4|12, 10|1
        String servant_material_num_arr = servantSkillItem.getBreak_material_num_arr();
        //图片  sphere01.png|sphere01.png|ruby01.png|ruby01.png,gear.png|star01.png,gear.png|star01.png,plate.png|plate.png,clevis.png|clevis.png,claw.png|cubes.png
        String servant_material_img_arr = servantSkillItem.getBreak_material_img_arr();
        //qp   50000|100000|300000|400000|1000000|1250000|2500000|3000000|5000000
        String servant_cost_arr = servantSkillItem.getBreak_cost_arr();

        //技能资源数据
        skillSourceList = CommonUtils.getSourceList(skillSourceList, skill_material_arr, skill_material_num_arr, skill_material_img_arr, skill_cost_arr);
        //英灵进阶资源数据
        servantSourceList = CommonUtils.getSourceList(servantSourceList, servant_material_arr, servant_material_num_arr, servant_material_img_arr, servant_cost_arr);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //灵基 当前
            case R.id.servant_spirit_based_return_dq_et:
                View spiritDq = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                setCustomPopData(spiritDq, mServantSpiritDq, "servant");
                break;
            case R.id.servant_spirit_based_return_mb_et:
                View spiritMb = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                setCustomPopData(spiritMb, mServantSpiritMb, "servant");
                break;

            //技能1
            case R.id.servant_skill_intensify_one_dq_ed:
                View skillOneDq = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                setCustomPopData(skillOneDq, mServantSkillOneDq, "skill");
                break;
            case R.id.servant_skill_intensify_one_mb_ed:
                View skillOneMb = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                setCustomPopData(skillOneMb, mServantSkillOneMb, "skill");
                break;

            //技能2
            case R.id.servant_skill_intensify_two_dq_ed:
                View skillTwoDq = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                setCustomPopData(skillTwoDq, mServantSkillTwoDq, "skill");
                break;
            case R.id.servant_skill_intensify_two_mb_ed:
                View skillTwoMb = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                setCustomPopData(skillTwoMb, mServantSkillTwoMb, "skill");
                break;

            //技能3
            case R.id.servant_skill_intensify_three_dq_ed:
                View skillThreeDq = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                setCustomPopData(skillThreeDq, mServantSkillThreeDq, "skill");
                break;
            case R.id.servant_skill_intensify_three_mb_ed:
                View skillThreeMb = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                setCustomPopData(skillThreeMb, mServantSkillThreeMb, "skill");
                break;


            case R.id.servant_skill_intensify_select:
                int servantSpiritDq = valueInt(mServantSpiritDq.getText().toString());
                int servantSpiritMb = valueInt(mServantSpiritMb.getText().toString());
                if (servantSpiritMb < servantSpiritDq) {
                    mServantSpiritMb.setText(0 + "");
                    Toast.makeText(this, "目标等级小于当前等级", Toast.LENGTH_SHORT).show();
                    return;
                }
                int servantSkillOneDq = valueInt(mServantSkillOneDq.getText().toString());
                int servantSkillOneMb = valueInt(mServantSkillOneMb.getText().toString());
                if (servantSkillOneMb < servantSkillOneDq) {
                    mServantSkillOneMb.setText(0 + "");
                    Toast.makeText(this, "目标等级小于当前等级", Toast.LENGTH_SHORT).show();
                    return;
                }
                int servantSkillTwoDq = valueInt(mServantSkillTwoDq.getText().toString());
                int servantSkillTwoMb = valueInt(mServantSkillTwoMb.getText().toString());
                if (servantSkillTwoMb < servantSkillTwoDq) {
                    mServantSkillTwoMb.setText(0 + "");
                    Toast.makeText(this, "目标等级小于当前等级", Toast.LENGTH_SHORT).show();
                    return;
                }
                int servantSkillThreeDq = valueInt(mServantSkillThreeDq.getText().toString());
                int servantSkillThreeMb = valueInt(mServantSkillThreeMb.getText().toString());
                if (servantSkillThreeMb < servantSkillThreeDq) {
                    mServantSkillThreeMb.setText(0 + "");
                    Toast.makeText(this, "目标等级小于当前等级", Toast.LENGTH_SHORT).show();
                    return;
                }

                String level = servantSpiritDq + "|" + servantSpiritMb + "|" + servantSkillOneDq + "|" + servantSkillOneMb + "|" + servantSkillTwoDq + "|" + servantSkillTwoMb + "|" + servantSkillThreeDq + "|" + servantSkillThreeMb;
                int id = servantSkillItem.getId();
                SharedPreferencesUtils.setParam(this, "" + id, level);

                List<String> servantSpiritList = new ArrayList<>();
                List<String> servantSkillOneList = new ArrayList<>();
                List<String> servantSkillTwoList = new ArrayList<>();
                List<String> servantSkillThreeList = new ArrayList<>();

                servantSpiritList = getSourceData(servantSpiritDq, servantSpiritMb, servantSpiritList, 1);

                servantSkillOneList = getSourceData(servantSkillOneDq - 1, servantSkillOneMb - 1, servantSkillOneList, 2);
                servantSkillTwoList = getSourceData(servantSkillTwoDq - 1, servantSkillTwoMb - 1, servantSkillTwoList, 2);
                servantSkillThreeList = getSourceData(servantSkillThreeDq - 1, servantSkillThreeMb - 1, servantSkillThreeList, 2);
                servantSpiritList.addAll(servantSkillOneList);
                servantSpiritList.addAll(servantSkillTwoList);
                servantSpiritList.addAll(servantSkillThreeList);

                String[] servantSpiritArray = listToArray(servantSpiritList);

                Map<String, String> servantSpiritMap = CommonUtils.setValue(servantSpiritArray);

                //判断以前有没有存储过材料
                String param = (String) SharedPreferencesUtils.getParam(ServantSourcePlanActivity.this, id + "skill", "");
                Map<String, String> oldServantSpiritMap = CommonUtils.transStringToMap(param);
                if (!TextUtils.isEmpty(param)) {
                    //删除以前存储材料
                    deleteDatabaseData(oldServantSpiritMap);
                }

                //存储现在材料
                String s = CommonUtils.transMapToString(servantSpiritMap);
                SharedPreferencesUtils.setParam(ServantSourcePlanActivity.this, id + "skill", s);

                //设置材料数据到数据库
                setDataToDb(servantSpiritMap);

                EventBus.getDefault().post(new MessageEvent("refresh"));

                finish();
                break;
        }
    }


    private void deleteDatabaseData(Map<String, String> oldServantSpiritMap) {

        //从数据库删除当前角色材料
        dbManager.getDatabase();

        for (String key : oldServantSpiritMap.keySet()) {
            int value = Integer.parseInt(oldServantSpiritMap.get(key));
//            Cursor cursor = dbManager.database.rawQuery("SELECT * FROM Materials WHERE " +
//                            "name LIKE ?",
//
//                    new String[]{"%" + key + "%"});

            Cursor cursor = DbUtils.searchData(dbManager, getContext(), "Materials", key, "", false);

            ArrayList<SourcePlanBean> sourcePlan = CommonUtils.getSkillList(cursor);
            int need = sourcePlan.get(0).getNeed();

            ContentValues contentValues = new ContentValues();

            contentValues.put("need", need - value);

            String[] args = {String.valueOf(key)};
            dbManager.database.update("Materials", contentValues, " name LIKE ?", args);

        }

    }

    private void setCustomPopData(View spiritMb, TextView tv, String str) {
        //处理popWindow 显示内容
        handleLogic(spiritMb, tv, str);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(spiritMb)
                .create()
                .showAsDropDown(tv, 0, 20);
    }

    private void setDataToDb(Map<String, String> StringMap) {

        dbManager.getDatabase();

        for (String key : StringMap.keySet()) {
            int value = Integer.parseInt(StringMap.get(key));

//            Cursor cursor = dbManager.database.rawQuery("SELECT * FROM Materials WHERE " +
//                            "name LIKE ?",
//
//                    new String[]{"%" + key + "%"});

            Cursor cursor = DbUtils.searchData(dbManager, getContext(), "Materials", key, "", false);

            ArrayList<SourcePlanBean> sourcePlan = CommonUtils.getSkillList(cursor);
            int need = sourcePlan.get(0).getNeed();

            ContentValues contentValues = new ContentValues();
            contentValues.put("need", value + need);

            String[] args = {String.valueOf(key)};
            dbManager.database.update("Materials", contentValues, " name LIKE ?", args);

        }

        dbManager.closeDatabase();

    }

    private String[] listToArray(List<String> list) {
        String[] strings = new String[list.size()];

        return list.toArray(strings);
    }

    private List<String> getSourceData(int servantSpiritDq, int servantSpiritMb, List<String> servantSpiritList, int flag) {
        List<String> skill_material;
        List<String> skill_material_num;
        for (int i = servantSpiritDq; i < servantSpiritMb; i++) {
            if (flag == 1) {
                skill_material = servantSourceList.get(i).getSkill_material();
                skill_material_num = servantSourceList.get(i).getSkill_material_num();

            } else {
                skill_material = skillSourceList.get(i).getSkill_material();
                skill_material_num = skillSourceList.get(i).getSkill_material_num();
            }
            for (int j = 0; j < skill_material.size(); j++) {
                String key = skill_material.get(j);
                String value = skill_material_num.get(j);
                servantSpiritList.add(key + "|" + value);
            }
        }
        return servantSpiritList;
    }

    private int valueInt(String servantSpiritDq) {
        if (!TextUtils.isEmpty(servantSpiritDq)) {
            return Integer.parseInt(servantSpiritDq);
        } else {
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MessageEvent("refresh"));
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView, final TextView tv, String servant) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.menu0:
                        tv.setText("0");
                        break;
                    case R.id.menu1:
                        tv.setText("1");
                        break;
                    case R.id.menu2:
                        tv.setText("2");
                        break;
                    case R.id.menu3:
                        tv.setText("3");
                        break;
                    case R.id.menu4:
                        tv.setText("4");
                        break;
                    case R.id.menu5:
                        tv.setText("5");
                        break;
                    case R.id.menu6:
                        tv.setText("6");
                        break;
                    case R.id.menu7:
                        tv.setText("7");
                        break;
                    case R.id.menu8:
                        tv.setText("8");
                        break;
                    case R.id.menu9:
                        tv.setText("9");
                        break;
                    case R.id.menu10:
                        tv.setText("10");
                        break;

                }
            }
        };
        contentView.findViewById(R.id.menu0).setOnClickListener(listener);
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
        contentView.findViewById(R.id.menu6).setOnClickListener(listener);
        contentView.findViewById(R.id.menu7).setOnClickListener(listener);
        contentView.findViewById(R.id.menu8).setOnClickListener(listener);
        contentView.findViewById(R.id.menu9).setOnClickListener(listener);
        contentView.findViewById(R.id.menu10).setOnClickListener(listener);

        if ("servant".equals(servant)) {
            contentView.findViewById(R.id.menu5).setVisibility(View.GONE);
            contentView.findViewById(R.id.menu6).setVisibility(View.GONE);
            contentView.findViewById(R.id.menu7).setVisibility(View.GONE);
            contentView.findViewById(R.id.menu8).setVisibility(View.GONE);
            contentView.findViewById(R.id.menu9).setVisibility(View.GONE);
            contentView.findViewById(R.id.menu10).setVisibility(View.GONE);
        } else {
            contentView.findViewById(R.id.menu0).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
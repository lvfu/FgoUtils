package com.fgo.utils.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fgo.utils.bean.ServantDetailBean;
import com.fgo.utils.bean.ServantSkillBean;
import com.king.frame.mvp.base.QuickActivity;
import com.fgo.utils.R;
import com.fgo.utils.adaper.SkillSourceAdaper;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.adaper.SkillAdaper;
import com.fgo.utils.bean.SkillSmallBean;
import com.fgo.utils.bean.SkillSourceBean;
import com.fgo.utils.mvp.presenter.SkillPresenter;
import com.fgo.utils.mvp.view.SkillView;
import com.fgo.utils.utils.CommonUtils;
import com.fgo.utils.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class SkillActivity extends QuickActivity<SkillView, SkillPresenter> implements SkillView {

    private Context context;
    private String skillSelect;
    private TextView mTitle;
    private RecyclerView mSkillRv, mSkillSourceRv;
    private SkillSmallBean skillSmallBean;
    private List<String> effectList;
    private List<SkillSourceBean> skillSourceList;

    private boolean isCanNewList = true;
    private LinearLayout mSkillSourceLl;
    private SkillPresenter skillPresenter;
    private int id;
    private ServantSkillBean.DataBean servantSkillData;


    @Override
    public int getRootViewId() {
        return R.layout.activity_skill;
    }

    @Override
    public void initUI() {
        context = this;
        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);

        skillSelect = getIntent().getStringExtra("skillSelect");
        id = getIntent().getIntExtra("id", -1);
        //title
        mTitle = findViewById(R.id.activity_skill_title);

        //技能
        mSkillRv = findViewById(R.id.skill_all_data_rv);
        mSkillSourceRv = findViewById(R.id.skill_source_data_rv);

        mSkillSourceLl = findViewById(R.id.servant_info_line_skill_source_ll);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSkillRv.setLayoutManager(linearLayoutManager);


        LinearLayoutManager linearLayoutSkillManager = new LinearLayoutManager(this);
        linearLayoutSkillManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSkillSourceRv.setLayoutManager(linearLayoutSkillManager);
    }

    @Override
    public void initData() {

        skillPresenter.getServantSkillData(id, skillSelect);

    }

    @Override
    public void showServantSkillData(ServantSkillBean body) {
        String code = body.getCode();

        if ("success".equals(code)) {

            servantSkillData = body.getData();

            //设置title数据
            setTitleData();

            //设置rv数据
            setSkillData();

            //设置技能资源数据
            setSkillSourdeData();

        } else {

        }

    }

    private void setSkillSourdeData() {
        skillSourceList = new ArrayList<>();

        //素材  剑之辉石|剑之辉石|剑之魔石|剑之魔石,凤凰的羽毛|剑之秘石,凤凰的羽毛|剑之秘石,八连双晶|八连双晶,龙牙|龙牙,无间的齿车|传承结晶
        String skill_material_arr = servantSkillData.getSkill_material_arr();
        //数量  4|8|4|8, 4|4, 7|8, 10|20, 4|12, 10|1
        String skill_material_num_arr = servantSkillData.getSkill_material_num_arr();
        //图片  sphere01.png|sphere01.png|ruby01.png|ruby01.png,gear.png|star01.png,gear.png|star01.png,plate.png|plate.png,clevis.png|clevis.png,claw.png|cubes.png
        String skill_material_img_arr = servantSkillData.getSkill_material_img_arr();
        //qp   50000|100000|300000|400000|1000000|1250000|2500000|3000000|5000000
        String skill_cost_arr = servantSkillData.getSkill_cost_arr();

        skillSourceList = CommonUtils.getSourceList(skillSourceList, skill_material_arr, skill_material_num_arr, skill_material_img_arr, skill_cost_arr);

        SkillSourceAdaper skillSourceAdaper = new SkillSourceAdaper(skillSourceList, this, 0, "skill");
        mSkillSourceRv.setAdapter(skillSourceAdaper);


    }

    private void setSkillData() {
        if (skillSelect.equals("1")) {
            setSkillBean();
        } else if (skillSelect.equals("2")) {
            setSkillBean();
        } else if (skillSelect.equals("3")) {
            setSkillBean();
        }

        SkillAdaper skillAdaper = new SkillAdaper(servantSkillData, skillSmallBean, this);
        mSkillRv.setAdapter(skillAdaper);
    }

    private void setSkillBean() {
        skillSmallBean = new SkillSmallBean();


        setBean(servantSkillData.getSkill_name(), servantSkillData.getSkill_level(),
                servantSkillData.getSkill_cool_down(), servantSkillData.getSkill_effect(),
                servantSkillData.getSkill_effect_value(), servantSkillData.getSkill_img());


    }

    private void setBean(String skill_name, String skill_level, String skill_cool_down, String skill_effect, String skill_effect_value, String skill_img) {

        skillSmallBean.setSkillName(skill_name);
        skillSmallBean.setSkillLevel(skill_level);
        skillSmallBean.setSkillCoolDown(skill_cool_down);
        skillSmallBean.setSkillImage(skill_img);


        List<String> effectList = new ArrayList<>();
        List<String> effectValueList = new ArrayList<>();

        String[] split = skill_effect.split("&");
        for (int i = 0; i < split.length; i++) {
            effectList.add(split[i]);
        }

        String[] split1 = skill_effect_value.split("-");
        for (int i = 0; i < split1.length; i++) {
            effectValueList.add(split1[i]);
        }

        skillSmallBean.setSkillEffect(effectList);
        skillSmallBean.setSkillEffectValue(effectValueList);
    }


    private void setTitleData() {
        mTitle.setText(servantSkillData.getSkill_name());
        mSkillSourceLl.setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public SkillPresenter createPresenter() {
        skillPresenter = new SkillPresenter();
        return skillPresenter;
    }


}

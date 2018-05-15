package com.fgo.utils.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private ServantSkill servantSkillItem;
    private String skillSelect;
    private TextView mTitle;
    private RecyclerView mSkillRv, mSkillSourceRv;
    private SkillSmallBean skillSmallBean;
    private List<String> effectList;
    private List<SkillSourceBean> skillSourceList;

    private boolean isCanNewList = true;
    private LinearLayout mSkillSourceLl;

    @Override
    public int getRootViewId() {
        return R.layout.activity_skill;
    }

    @Override
    public void initUI() {
        context = this;
        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);

        //获取数据源
        servantSkillItem = (ServantSkill) getIntent().getSerializableExtra("servantSkillItem");
        skillSelect = getIntent().getStringExtra("skillSelect");

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

        //设置title数据
        setTitleData(skillSelect);

        //设置rv数据
        setSkillData();

        //设置技能资源数据
        setSkillSourdeData();

    }

    private void setSkillSourdeData() {
        skillSourceList = new ArrayList<>();

        //素材  剑之辉石|剑之辉石|剑之魔石|剑之魔石,凤凰的羽毛|剑之秘石,凤凰的羽毛|剑之秘石,八连双晶|八连双晶,龙牙|龙牙,无间的齿车|传承结晶
        String skill_material_arr = servantSkillItem.getSkill_material_arr();
        //数量  4|8|4|8, 4|4, 7|8, 10|20, 4|12, 10|1
        String skill_material_num_arr = servantSkillItem.getSkill_material_num_arr();
        //图片  sphere01.png|sphere01.png|ruby01.png|ruby01.png,gear.png|star01.png,gear.png|star01.png,plate.png|plate.png,clevis.png|clevis.png,claw.png|cubes.png
        String skill_material_img_arr = servantSkillItem.getSkill_material_img_arr();
        //qp   50000|100000|300000|400000|1000000|1250000|2500000|3000000|5000000
        String skill_cost_arr = servantSkillItem.getSkill_cost_arr();

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

        SkillAdaper skillAdaper = new SkillAdaper(servantSkillItem, skillSmallBean, this);
        mSkillRv.setAdapter(skillAdaper);
    }

    private void setSkillBean() {
        skillSmallBean = new SkillSmallBean();

        if (skillSelect.equals("1")) {
            setBean(servantSkillItem.getSkill_one_name(), servantSkillItem.getSkill_one_level(),
                    servantSkillItem.getSkill_one_cool_down(), servantSkillItem.getSkill_one_effect(),
                    servantSkillItem.getSkill_one_effect_value(), servantSkillItem.getSkill_one_img());
        } else if (skillSelect.equals("2")) {
            setBean(servantSkillItem.getSkill_two_name(), servantSkillItem.getSkill_two_level(),
                    servantSkillItem.getSkill_two_cool_down(), servantSkillItem.getSkill_two_effect(),
                    servantSkillItem.getSkill_two_effect_value(), servantSkillItem.getSkill_two_img());
        } else if (skillSelect.equals("3")) {
            setBean(servantSkillItem.getSkill_three_name(), servantSkillItem.getSkill_three_level(),
                    servantSkillItem.getSkill_three_cool_down(), servantSkillItem.getSkill_three_effect(),
                    servantSkillItem.getSkill_three_effect_value(), servantSkillItem.getSkill_three_img());
        }

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


    private void setTitleData(String str) {
        switch (str) {
            case "1":
                mTitle.setText(servantSkillItem.getSkill_one_name());
                mSkillSourceLl.setVisibility(View.VISIBLE);
                break;
            case "2":
                mTitle.setText(servantSkillItem.getSkill_two_name());
                mSkillSourceLl.setVisibility(View.GONE);
                break;
            case "3":
                mTitle.setText(servantSkillItem.getSkill_three_name());
                mSkillSourceLl.setVisibility(View.GONE);
                break;
        }
    }

    @NonNull
    @Override
    public SkillPresenter createPresenter() {
        return new SkillPresenter();
    }
}

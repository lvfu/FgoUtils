package com.fgo.utils.activity;

import android.renderscript.Short3;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.king.frame.mvp.base.QuickActivity;
import com.fgo.utils.R;
import com.fgo.utils.adaper.ServantImgAdaper;
import com.fgo.utils.adaper.SkillSourceAdaper;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.bean.SkillSourceBean;
import com.fgo.utils.mvp.presenter.ServantSourcePresenter;
import com.fgo.utils.mvp.view.ServantSourceView;
import com.fgo.utils.utils.CommonUtils;
import com.fgo.utils.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfu on 2018/4/16.
 */

public class ServantSourceActivity extends QuickActivity<ServantSourceView, ServantSourcePresenter> implements ServantSourceView {
    private ServantSkill servantSkillItem;
    private RecyclerView mServantSourceRv, mServantImgRv;
    private boolean isCanNewList = true;
    private List<String> splitMaterialList = new ArrayList<>();
    private List<String> splitMaterialNumList = new ArrayList<>();
    private List<String> splitMaterialImgList = new ArrayList<>();
    private List<SkillSourceBean> servantSourceList;
    private List<String> servanImgList = new ArrayList<>();
    private boolean isMaXiu;
    private LinearLayout mServantSourceLl;
    //https://fatego.oss-cn-beijing.aliyuncs.com/fate/001-a1.jpg
    private String url = "https://fatego.oss-cn-beijing.aliyuncs.com/fate/";
    private List<String> imgurl;
    private String isAct;

    @Override
    public int getRootViewId() {
        return R.layout.activity_servant_source;
    }

    @Override
    public void initUI() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);
        //获取数据源
        servantSkillItem = (ServantSkill) getIntent().getSerializableExtra("servantSkillItem");
        isMaXiu = getIntent().getBooleanExtra("isMaXiu", false);
        //进阶
        mServantSourceRv = findViewById(R.id.servant_source_rv);
        mServantSourceLl = findViewById(R.id.servant_source_ll);

        mServantImgRv = findViewById(R.id.servant_img_rv);
        LinearLayoutManager linearLayoutImgManager = new LinearLayoutManager(this);
        linearLayoutImgManager.setOrientation(LinearLayoutManager.VERTICAL);
        mServantImgRv.setLayoutManager(linearLayoutImgManager);


        LinearLayoutManager linearLayoutSkillManager = new LinearLayoutManager(this);
        linearLayoutSkillManager.setOrientation(LinearLayoutManager.VERTICAL);
        mServantSourceRv.setLayoutManager(linearLayoutSkillManager);

        if (isMaXiu) {
            mServantSourceLl.setVisibility(View.GONE);
        } else {
            mServantSourceLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
        //设置技能资源数据
        setServantSourceData();

        //图片资源数据
        setServantImgData();
    }

    private void setServantImgData() {
        String num = "";
        if (servantSkillItem.getId() > 0 && servantSkillItem.getId() < 10) {
            num = new StringBuilder().append("00").append(servantSkillItem.getId()).toString();
        } else if (servantSkillItem.getId() >= 10 && servantSkillItem.getId() < 100) {
            num = new StringBuilder().append("0").append(servantSkillItem.getId()).toString();
        } else {
            num = new StringBuilder().append(servantSkillItem.getId()).toString();
        }

        //活动id 61 69 73 92 111 115 133 137 138 141 162 166 182

        if (Integer.parseInt(num) == 61 | Integer.parseInt(num) == 69 | Integer.parseInt(num) == 73
                | Integer.parseInt(num) == 92 | Integer.parseInt(num) == 111 | Integer.parseInt(num) == 115
                | Integer.parseInt(num) == 133 | Integer.parseInt(num) == 137 | Integer.parseInt(num) == 138
                | Integer.parseInt(num) == 141 | Integer.parseInt(num) == 162 | Integer.parseInt(num) == 166
                | Integer.parseInt(num) == 182 | Integer.parseInt(num) == 190 | Integer.parseInt(num) == 191) {

            imgurl = getimgurl(2, num);
            isAct = "true";
        } else {

            imgurl = getimgurl(4, num);
            isAct = "false";
        }


        ServantImgAdaper skillSourceAdaper = new ServantImgAdaper(imgurl,
                this, 0, isAct);
        mServantImgRv.setAdapter(skillSourceAdaper);

    }

    //https://fatego.oss-cn-beijing.aliyuncs.com/fate/001-a1.jpg
    public List<String> getimgurl(int length, String num) {
        servanImgList.clear();

        for (int i = 0; i < length; i++) {

            if (length == 2) {
                if (i == 0) {
                    servanImgList.add(url + num + "-a1.jpg");
                } else {
                    servanImgList.add(url + num + "-a2.jpg");
                }
            } else {
                if (i == 0) {
                    servanImgList.add(url + num + "-a1.jpg");
                } else if (i == 1) {
                    servanImgList.add(url + num + "-b2.jpg");
                } else if (i == 2) {
                    servanImgList.add(url + num + "-c3.jpg");
                } else if (i == 3) {
                    servanImgList.add(url + num + "-d3.jpg");
                }

            }

        }
        return servanImgList;
    }

    private void setServantSourceData() {
        servantSourceList = new ArrayList<>();

        //素材  剑之辉石|剑之辉石|剑之魔石|剑之魔石,凤凰的羽毛|剑之秘石,凤凰的羽毛|剑之秘石,八连双晶|八连双晶,龙牙|龙牙,无间的齿车|传承结晶
        String skill_material_arr = servantSkillItem.getBreak_material_arr();
        //数量  4|8|4|8, 4|4, 7|8, 10|20, 4|12, 10|1
        String skill_material_num_arr = servantSkillItem.getBreak_material_num_arr();
        //图片  sphere01.png|sphere01.png|ruby01.png|ruby01.png,gear.png|star01.png,gear.png|star01.png,plate.png|plate.png,clevis.png|clevis.png,claw.png|cubes.png
        String skill_material_img_arr = servantSkillItem.getBreak_material_img_arr();
        //qp   50000|100000|300000|400000|1000000|1250000|2500000|3000000|5000000
        String skill_cost_arr = servantSkillItem.getBreak_cost_arr();

        servantSourceList = CommonUtils.getSourceList(servantSourceList, skill_material_arr, skill_material_num_arr, skill_material_img_arr, skill_cost_arr);

        SkillSourceAdaper skillSourceAdaper = new SkillSourceAdaper(servantSourceList, this, 0, "servant");
//        skillSourceAdaper.setIsMaxiu(isMaXiu);
        mServantSourceRv.setAdapter(skillSourceAdaper);


    }


    @NonNull
    @Override
    public ServantSourcePresenter createPresenter() {
        return new ServantSourcePresenter();
    }
}

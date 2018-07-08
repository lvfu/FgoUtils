package com.fgo.utils.activity;

import android.renderscript.Short3;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fgo.utils.adaper.HeroFragmentAdaper;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantAdvancedBean;
import com.fgo.utils.bean.ServantListNBean;
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

    private RecyclerView mServantSourceRv, mServantImgRv;
    private List<SkillSourceBean> servantSourceList;
    private List<String> servanImgList = new ArrayList<>();
    private boolean isMaXiu;
    private LinearLayout mServantSourceLl;
    //https://fatego.oss-cn-beijing.aliyuncs.com/fate/001-a1.jpg
    private String url = "https://fatego.oss-cn-beijing.aliyuncs.com/fate/";

    private ServantSourcePresenter servantSourcePresenter;
    private int id;
    private ServantAdvancedBean servantSkillData;
    private ServantAdvancedBean servantSkillItem;


    @Override
    public int getRootViewId() {
        return R.layout.activity_servant_source;
    }

    @Override
    public void initUI() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);
        //获取数据源
        isMaXiu = getIntent().getBooleanExtra("isMaXiu", false);
        id = getIntent().getIntExtra("id", -1);
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
        servantSourcePresenter.getServantAdvanced(id);
    }

    @Override
    public void showServantAdvancedData(BaseCommonBean<ServantAdvancedBean> body) {


        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();
        if ("success".equals(respCode)) {
            servantSkillItem = (ServantAdvancedBean) data.getModel();
            //设置技能资源数据
            setServantSourceData();
            //图片资源数据
            setServantImgData();

        } else {
            Toast.makeText(this, respMsg, Toast.LENGTH_SHORT).show();
        }




    }

    private void setServantImgData() {
        List<String> servantImgUrl = servantSkillItem.getServantImgUrl();
        boolean activityFollower = servantSkillItem.isActivityFollower();
        ServantImgAdaper skillSourceAdaper = new ServantImgAdaper(servantImgUrl,
                this, 0, activityFollower + "");
        mServantImgRv.setAdapter(skillSourceAdaper);

    }

    private void setServantSourceData() {
        servantSourceList = new ArrayList<>();

        //素材  剑之辉石|剑之辉石|剑之魔石|剑之魔石,凤凰的羽毛|剑之秘石,凤凰的羽毛|剑之秘石,八连双晶|八连双晶,龙牙|龙牙,无间的齿车|传承结晶
        String skill_material_arr = servantSkillItem.getBreakMaterialArr();
        //数量  4|8|4|8, 4|4, 7|8, 10|20, 4|12, 10|1
        String skill_material_num_arr = servantSkillItem.getBreakMaterialNumArr();
        //图片  sphere01.png|sphere01.png|ruby01.png|ruby01.png,gear.png|star01.png,gear.png|star01.png,plate.png|plate.png,clevis.png|clevis.png,claw.png|cubes.png
        String skill_material_img_arr = servantSkillItem.getBreakMaterialImgArr();
        //qp   50000|100000|300000|400000|1000000|1250000|2500000|3000000|5000000
        String skill_cost_arr = servantSkillItem.getBreakCostArr();

        servantSourceList = CommonUtils.getSourceList(servantSourceList, skill_material_arr, skill_material_num_arr, skill_material_img_arr, skill_cost_arr);

        SkillSourceAdaper skillSourceAdaper = new SkillSourceAdaper(servantSourceList, this, 0, "servant");
//        skillSourceAdaper.setIsMaxiu(isMaXiu);
        mServantSourceRv.setAdapter(skillSourceAdaper);


    }


    @NonNull
    @Override
    public ServantSourcePresenter createPresenter() {
        servantSourcePresenter = new ServantSourcePresenter();
        return servantSourcePresenter;
    }


}

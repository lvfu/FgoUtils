package com.fgo.utils;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.hjm.bottomtabbar.BottomTabBar;
import com.king.frame.mvp.base.QuickActivity;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.fragment.PersonFragment;
import com.fgo.utils.fragment.HeroFragment;
import com.fgo.utils.fragment.SourcePlanFragment;
import com.fgo.utils.fragment.ParisFragment;
import com.fgo.utils.mvp.presenter.IPAddrPresenter;
import com.fgo.utils.mvp.view.IIPAddrView;
import com.fgo.utils.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends QuickActivity<IIPAddrView, IPAddrPresenter> implements IIPAddrView {

    BottomTabBar bottomTabBar;
    public List<ServantItem> servantList = new ArrayList<>();
    public List<ServantSkill> servantSkillList = new ArrayList<>();

    @NonNull
    @Override
    public IPAddrPresenter createPresenter() {
        return new IPAddrPresenter(this);
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);
        initBottomNavigation();

    }

    private void initBottomNavigation() {
        bottomTabBar = (BottomTabBar) findViewById(R.id.bottom_tab_bar);

        bottomTabBar.init(getSupportFragmentManager())
                .addTabItem("从者", R.mipmap.hero, HeroFragment.class)
                .addTabItem("攻略", R.mipmap.source, ParisFragment.class)
                .addTabItem("规划", R.mipmap.wiki, SourcePlanFragment.class)
                .addTabItem("个人", R.mipmap.person, PersonFragment.class);

    }


    @Override
    public void initData() {
        getPresenter().getSeventData();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);

    }

    @Override
    public void showCharacter(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setServantList(List<ServantItem> list,List<ServantSkill> skillList) {
        servantList.addAll(list);
        servantSkillList.addAll(skillList);
    }

    public List<ServantItem> getServantList() {
        return servantList;
    }

    public List<ServantSkill> getServantSkillList() {
        return servantSkillList;
    }

    public void setServantSkillList(List<ServantSkill> servantSkillList) {
        this.servantSkillList = servantSkillList;
    }
}

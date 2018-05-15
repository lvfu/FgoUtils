package com.fgo.utils.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.king.frame.mvp.base.QuickFragment;
import com.fgo.utils.MainActivity;
import com.fgo.utils.R;
import com.fgo.utils.activity.SearchActivity;
import com.fgo.utils.adaper.HeroFragmentAdaper;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.mvp.presenter.HeroPresenter;
import com.fgo.utils.mvp.view.HeroView;

import java.util.List;

/**
 * Created by lvfu on 2018/3/26.
 */

public class HeroFragment extends QuickFragment<HeroView, HeroPresenter> implements HeroView, View.OnClickListener {

    private RecyclerView seventRv;
    private HeroPresenter presenter;
    private HeroFragmentAdaper heroFragmentAdaper;
    private FloatingActionMenu menuRed;
    private FloatingActionButton mHeroSearch;


    @Override
    public int getRootViewId() {
        return R.layout.fragment_hero;
    }

    @Override
    public void initUI() {
        seventRv = (RecyclerView) getRootView().findViewById(R.id.hero_fragment_rv);

        //fly button
        menuRed = (FloatingActionMenu) getRootView().findViewById(R.id.menu_red);
        mHeroSearch = (FloatingActionButton) getRootView().findViewById(R.id.fab0);
        mHeroSearch.setOnClickListener(this);
    }

    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        seventRv.setLayoutManager(linearLayoutManager);

        List<ServantItem> servantList = ((MainActivity) getActivity()).getServantList();
        List<ServantSkill> servantSkillList = ((MainActivity) getActivity()).getServantSkillList();

        heroFragmentAdaper = new HeroFragmentAdaper(servantList, servantSkillList,getContext());
        seventRv.setAdapter(heroFragmentAdaper);
    }

    @Override
    public HeroPresenter createPresenter() {
        return new HeroPresenter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab0:
                //search
                menuRed.close(true);
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}

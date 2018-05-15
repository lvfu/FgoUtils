package com.king.frame.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.king.frame.R;
import com.king.frame.mvp.activity.ActivityCollector;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends MvpActivity<V, P> {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView( getRootViewId() );

        ActivityCollector.addActivity(this);
        initUI();
        initData();

    }

    public <T extends View> T findView(@IdRes int id){
        return (T)findViewById(id);
    }

    public Context getContext(){
        return this;
    }

    public void replaceFragment(Fragment fragmnet){
        replaceFragment( R.id.fragmentContent,fragmnet);
    }

    public void replaceFragment(@IdRes int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    public abstract @LayoutRes int getRootViewId();

    public abstract void initUI();

    public abstract void initData();

}

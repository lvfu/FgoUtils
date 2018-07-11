package com.fgo.utils.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fgo.utils.R;
import com.fgo.utils.bean.MessageEvent;
import com.fgo.utils.utils.StatusBarUtil;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.king.frame.mvp.base.BasePresenter;
import com.king.frame.mvp.base.QuickActivity;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends QuickActivity implements View.OnClickListener {


    private LinearLayout replaceAccount, loginOutAccount;

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initUI() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);
        replaceAccount = (LinearLayout) findView(R.id.setting_replace_account);
        loginOutAccount = (LinearLayout) findView(R.id.setting_logout_account);

        setOnClick();
    }

    private void setOnClick() {
        replaceAccount.setOnClickListener(this);
        loginOutAccount.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_replace_account:
                new MaterialDialog.Builder(this)
                        .content("是否更换账号？")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                EventBus.getDefault().post(new MessageEvent("refreshPersonForReplaceAccount"));
                                finish();
                            }
                        })
                        .show();


                break;

            case R.id.setting_logout_account:
                new MaterialDialog.Builder(this)
                        .content("是否退出账号？")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                EventBus.getDefault().post(new MessageEvent("refreshPersonForLoginOutAccount"));
                                finish();
                            }
                        })
                        .show();


                break;
        }
    }
}
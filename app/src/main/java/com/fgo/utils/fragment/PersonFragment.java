package com.fgo.utils.fragment;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fgo.utils.activity.SettingActivity;
import com.fgo.utils.adaper.HeroFragmentAdaper;
import com.fgo.utils.base.CircleImageView;
import com.fgo.utils.base.PrefUtil;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.LoginBean;
import com.fgo.utils.bean.MessageEvent;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.bean.userBean;
import com.fgo.utils.constant.GlobalConstant;
import com.fgo.utils.face.GetRequest_Interface;
import com.fgo.utils.utils.SharedPreferencesUtils;
import com.king.frame.mvp.base.QuickFragment;
import com.fgo.utils.MainActivity;
import com.fgo.utils.R;
import com.fgo.utils.db.DBManager;
import com.fgo.utils.mvp.presenter.PersonPresenter;
import com.fgo.utils.mvp.view.PersonView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fgo.utils.db.DBManager.DB_NAME;
import static com.fgo.utils.utils.UnCentHandler.TAG;

/**
 * Created by lvfu on 2018/3/26.
 */

public class PersonFragment extends QuickFragment<PersonView, PersonPresenter> implements View.OnClickListener, PersonView {

    private Context context;

    private PersonPresenter personPresenter;
    private CircleImageView mCircleImageView;
    private TextView mAddAccoumtTv, mLoginTv, mToRegeistTv, mNickNameTv;
    private LinearLayout mRegeistLl1, mNormalLl1, mLoginl1;
    List<LocalMedia> selectList = new ArrayList<>();
    private Button mRegeistFinsh, mLoginFinsh;
    private EditText mRegeistNickName, mRegeistUserName, mRegeistPwd, mLoginPwd, mLoginUserName;
    private RelativeLayout noAccountLl, haveAccountLl;
    private LinearLayout mPersionSetting;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_person;
    }

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);
        context = getContext();

        initView();
    }

    private void initView() {

        mCircleImageView = findView(R.id.persion_icon_iv);
        mRegeistLl1 = findView(R.id.persion_regeist_ll);
        mNormalLl1 = findView(R.id.persion_normal_ll);
        mLoginl1 = findView(R.id.persion_login_ll);
        mLoginTv = findView(R.id.persion_regeist_login_tv);
        mToRegeistTv = findView(R.id.persion_login_toregeist_tv);

        //注册
        mRegeistFinsh = findView(R.id.persion_regeist_finish_btn);
        mRegeistNickName = findView(R.id.persion_regeist_nickname_et);
        mRegeistUserName = findView(R.id.persion_regeist_username_et);
        mRegeistPwd = findView(R.id.persion_regeist_pwd_et);

        //登陆
        mLoginFinsh = findView(R.id.persion_login_finish_btn);
        mLoginPwd = findView(R.id.persion_login_pwd_et);
        mLoginUserName = findView(R.id.persion_login_username_et);

        //无账号topll
        noAccountLl = findView(R.id.persion_normal_no_account_ll);
        mAddAccoumtTv = findView(R.id.persion_no_account_tv);
        //有账号
        haveAccountLl = findView(R.id.persion_normal_top_ll);
        mNickNameTv = findView(R.id.persion_name_tv);

        //设置
        mPersionSetting = findView(R.id.persion_bottom_setting_ll);

        isLogin();

        setOnClick();

    }

    /**
     * 判断是否登陆过
     */
    private void isLogin() {

        boolean is_login = PrefUtil.getBoolean(getContext(), "is_login", false);
        if (is_login) {

            String nickname = (String) SharedPreferencesUtils.getParam(getContext(), "nickname", "");
            noAccountLl.setVisibility(View.GONE);
            haveAccountLl.setVisibility(View.VISIBLE);
            mNickNameTv.setText(nickname);
        } else {
            noAccountLl.setVisibility(View.VISIBLE);
            haveAccountLl.setVisibility(View.GONE);
        }
        mNormalLl1.setVisibility(View.VISIBLE);
        mRegeistLl1.setVisibility(View.GONE);
        mLoginl1.setVisibility(View.GONE);

    }

    private void setOnClick() {
        mAddAccoumtTv.setOnClickListener(this);
        mLoginTv.setOnClickListener(this);
        mToRegeistTv.setOnClickListener(this);
        mCircleImageView.setOnClickListener(this);
        mRegeistFinsh.setOnClickListener(this);
        mLoginFinsh.setOnClickListener(this);
        mPersionSetting.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }


    @Override
    public PersonPresenter createPresenter() {
        personPresenter = new PersonPresenter();

        return personPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.persion_no_account_tv:
                if (mRegeistLl1.getVisibility() == View.VISIBLE) {
                    mRegeistLl1.setVisibility(View.GONE);
                    mNormalLl1.setVisibility(View.VISIBLE);
                    mLoginl1.setVisibility(View.GONE);

                } else {
                    mRegeistLl1.setVisibility(View.VISIBLE);
                    mNormalLl1.setVisibility(View.GONE);
                    mLoginl1.setVisibility(View.GONE);
                }
                break;

            case R.id.persion_regeist_login_tv:
                mRegeistLl1.setVisibility(View.GONE);
                mNormalLl1.setVisibility(View.GONE);
                mLoginl1.setVisibility(View.VISIBLE);
                break;


            case R.id.persion_login_toregeist_tv:
                mRegeistLl1.setVisibility(View.VISIBLE);
                mNormalLl1.setVisibility(View.GONE);
                mLoginl1.setVisibility(View.GONE);
                break;

            //设置
            case R.id.persion_bottom_setting_ll:
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.persion_regeist_finish_btn:
//                mRegeistNickName，mRegeistUserName，mRegeistPwd
                String nickname = mRegeistNickName.getText().toString().trim();
                if (TextUtils.isEmpty(nickname)) {
                    Toast.makeText(getContext(), "昵称不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = mRegeistUserName.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getContext(), "账号不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = mRegeistPwd.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "密码不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                personPresenter.RegeistData(nickname, username, password);
                break;

            case R.id.persion_login_finish_btn:

                String usernameL = mLoginUserName.getText().toString().trim();
                if (TextUtils.isEmpty(usernameL)) {
                    Toast.makeText(getContext(), "账号不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String passwordL = mLoginPwd.getText().toString().trim();
                if (TextUtils.isEmpty(passwordL)) {
                    Toast.makeText(getContext(), "密码不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                personPresenter.LoginData(usernameL, passwordL);
                break;

            case R.id.persion_icon_iv:

                //头像选择
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(PersonFragment.this)
                        .openGallery(PictureMimeType.ofImage())
                        .theme(R.style.picture_style)
                        .maxSelectNum(1)
                        .minSelectNum(1)
                        .previewImage(true)
                        .isCamera(true)
                        .enableCrop(true)
                        .compress(true)
                        .glideOverride(160, 160)
                        .previewEggs(true)
                        .withAspectRatio(1, 1)
                        .freeStyleCropEnabled(true)
                        .circleDimmedLayer(false)
                        .showCropFrame(true)
                        .showCropGrid(true)
                        .openClickSound(false)
                        .selectionMedia(selectList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    LocalMedia localMedia = selectList.get(0);
                    Glide.with(getContext().getApplicationContext()).load(localMedia.getPath()).into(mCircleImageView);
                    break;
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //注册结果
    @Override
    public void showRegeistData(BaseCommonBean body) {
        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();
        if ("success".equals(respCode)) {
            String result = data.getResult();
            if ("True".equals(result)) {
//                //bottomll
                mRegeistLl1.setVisibility(View.GONE);
                mNormalLl1.setVisibility(View.VISIBLE);
                mLoginl1.setVisibility(View.GONE);

                mRegeistNickName.setText("");
                mRegeistUserName.setText("");
                mRegeistPwd.setText("");
                Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void showLoginData(BaseCommonBean<LoginBean> body) {
        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();
        if ("success".equals(respCode)) {
            LoginBean model = (LoginBean) data.getModel();

            mRegeistLl1.setVisibility(View.GONE);
            mNormalLl1.setVisibility(View.VISIBLE);
            mLoginl1.setVisibility(View.GONE);

            noAccountLl.setVisibility(View.GONE);
            haveAccountLl.setVisibility(View.VISIBLE);
            mNickNameTv.setText(model.getNickName() + "");

            PrefUtil.setBoolean(getContext(), "is_login", true);
            SharedPreferencesUtils.setParam(getContext(), "userId", model.getUserId());
            SharedPreferencesUtils.setParam(getContext(), "nickname", model.getNickName());
        } else {

            Toast.makeText(getContext(), respMsg, Toast.LENGTH_SHORT).show();

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("refreshPersonForReplaceAccount")) {
            loginOut();
            mLoginl1.setVisibility(View.VISIBLE);
            mNormalLl1.setVisibility(View.GONE);
        } else if (messageEvent.getMessage().equals("refreshPersonForLoginOutAccount")) {
            loginOut();
            mLoginl1.setVisibility(View.GONE);
            mNormalLl1.setVisibility(View.VISIBLE);
        }
    }

    private void loginOut() {
        PrefUtil.setBoolean(getContext(), "is_login", false);
        SharedPreferencesUtils.setParam(getContext(), "userId", "");
        SharedPreferencesUtils.setParam(getContext(), "nickname", "");
        mRegeistLl1.setVisibility(View.GONE);
        noAccountLl.setVisibility(View.VISIBLE);
        haveAccountLl.setVisibility(View.GONE);
    }
}

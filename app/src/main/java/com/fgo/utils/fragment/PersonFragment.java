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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fgo.utils.base.CircleImageView;
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
    private TextView mAddAccoumtTv, mLoginTv, mToRegeistTv;
    private LinearLayout mRegeistLl1, mNormalLl1, mLoginl1;
    List<LocalMedia> selectList = new ArrayList<>();

    @Override
    public int getRootViewId() {
        return R.layout.fragment_person;
    }

    @Override
    public void initUI() {

        context = getContext();

        initView();
    }

    private void initView() {
        mCircleImageView = findView(R.id.persion_icon_iv);
        mAddAccoumtTv = findView(R.id.persion_no_account_tv);
        mRegeistLl1 = findView(R.id.persion_regeist_ll);
        mNormalLl1 = findView(R.id.persion_normal_ll);
        mLoginl1 = findView(R.id.persion_login_ll);
        mLoginTv = findView(R.id.persion_regeist_login_tv);
        mToRegeistTv = findView(R.id.persion_login_toregeist_tv);
        setOnClick();
    }

    private void setOnClick() {
        mAddAccoumtTv.setOnClickListener(this);
        mLoginTv.setOnClickListener(this);
        mToRegeistTv.setOnClickListener(this);
        mCircleImageView.setOnClickListener(this);
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

    }

}

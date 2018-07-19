package com.fgo.utils.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fgo.utils.adaper.HeroFragmentAdaper;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantDetailBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.constant.GlobalData;
import com.king.frame.mvp.base.QuickActivity;
import com.fgo.utils.R;
import com.fgo.utils.adaper.ServantAdaper;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.bean.CardNumBean;
import com.fgo.utils.mvp.presenter.ServantPresenter;
import com.fgo.utils.mvp.view.ServantView;
import com.fgo.utils.utils.CommonUtils;
import com.fgo.utils.utils.StatusBarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfu on 2018/4/11.
 */

public class ServantActivity extends QuickActivity<ServantView, ServantPresenter> implements ServantView, View.OnClickListener {

    private TextView mServantTitle;
    private int id;
    private ImageView mServantIcon;
    private TextView mServantName;
    private TextView mServantClassType;
    private RecyclerView mServantRv;
    private CardNumBean cardNumBean;
    private List<String> mCardList;
    private TextView mBusterNum;
    private TextView mArtNum;
    private TextView mQuickNum;
    private TextView mExNum;
    private TextView mNickName;
    private TextView mAttribute;
    private ImageView mSkillOneIv;
    private TextView mSkillOneName;
    private ImageView mSkillTwoIv;
    private TextView mSkillTwoName;
    private ImageView mSkillThreeIv;
    private TextView mSkillThreeName;
    private RelativeLayout mSkillOneRl;
    private RelativeLayout mSkillTwoRl;
    private RelativeLayout mSkillThreeRl;
    private TextView mTreasureTv;
    private ImageView mTreasureIv;
    private VideoView mTreasureVideo;
    private RelativeLayout mServantLl;
    private RelativeLayout mServantSourcePlan;
    private ServantPresenter servantPresenter;
    private ServantDetailBean servantItem;
    private ServantSkill servantSkill;


    @Override
    public int getRootViewId() {
        return R.layout.activity_servant;
    }

    @Override
    public void initUI() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);
        //title
        mServantTitle = (TextView) findViewById(R.id.activity_servant_title);
        mServantLl = findViewById(R.id.hero_item_ll);

        //名字，头像，阶职
        mServantIcon = (ImageView) findViewById(R.id.servant_icon_iv);
        mServantName = (TextView) findViewById(R.id.servant_name_tv);
        mServantClassType = (TextView) findViewById(R.id.sevent_class_type_tv);
        //色卡数量
        mBusterNum = (TextView) findViewById(R.id.servant_normal_hit_buster_num);
        mArtNum = (TextView) findViewById(R.id.servant_normal_hit_art_num);
        mQuickNum = (TextView) findViewById(R.id.servant_normal_hit_quick_num);
        mExNum = (TextView) findViewById(R.id.servant_normal_hit_ex_num);

        //昵称
        mNickName = (TextView) findViewById(R.id.servant_info_nickname);
        //阵营
        mAttribute = (TextView) findViewById(R.id.servant_info_attribute);

        //技能1
        mSkillOneRl = findViewById(R.id.servant_info_skill_one_ll);
        mSkillOneIv = findViewById(R.id.servant_info_skill_one_iv);
        mSkillOneName = findViewById(R.id.servant_info_skill_one_name);
        //技能2
        mSkillTwoRl = findViewById(R.id.servant_info_skill_two_ll);
        mSkillTwoIv = findViewById(R.id.servant_info_skill_two_iv);
        mSkillTwoName = findViewById(R.id.servant_info_skill_two_name);
        //技能3
        mSkillThreeRl = findViewById(R.id.servant_info_skill_three_ll);
        mSkillThreeIv = findViewById(R.id.servant_info_skill_three_iv);
        mSkillThreeName = findViewById(R.id.servant_info_skill_three_name);

        //宝具
        mTreasureTv = findViewById(R.id.servant_info_skill_treasure_name);
        mTreasureIv = findViewById(R.id.servant_info_treasure_iv);
        mTreasureVideo = findViewById(R.id.servant_info_treasure_video);

        //素材规划
        mServantSourcePlan = findViewById(R.id.servant_source_plane_ll);
        //配卡rv
        mServantRv = (RecyclerView) findViewById(R.id.servant_name_card_rv);
        //配置ll
        LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        mServantRv.setLayoutManager(ll);


        setOnclick();

    }

    private void setOnclick() {
        mSkillOneRl.setOnClickListener(this);
        mSkillTwoRl.setOnClickListener(this);
        mSkillThreeRl.setOnClickListener(this);
        mTreasureVideo.setOnClickListener(this);
        mServantLl.setOnClickListener(this);
        mServantSourcePlan.setOnClickListener(this);
    }

    @Override
    public void initData() {
        id = getIntent().getIntExtra("id", -1);

        //暂时为了适配素材规划
        servantSkill = (ServantSkill) getIntent().getSerializableExtra("servantSkill");

        servantPresenter.getServantData(this.id);
    }

    @Override
    public void showServantData(BaseCommonBean<ServantDetailBean> body) {

        String respCode = body.getRespCode();
        String respMsg = body.getRespMsg();
        BaseCommonBean.BaseCommonData data = body.getData();
        if ("success".equals(respCode)) {
            servantItem = (ServantDetailBean) data.getModel();
            //获取色卡数量
            int arts_num = servantItem.getArts_num();
            int buster_num = servantItem.getBuster_num();
            int quick_num = servantItem.getQuick_num();

            //获取色卡hit
            setCardHit();

            //设置色卡
            setCardBean(arts_num, buster_num, quick_num);

            //设置头像等数据
            setResouse();

            //设置阵营
            setAttribute();

            //设置技能
            setSkillData();


            //设置宝具
            setTreasure();

            mServantTitle.setText(servantItem.getName());
            mNickName.setText(servantItem.getNickname());
            ServantAdaper adapter = new ServantAdaper(this, mCardList);
            mServantRv.setAdapter(adapter);

        } else {
            Toast.makeText(this, respMsg, Toast.LENGTH_SHORT).show();
        }

    }

    //HANDLER 控制视屏dialog
    private boolean isCanShowDialog = true;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            if (!mTreasureVideo.isPlaying()) {
                if (isCanShowDialog) {
                    showProgressDialog();
                }
            } else {
                if (isCanShowDialog) {
                    dismissProgressDialog();
                    isCanShowDialog = false;
                }
            }
            handler.postDelayed(runnable, 500);//每0.5秒监听一次是否在播放视频
        }
    };


    private void setTreasure() {
        String treasureColor = "";
        int treasure_color = servantItem.getTreasure_color();
        if (treasure_color == 1) {
            treasureColor = "buster";
        } else if (treasure_color == 2) {
            treasureColor = "arts";
        } else if (treasure_color == 3) {
            treasureColor = "quick";
        }
        String servant_treasure = subString(treasureColor.toLowerCase());
        mTreasureTv.setText(servantItem.getTreasure() + "");
        mTreasureIv.setImageResource(CommonUtils.getResourceId(servant_treasure, this));


    }

    private void setSkillData() {
        String skill_one_img = subString(servantItem.getSkill_one_img().toLowerCase());
        String skill_two_img = subString(servantItem.getSkill_two_img().toLowerCase());
        String skill_three_img = subString(servantItem.getSkill_three_img().toLowerCase());

        mSkillOneIv.setImageResource(CommonUtils.getResourceId(skill_one_img, this));
        mSkillTwoIv.setImageResource(CommonUtils.getResourceId(skill_two_img, this));
        mSkillThreeIv.setImageResource(CommonUtils.getResourceId(skill_three_img, this));

        mSkillOneName.setText(servantItem.getSkill_one_name());
        mSkillTwoName.setText(servantItem.getSkill_two_name());
        mSkillThreeName.setText(servantItem.getSkill_three_name());
    }


    public String subString(String str) {
        String[] split = str.split(".png");
        return split[0];
    }

    private void setAttribute() {
        int attribute = servantItem.getAttribute();
        switch (attribute) {
            case 0:
                mAttribute.setText("天");
                break;
            case 1:
                mAttribute.setText("地");
                break;
            case 2:
                mAttribute.setText("人");
                break;
            case 3:
                mAttribute.setText("星");
                break;
            case 4:
                mAttribute.setText("兽");
                break;
        }
    }

    private void setCardHit() {
        mBusterNum.setText(servantItem.getBuster_hit() + "");
        mArtNum.setText(servantItem.getArts_hit() + "");
        mQuickNum.setText(servantItem.getQuick_hit() + "");
        mExNum.setText(servantItem.getEx_hit() + "");

    }

    private void setCardBean(int arts_num, int buster_num, int quick_num) {
        mCardList = new ArrayList<>();
        for (int i = 0; i < buster_num; i++) {
            mCardList.add("b");
        }
        for (int i = 0; i < arts_num; i++) {
            mCardList.add("a");
        }
        for (int i = 0; i < quick_num; i++) {
            mCardList.add("q");
        }

    }


    private void setResouse() {

        int resId = getResources().getIdentifier("image" + id, "mipmap", getPackageName());
        if (resId != 0) {
            mServantIcon.setImageResource(resId);
        } else {
            String num = CommonUtils.getId(id);
            //从fgowiki获取头像
            String url = new StringBuilder().append("http://file.fgowiki.fgowiki.com/fgo/head/").append(num).append(".jpg").toString();

            Glide.with(getApplicationContext()).load(url).into(mServantIcon);

        }

        mServantName.setText(servantItem.getName());
        mServantClassType.setText(servantItem.getClass_type());
    }

    @NonNull
    @Override
    public ServantPresenter createPresenter() {
        servantPresenter = new ServantPresenter();
        return servantPresenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.servant_info_skill_one_ll:
                Intent intent = new Intent(this, SkillActivity.class);
//                intent.putExtra("servantSkillItem", servantSkillItem);
                intent.putExtra("skillSelect", "1");
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.servant_info_skill_two_ll:
                Intent intentTwo = new Intent(this, SkillActivity.class);
//                intentTwo.putExtra("servantSkillItem", servantSkillItem);
                intentTwo.putExtra("skillSelect", "2");
                intentTwo.putExtra("id", id);
                startActivity(intentTwo);
                break;
            case R.id.servant_info_skill_three_ll:
                Intent intentThree = new Intent(this, SkillActivity.class);
//                intentThree.putExtra("servantSkillItem", servantSkillItem);
                intentThree.putExtra("skillSelect", "3");
                intentThree.putExtra("id", id);
                startActivity(intentThree);
                break;

            case R.id.servant_info_treasure_video:
                boolean wifi = CommonUtils.isWifi(this);
                if (wifi) {
                    handler.postDelayed(runnable, 500);
                    setTreasureVideo();

                } else {
                    new MaterialDialog.Builder(getContext())
                            .title(R.string.person_init_db_title)
                            .titleColor(getResources().getColor(R.color.colorAccent))
                            .content(R.string.servant_wifi_video_content)
                            .positiveText(R.string.person_init_db_ok)
                            .negativeText(R.string.person_init_db_cancle)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    handler.postDelayed(runnable, 500);
                                    setTreasureVideo();
                                }
                            })
                            .show();
                }
                break;

            case R.id.hero_item_ll:
                Intent intentFour = new Intent(this, ServantSourceActivity.class);
                intentFour.putExtra("id", id);
                if ("Shielder".equals(servantItem.getClass_type())) {
                    intentFour.putExtra("isMaXiu", true);
                } else {
                    intentFour.putExtra("isMaXiu", false);
                }

                startActivity(intentFour);
                break;

            case R.id.servant_source_plane_ll:
                Intent intentFive = new Intent(this, ServantSourcePlanActivity.class);
                intentFive.putExtra("servantSkillItem", servantSkill);
                intentFive.putExtra("id", id);
                if ("Shielder".equals(servantItem.getClass_type())) {
                    intentFive.putExtra("isMaXiu", true);
                } else {
                    intentFive.putExtra("isMaXiu", false);
                }

                startActivity(intentFive);
                break;
        }
    }

    private void setTreasureVideo() {

        //设置宝具动画
        int resId = servantItem.getId();
        String id = CommonUtils.getId(resId);
        String url = "http://img.fgowiki.com/fgo/mp4/No." + id + ".mp4";
        Uri uri = Uri.parse(url);
        mTreasureVideo.setMediaController(new MediaController(this));
        mTreasureVideo.setVideoURI(uri);
        mTreasureVideo.start();
        mTreasureVideo.requestFocus();

    }


}

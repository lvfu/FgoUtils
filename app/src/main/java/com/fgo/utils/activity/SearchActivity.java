package com.fgo.utils.activity;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.king.frame.mvp.base.QuickActivity;
import com.fgo.utils.R;
import com.fgo.utils.adaper.HeroFragmentAdaper;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantSkill;
import com.fgo.utils.mvp.presenter.SearchPresenter;
import com.fgo.utils.mvp.view.SearchView;
import com.fgo.utils.ui.view.FundPopAdapter;
import com.fgo.utils.ui.view.FundSpinPopWindow;
import com.fgo.utils.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends QuickActivity<SearchView, SearchPresenter> implements SearchView, FundPopAdapter.ItemSelectListenner, View.OnClickListener, TextView.OnEditorActionListener {


    private LinearLayout mSearchSxLL, mSearchNameLL;
    private TextView mSearchSxClassType;
    private TextView mSearchSxStar;
    private TextView mSearchSxSort;
    private List<String> sortList = new ArrayList<>();
    private List<String> starList = new ArrayList<>();
    private List<String> classTypeList = new ArrayList<>();
    private List<String> sortValueList = new ArrayList<>();
    private List<Integer> starValueList = new ArrayList<>();

    private int width;
    private FundSpinPopWindow mSpinerPopSort;
    private FundSpinPopWindow mSpinerPopStar;
    private FundSpinPopWindow mSpinerPopSClassType;
    private boolean chooseClassType, chooseStar, chooseSort;
    private TextView mSearchTitle;
    private ImageView mSearchReplace;
    private RecyclerView mSearchRv;
    private EditText mSearchServant;
    private String keyWord;
    private HeroFragmentAdaper heroFragmentAdaper;
    private TextView mSearchServantTv;
    private String selectSortValue ;
    private int selectStarValue = -1;
    private String selectClassTypeValue ;
    private String selectStar;
    private String selectSort;

    @Override
    public int getRootViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initUI() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.google_red), 0);
        Display display = getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        mSearchTitle = findViewById(R.id.activity_search_title);
        mSearchReplace = findView(R.id.search_replace);

        mSearchNameLL = findViewById(R.id.search_searchname_la);
        mSearchSxLL = findViewById(R.id.search_sx_layout);
        //筛选
        mSearchSxClassType = findViewById(R.id.search_include_sx_class_type);
        mSearchSxStar = findViewById(R.id.search_include_sx_star);
        mSearchSxSort = findViewById(R.id.search_include_sx_sort);
        //sousuo
        mSearchServant = findViewById(R.id.activity_search_edit);

        mSearchServantTv = findViewById(R.id.activity_search_tv);
        //搜索rv
        mSearchRv = findViewById(R.id.search_servant_rv);
        //配置ll
        LinearLayoutManager ll = new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchRv.setLayoutManager(ll);


        setOnClick();
    }

    private void setOnClick() {
        mSearchSxClassType.setOnClickListener(this);
        mSearchSxStar.setOnClickListener(this);
        mSearchSxSort.setOnClickListener(this);
        mSearchReplace.setOnClickListener(this);
        mSearchServant.setOnEditorActionListener(this);
        mSearchServantTv.setOnClickListener(this);

    }

    @Override
    public void initData() {

        //设置筛选数据
        setClassTypeData();

        setStarData();

        setSortData();
    }

    private void setSortData() {

        String[] stringArray = getResources().getStringArray(R.array.sort);
        for (int i = 0; i < stringArray.length; i++) {
            sortList.add(stringArray[i]);
        }

        String[] stringValueArray = getResources().getStringArray(R.array.sort_value);
        for (int i = 0; i < stringValueArray.length; i++) {
            sortValueList.add(stringValueArray[i]);
        }

        mSpinerPopSort = new FundSpinPopWindow(this, stringArray.length);
        mSpinerPopSort.setWidth(width * 4 / 5);
        mSpinerPopSort.refreshData(sortList, 0);
        mSpinerPopSort.setItemListener(this);
        mSpinerPopSort.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失的时候恢复成原来的透明度
                backgroundAlpha(1f);
            }
        });
    }


    private void setStarData() {

        String[] stringArray = getResources().getStringArray(R.array.star);
        for (int i = 0; i < stringArray.length; i++) {
            starList.add(stringArray[i]);
        }

        int[] stringValueArray = getResources().getIntArray(R.array.star_value);
        for (int i = 0; i < stringValueArray.length; i++) {
            starValueList.add(stringValueArray[i]);
        }

        mSpinerPopStar = new FundSpinPopWindow(this, stringArray.length);
        mSpinerPopStar.setWidth(width * 4 / 5);
        mSpinerPopStar.refreshData(starList, 0);
        mSpinerPopStar.setItemListener(this);
        mSpinerPopStar.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失的时候恢复成原来的透明度
                backgroundAlpha(1f);
            }
        });
    }

    private void setClassTypeData() {

        String[] stringArray = getResources().getStringArray(R.array.classType);
        for (int i = 0; i < stringArray.length; i++) {
            classTypeList.add(stringArray[i]);
        }


        mSpinerPopSClassType = new FundSpinPopWindow(this, stringArray.length);
        mSpinerPopSClassType.setWidth(width * 4 / 5);
        mSpinerPopSClassType.refreshData(classTypeList, 0);
        mSpinerPopSClassType.setItemListener(this);
        mSpinerPopSClassType.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失的时候恢复成原来的透明度
                backgroundAlpha(1f);
            }
        });

    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @NonNull
    @Override
    public SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public void onItemClick(int position) {
        if (chooseClassType) {
            selectClassTypeValue = classTypeList.get(position);
            mSearchSxClassType.setText(selectClassTypeValue);
            mSearchSxClassType.setBackgroundResource(R.mipmap.btn_select_bg);
            mSearchSxClassType.setTextColor(Color.WHITE);

            mSearchSxSort.setText("排序");
            mSearchSxSort.setTextColor(getResources().getColor(R.color.fund_dialog_border_text_color));
            mSearchSxSort.setBackgroundResource(R.mipmap.no_select_bg);
            mSearchSxStar.setText("星级");
            mSearchSxStar.setTextColor(getResources().getColor(R.color.fund_dialog_border_text_color));
            mSearchSxStar.setBackgroundResource(R.mipmap.no_select_bg);

            mSpinerPopStar.setSelect(-1);
            mSpinerPopSort.setSelect(-1);

            //设置默认
            selectStarValue = -1;
            selectSortValue = "id,asc";

        }
        if (chooseStar) {
            selectStar = starList.get(position);
            selectStarValue = starValueList.get(position);
            mSearchSxStar.setText(selectStar);
            mSearchSxStar.setBackgroundResource(R.mipmap.btn_select_bg);
            mSearchSxStar.setTextColor(Color.WHITE);
            //设置默认
            if (TextUtils.isEmpty(selectSortValue)) {
                selectSortValue = "id,asc";
            }
        }
        if (chooseSort) {
            selectSort = sortList.get(position);
            selectSortValue = sortValueList.get(position);
            mSearchSxSort.setText(selectSort);
            mSearchSxSort.setBackgroundResource(R.mipmap.btn_select_bg);
            mSearchSxSort.setTextColor(Color.WHITE);
            //设置默认
            if (selectStarValue == -1){
                selectStarValue = -1;
            }


        }

        getPresenter().searchServantsByCondition(selectClassTypeValue, selectStarValue, selectSortValue);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_include_sx_class_type:
                chooseClassType = true;
                chooseStar = false;
                chooseSort = false;
                mSpinerPopSClassType.setWidth(width * 4 / 5);
                backgroundAlpha(0.7f);
                mSpinerPopSClassType.showAtDropDownCenter(mSearchTitle);
                mSpinerPopStar.dismiss();
                mSpinerPopSort.dismiss();

                break;
            case R.id.search_include_sx_star:
                if (mSearchSxClassType.getText().equals("职介")){
                    Toast.makeText(this, "请先选择阶职", Toast.LENGTH_SHORT).show();
                }else {
                    chooseClassType = false;
                    chooseStar = true;
                    chooseSort = false;
                    mSpinerPopStar.setWidth(width * 4 / 5);
                    backgroundAlpha(0.7f);
                    mSpinerPopStar.showAtDropDownCenter(mSearchTitle);
                    mSpinerPopSClassType.dismiss();
                    mSpinerPopSort.dismiss();
                }

                break;
            case R.id.search_include_sx_sort:
                if (mSearchSxClassType.getText().equals("职介")){
                    Toast.makeText(this, "请先选择阶职", Toast.LENGTH_SHORT).show();

                }else {
                    chooseClassType = false;
                    chooseStar = false;
                    chooseSort = true;
                    mSpinerPopSort.setWidth(width * 4 / 5);
                    backgroundAlpha(0.7f);
                    mSpinerPopSort.showAtDropDownCenter(mSearchTitle);
                    mSpinerPopStar.dismiss();
                    mSpinerPopSClassType.dismiss();
                }

                break;

            case R.id.search_replace:
                if (mSearchNameLL.getVisibility() == View.VISIBLE) {
                    mSearchNameLL.setVisibility(View.GONE);
                    mSearchSxLL.setVisibility(View.VISIBLE);
                    mSearchSxClassType.setText("职介");
                    mSearchSxClassType.setTextColor(getResources().getColor(R.color.fund_dialog_border_text_color));
                    mSearchSxClassType.setBackgroundResource(R.mipmap.no_select_bg);
                    mSearchSxSort.setText("星级");
                    mSearchSxSort.setTextColor(getResources().getColor(R.color.fund_dialog_border_text_color));
                    mSearchSxSort.setBackgroundResource(R.mipmap.no_select_bg);
                    mSearchSxStar.setText("排序");
                    mSearchSxStar.setTextColor(getResources().getColor(R.color.fund_dialog_border_text_color));
                    mSearchSxStar.setBackgroundResource(R.mipmap.no_select_bg);
                    mSpinerPopStar.setSelect(-1);
                    mSpinerPopSort.setSelect(-1);
                    mSpinerPopSClassType.setSelect(-1);
                } else {
                    mSearchNameLL.setVisibility(View.VISIBLE);
                    mSearchSxLL.setVisibility(View.GONE);
                }


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                if (heroFragmentAdaper != null) {
                    heroFragmentAdaper.setEmptyData();
                    heroFragmentAdaper.notifyDataSetChanged();
                }


                break;
            case R.id.activity_search_tv:
                keyWord = etValue(mSearchServant);
                getPresenter().searchServantsByKeyword(keyWord);
                break;

        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            // 先隐藏键盘
            ((InputMethodManager) mSearchServant.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

            keyWord = etValue(mSearchServant);
            getPresenter().searchServantsByKeyword(keyWord);
            return true;
        }
        return false;
    }

    public static String etValue(EditText et) {
        if (et.getText() == null) {
            return "";
        } else {
            return et.getText().toString().trim();
        }
    }

    @Override
    public void setServantList(List<ServantItem> servantList, List<ServantSkill> servantSkillList) {
        if (servantList == null || servantSkillList == null) {
            Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
            return;
        }
//        heroFragmentAdaper = new HeroFragmentAdaper(servantList, servantSkillList, getContext());
        mSearchRv.setAdapter(heroFragmentAdaper);
    }
}

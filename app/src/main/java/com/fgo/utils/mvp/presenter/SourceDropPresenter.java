package com.fgo.utils.mvp.presenter;

import com.fgo.utils.base.BaseRetrofitService;
import com.fgo.utils.base.retrofit.StringCallBack;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.mvp.view.HeroView;
import com.fgo.utils.mvp.view.SourceDropView;
import com.king.frame.mvp.base.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;


public class SourceDropPresenter extends BasePresenter<SourceDropView> {


    public void getSourceDropData(String position) {

        BaseRetrofitService.getInstance().getSourceDropData(position, new StringCallBack() {
            @Override
            public void onResponse(Response respBody) {
                BaseCommonBean body = (BaseCommonBean) respBody.body();
                getView().parseSouceDropData(body);
            }

            @Override
            public void onEroor(Call errorBody) {
                System.out.println("连接失败");
            }
        });
    }
}

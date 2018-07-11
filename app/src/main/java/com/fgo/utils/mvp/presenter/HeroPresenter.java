package com.fgo.utils.mvp.presenter;

import com.fgo.utils.base.BaseRetrofitService;
import com.fgo.utils.base.retrofit.StringCallBack;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantListNBean;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.HeroView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public class HeroPresenter extends BasePresenter<HeroView> {


    public void getServantList() {

        JSONObject jsonObjectData = new JSONObject();
        try {
            jsonObjectData.put("pageIndex", "1");
            jsonObjectData.put("pageSize", "20");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseRetrofitService.getInstance().getHeroListData(jsonObjectData, new StringCallBack<ServantListNBean>() {
            @Override
            public void onResponse(Response<BaseCommonBean<ServantListNBean>> respBody) {
                BaseCommonBean<ServantListNBean> body = respBody.body();
                getView().showServantList(body);
            }

            @Override
            public void onEroor(Call<BaseCommonBean<ServantListNBean>> errorBody) {
                System.out.println("连接失败");
            }
        });


    }
}

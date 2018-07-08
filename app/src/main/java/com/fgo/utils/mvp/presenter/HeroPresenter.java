package com.fgo.utils.mvp.presenter;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantListBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.bean.userBean;
import com.fgo.utils.constant.GlobalConstant;
import com.fgo.utils.face.GetRequest_Interface;
import com.google.gson.JsonObject;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.HeroView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public class HeroPresenter extends BasePresenter<HeroView> {


    public void getServantList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConstant.PUBLIC_URL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectData = new JSONObject();
        try {
            jsonObjectData.put("pageIndex", "1");
            jsonObjectData.put("pageSize", "20");

            jsonObject.put("version", "v1.1");
            jsonObject.put("bean", jsonObjectData);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());
        //对 发送请求 进行封装
        Call<BaseCommonBean<ServantListNBean>> call = request.getCall(body);

        call.enqueue(new Callback<BaseCommonBean<ServantListNBean>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<BaseCommonBean<ServantListNBean>> call, Response<BaseCommonBean<ServantListNBean>> response) {
                BaseCommonBean<ServantListNBean> body = response.body();
                getView().showServantList(body);
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<BaseCommonBean<ServantListNBean>> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }
}

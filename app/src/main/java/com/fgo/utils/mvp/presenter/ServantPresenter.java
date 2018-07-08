package com.fgo.utils.mvp.presenter;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantDetailBean;
import com.fgo.utils.bean.ServantItem;
import com.fgo.utils.bean.ServantListBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.constant.GlobalConstant;
import com.fgo.utils.face.GetRequest_Interface;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.IIPAddrView;
import com.fgo.utils.mvp.view.ServantView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lvfu on 2018/4/11.
 */

public class ServantPresenter extends BasePresenter<ServantView> {

    public void getServantData(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConstant.PUBLIC_URL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<BaseCommonBean<ServantDetailBean>> call = request.getServantDetail(id + "","v1.1");

        call.enqueue(new Callback<BaseCommonBean<ServantDetailBean>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<BaseCommonBean<ServantDetailBean>> call, Response<BaseCommonBean<ServantDetailBean>> response) {
                BaseCommonBean<ServantDetailBean> body = response.body();
                getView().showServantData(body);
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<BaseCommonBean<ServantDetailBean>> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }

}

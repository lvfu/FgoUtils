package com.fgo.utils.mvp.presenter;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.ServantAdvancedBean;
import com.fgo.utils.bean.ServantSkillBean;
import com.fgo.utils.constant.GlobalConstant;
import com.fgo.utils.face.GetRequest_Interface;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.ServantSourceView;
import com.fgo.utils.mvp.view.ServantView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lvfu on 2018/4/16.
 */

public class ServantSourcePresenter extends BasePresenter<ServantSourceView> {
    public void getServantAdvanced(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConstant.PUBLIC_URL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<BaseCommonBean<ServantAdvancedBean>> call = request.getServantAdvanced(id + "", "v1.1");

        call.enqueue(new Callback<BaseCommonBean<ServantAdvancedBean>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<BaseCommonBean<ServantAdvancedBean>> call, Response<BaseCommonBean<ServantAdvancedBean>> response) {
                BaseCommonBean<ServantAdvancedBean> body = response.body();
                getView().showServantAdvancedData(body);
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<BaseCommonBean<ServantAdvancedBean>> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }
}

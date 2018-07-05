package com.fgo.utils.mvp.presenter;

import com.fgo.utils.bean.userBean;
import com.fgo.utils.constant.GlobalConstant;
import com.fgo.utils.face.GetRequest_Interface;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.HeroView;
import com.fgo.utils.mvp.view.PersonView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public class PersonPresenter extends BasePresenter<PersonView> {
    //网络请求
    public void request() {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(GlobalConstant.PUBLIC_URL) // 设置 网络请求 Url
//                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
//                .build();
//
//        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//
//        //对 发送请求 进行封装
//        Call<userBean> call = request.getCall();
//
//        call.enqueue(new Callback<userBean>() {
//            //请求成功时回调
//            @Override
//            public void onResponse(Call<userBean> call, Response<userBean> response) {
//                userBean body = response.body();
//                getView().showData(body);
//            }
//
//            //请求失败时回调
//            @Override
//            public void onFailure(Call<userBean> call, Throwable throwable) {
//                System.out.println("连接失败");
//            }
//        });
    }


}

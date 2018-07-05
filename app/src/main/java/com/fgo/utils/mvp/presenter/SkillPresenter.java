package com.fgo.utils.mvp.presenter;

import com.fgo.utils.bean.ServantDetailBean;
import com.fgo.utils.bean.ServantSkillBean;
import com.fgo.utils.constant.GlobalConstant;
import com.fgo.utils.face.GetRequest_Interface;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.ServantView;
import com.fgo.utils.mvp.view.SkillView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lvfu on 2018/4/11.
 */

public class SkillPresenter extends BasePresenter<SkillView> {
    /**
     * 获取技能数据
     */
    public void getServantSkillData(int id, String skill) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConstant.PUBLIC_URL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<ServantSkillBean> call = request.getServantSkill(id, skill);

        call.enqueue(new Callback<ServantSkillBean>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<ServantSkillBean> call, Response<ServantSkillBean> response) {
                ServantSkillBean body = response.body();
                getView().showServantSkillData(body);
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ServantSkillBean> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }
}

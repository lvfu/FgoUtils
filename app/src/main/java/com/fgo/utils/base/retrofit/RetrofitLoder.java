package com.fgo.utils.base.retrofit;

import com.fgo.utils.bean.BaseCommonBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitLoder<T> {
    public static RetrofitLoder retrofitLoder;

    public static RetrofitLoder getInstance() {
        if (retrofitLoder == null) {
            return retrofitLoder = new RetrofitLoder();
        }

        return retrofitLoder;
    }

    public void post(Call<BaseCommonBean<T>> call, final StringCallBack callBack) {

        call.enqueue(new Callback<BaseCommonBean<T>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<BaseCommonBean<T>> call, Response<BaseCommonBean<T>> response) {
                callBack.onResponse(response);

            }

            //请求失败时回调
            @Override
            public void onFailure(Call<BaseCommonBean<T>> call, Throwable throwable) {
                callBack.onEroor(call);

            }
        });
    }
}

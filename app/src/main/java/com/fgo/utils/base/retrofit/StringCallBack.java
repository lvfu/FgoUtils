package com.fgo.utils.base.retrofit;

import com.fgo.utils.bean.BaseCommonBean;

import retrofit2.Call;
import retrofit2.Response;


public interface  StringCallBack<T> {

     void onResponse(Response<BaseCommonBean<T>> respBody);

     void onEroor(Call<BaseCommonBean<T>> errorBody);

}

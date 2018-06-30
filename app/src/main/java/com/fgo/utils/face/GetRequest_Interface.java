package com.fgo.utils.face;

import com.fgo.utils.bean.userBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest_Interface {
    //http://47.96.105.170:8080/login_war/Servlet?username=jk&password=ll
    @GET("login_war/Servlet?username=jk&password=ll")
    Call<userBean> getCall();
}

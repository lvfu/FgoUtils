package com.fgo.utils.face;

import com.fgo.utils.bean.ServantAdvancedBean;
import com.fgo.utils.bean.ServantDetailBean;
import com.fgo.utils.bean.ServantListBean;
import com.fgo.utils.bean.ServantSkillBean;
import com.fgo.utils.bean.userBean;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface GetRequest_Interface {
    //http://47.96.105.170:8080/login_war/Servlet?username=jk&password=ll
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/servant/servantList")
    Call<ServantListBean> getCall(@Body RequestBody body);

    /**
     * 英灵详情
     *
     * @param id
     * @return
     */
    @GET("fate/servant/servantDetail")
    Call<ServantDetailBean> getServantDetail(@Query("id") String id);

    /**
     * 英灵技能
     */
    @GET("fate/skill/servantskill")
    Call<ServantSkillBean> getServantSkill(@Query("id") int id, @Query("skill") String skill);

    /**
     * 英灵进阶
     */

    @GET("fate/servant/servantAdvance")
    Call<ServantAdvancedBean> getServantAdvanced(@Query("id") String id);
}

package com.fgo.utils.face;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.LoginBean;
import com.fgo.utils.bean.ServantAdvancedBean;
import com.fgo.utils.bean.ServantDetailBean;
import com.fgo.utils.bean.ServantListBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.bean.ServantSkillBean;
import com.fgo.utils.bean.ServantSkillPlanBean;
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
    Call<BaseCommonBean<ServantListNBean>> getCall(@Body RequestBody body);

    /**
     * 英灵详情
     *
     * @param id
     * @return
     */
    @GET("fate/servant/servantDetail")
    Call<BaseCommonBean<ServantDetailBean>> getServantDetail(@Query("id") String id, @Query("version") String version);

    /**
     * 英灵技能
     */
    @GET("fate/skill/servantskill")
    Call<BaseCommonBean<ServantSkillBean>> getServantSkill(@Query("id") int id, @Query("skill") String skill, @Query("version") String version);

    /**
     * 英灵进阶
     */

    @GET("fate/servant/servantAdvance")
    Call<BaseCommonBean<ServantAdvancedBean>> getServantAdvanced(@Query("id") String id, @Query("version") String version);

    /**
     * 筛选
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/search/servantfliter")
    Call<BaseCommonBean<ServantListNBean>> getServantFliter(@Body RequestBody body);

    /**
     * 搜索名字
     *
     * @param
     * @return
     */

    @GET("fate/search/servantsearch")
    Call<BaseCommonBean<ServantListNBean>> getServantForName(@Query("keyword") String keyword, @Query("version") String version);

    /**
     * 注册
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/user/regeist")
    Call<BaseCommonBean> getRegeistData(@Body RequestBody body);

    /**
     * 注册
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/user/login")
    Call<BaseCommonBean<LoginBean>> getLoginData(@Body RequestBody body);

    /**
     * 获取英灵技能素材
     *
     * @param
     * @return
     */

    @GET("fate/servantskill/skillplan")
    Call<BaseCommonBean<ServantSkillPlanBean>> getServantSouceList(@Query("id") int id, @Query("userId") int userId, @Query("version") String version);


    /**
     * 设置技能素材
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/servantskill/skillsource")
    Call<BaseCommonBean> setServantsSourceData(@Body RequestBody body);
}

package com.fgo.utils.face;

import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.FeedBackListBean;
import com.fgo.utils.bean.LoginBean;
import com.fgo.utils.bean.ServantAdvancedBean;
import com.fgo.utils.bean.ServantDetailBean;
import com.fgo.utils.bean.ServantListBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.bean.ServantSkillBean;
import com.fgo.utils.bean.ServantSkillPlanBean;
import com.fgo.utils.bean.SourceDropBean;
import com.fgo.utils.bean.SourcesPlanBean;
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
    @POST("fate/Servant/ServantList")
    Call<BaseCommonBean<ServantListNBean>> getCall(@Body RequestBody body);

    /**
     * 英灵详情
     *
     * @param id
     * @return
     */
    @GET("fate/Servant/ServantDetail")
    Call<BaseCommonBean<ServantDetailBean>> getServantDetail(@Query("id") String id, @Query("version") String version);

    /**
     * 英灵技能
     */
    @GET("fate/Skill/ServantSkill")
    Call<BaseCommonBean<ServantSkillBean>> getServantSkill(@Query("id") int id, @Query("skill") String skill, @Query("version") String version);

    /**
     * 英灵进阶
     */

    @GET("fate/Servant/ServantAdvance")
    Call<BaseCommonBean<ServantAdvancedBean>> getServantAdvanced(@Query("id") String id, @Query("version") String version);

    /**
     * 筛选
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/Search/ServantFliter")
    Call<BaseCommonBean<ServantListNBean>> getServantFliter(@Body RequestBody body);

    /**
     * 搜索名字
     *
     * @param
     * @return
     */

    @GET("fate/Search/ServantSearch")
    Call<BaseCommonBean<ServantListNBean>> getServantForName(@Query("keyword") String keyword, @Query("version") String version);

    /**
     * 注册
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/User/Regeist")
    Call<BaseCommonBean> getRegeistData(@Body RequestBody body);

    /**
     * 注册
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/User/Login")
    Call<BaseCommonBean<LoginBean>> getLoginData(@Body RequestBody body);

    /**
     * 获取英灵技能素材
     *
     * @param
     * @return
     */

    @GET("fate/ServantSkill/SkillPlan")
    Call<BaseCommonBean<ServantSkillPlanBean>> getServantSouceList(@Query("id") int id, @Query("userId") int userId, @Query("version") String version);


    /**
     * 设置技能素材
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/ServantSkill/SkillSource")
    Call<BaseCommonBean> setServantsSourceData(@Body RequestBody body);

    /**
     * 素材列表
     *
     * @param userId
     * @param version
     * @return
     */
    @GET("fate/SourcesPlan/SourceCount")
    Call<BaseCommonBean<SourcesPlanBean>> getSourceList(@Query("userId") int userId, @Query("version") String version);

    /**
     * 设置素材数量
     *
     * @param userId
     * @param version
     * @return
     */
    @GET("fate/SourcesPlan/SourceOperate")
    Call<BaseCommonBean<SourcesPlanBean>> insertSourceCount(@Query("userId") int userId, @Query("sourceCount") int sourceCount, @Query("sourceName") String sourceName, @Query("version") String version);

    /**
     * 素材掉落
     * @param position
     * @param version
     * @return
     */
    @GET("fate/SourcesPlan/SourceDrop")
    Call<BaseCommonBean<SourceDropBean>> getSourceDropData(@Query("position") String position, @Query("version") String version);

    /**
     * 交流列表
     * @param version
     * @return
     */
    @GET("fate/FeedBack/List")
    Call<BaseCommonBean<FeedBackListBean>> getFeedBackData(@Query("version") String version);

    /**
     * 发表交流
     *
     * @param body
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("fate/FeedBack/InsertData")
    Call<BaseCommonBean> insertFeedBackData(@Body RequestBody body);

}

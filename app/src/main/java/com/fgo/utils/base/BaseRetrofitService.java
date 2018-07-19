package com.fgo.utils.base;

import com.fgo.utils.base.retrofit.RetrofitLoder;
import com.fgo.utils.base.retrofit.StringCallBack;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.LoginBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.bean.ServantSkillPlanBean;
import com.fgo.utils.constant.GlobalConstant;
import com.fgo.utils.face.GetRequest_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofitService {

    public static BaseRetrofitService baseRetrofitService;
    private final Retrofit retrofit;
    private final GetRequest_Interface request;

    /**
     * 版本号
     */
    public static final String VERSION_1_1 = "v1.1";


    public static BaseRetrofitService getInstance() {

        if (baseRetrofitService == null) {
            return baseRetrofitService = new BaseRetrofitService();
        }
        return baseRetrofitService;
    }

    /**
     * 初始化retrofit
     */
    public BaseRetrofitService() {

        // 设置 网络请求 Url
        //设置使用Gson解析(记得加入依赖)
        retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConstant.PUBLIC_URL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        request = retrofit.create(GetRequest_Interface.class);

    }

    //servant列表
    public void getHeroListData(JSONObject jsonObjectData, final StringCallBack callBack) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("version", VERSION_1_1);
            jsonObject.put("bean", jsonObjectData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());

        //对 发送请求 进行封装
        Call<BaseCommonBean<ServantListNBean>> call = request.getCall(body);

        RetrofitLoder.getInstance().post(call, callBack);
    }


    //regeist
    public void getRegeistData(JSONObject jsonObjectData, final StringCallBack callBack) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("version", VERSION_1_1);
            jsonObject.put("bean", jsonObjectData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());

        //对 发送请求 进行封装
        Call<BaseCommonBean> call = request.getRegeistData(body);

        RetrofitLoder.getInstance().post(call, callBack);
    }

    //login

    public void getLoginData(JSONObject jsonObjectData, final StringCallBack callBack) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("version", VERSION_1_1);
            jsonObject.put("bean", jsonObjectData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());

        //对 发送请求 进行封装
        Call<BaseCommonBean<LoginBean>> call = request.getLoginData(body);

        RetrofitLoder.getInstance().post(call, callBack);
    }


    //servantSouce
    public void getServantSouceList(int id, int userId, final StringCallBack callBack) {

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<BaseCommonBean<ServantSkillPlanBean>> call = request.getServantSouceList(id, userId, VERSION_1_1);

        RetrofitLoder.getInstance().post(call, callBack);
    }

    /**
     * 设置技能素材
     *
     * @param
     */
    public void setServantsSourceData(JSONObject jsonObjectData, final StringCallBack callBack) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("version", VERSION_1_1);
            jsonObject.put("bean", jsonObjectData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());

        //对 发送请求 进行封装
        Call<BaseCommonBean> call = request.setServantsSourceData(body);

        RetrofitLoder.getInstance().post(call, callBack);
    }


}

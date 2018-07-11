package com.fgo.utils.mvp.presenter;

import com.fgo.utils.base.BaseRetrofitService;
import com.fgo.utils.base.retrofit.StringCallBack;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.LoginBean;
import com.fgo.utils.bean.ServantListNBean;
import com.fgo.utils.bean.userBean;
import com.fgo.utils.constant.GlobalConstant;
import com.fgo.utils.face.GetRequest_Interface;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.HeroView;
import com.fgo.utils.mvp.view.PersonView;

import org.json.JSONException;
import org.json.JSONObject;

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

    //注册
    public void RegeistData(String nickname, String username, String password) {
        JSONObject jsonObjectData = new JSONObject();
        try {
            jsonObjectData.put("userName", username);
            jsonObjectData.put("passWord", password);
            jsonObjectData.put("userIcon", "img");
            jsonObjectData.put("name", nickname);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseRetrofitService.getInstance().getRegeistData(jsonObjectData, new StringCallBack() {
            @Override
            public void onResponse(Response respBody) {
                BaseCommonBean body = (BaseCommonBean) respBody.body();
                getView().showRegeistData(body);
            }

            @Override
            public void onEroor(Call errorBody) {
                System.out.println("连接失败");
            }
        });
    }

    //登陆
    public void LoginData(String usernameL, String passwordL) {
        JSONObject jsonObjectData = new JSONObject();
        try {
            jsonObjectData.put("userName", usernameL);
            jsonObjectData.put("passWord", passwordL);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseRetrofitService.getInstance().getLoginData(jsonObjectData, new StringCallBack() {
            @Override
            public void onResponse(Response respBody) {
                BaseCommonBean<LoginBean> body = (BaseCommonBean<LoginBean>) respBody.body();
                getView().showLoginData(body);
            }

            @Override
            public void onEroor(Call errorBody) {
                System.out.println("连接失败");
            }
        });
    }
}

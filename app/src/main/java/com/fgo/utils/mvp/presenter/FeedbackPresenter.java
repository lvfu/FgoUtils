package com.fgo.utils.mvp.presenter;

import com.fgo.utils.base.BaseRetrofitService;
import com.fgo.utils.base.retrofit.StringCallBack;
import com.fgo.utils.bean.BaseCommonBean;
import com.fgo.utils.bean.FeedBackListBean;
import com.fgo.utils.bean.LoginBean;
import com.fgo.utils.bean.ServantListNBean;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.FeedbackView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;


public class FeedbackPresenter extends BasePresenter<FeedbackView> {

    public void getFeedBackData() {

        BaseRetrofitService.getInstance().getFeedBackData(new StringCallBack<FeedBackListBean>() {
            @Override
            public void onResponse(Response<BaseCommonBean<FeedBackListBean>> respBody) {
                BaseCommonBean<FeedBackListBean> body = respBody.body();
                getView().showFeedBackData(body);
            }

            @Override
            public void onEroor(Call<BaseCommonBean<FeedBackListBean>> errorBody) {
                System.out.println("连接失败");
            }
        });
    }

    public void insertFeedBackData(int userId, String nickname, String content, String time) {

        JSONObject jsonObjectData = new JSONObject();
        try {
            jsonObjectData.put("userId", userId);
            jsonObjectData.put("name", nickname);
            jsonObjectData.put("content", content);
            jsonObjectData.put("time", time);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseRetrofitService.getInstance().insertFeedBackData(jsonObjectData, new StringCallBack() {
            @Override
            public void onResponse(Response respBody) {
                BaseCommonBean body = (BaseCommonBean) respBody.body();
                getView().showInsertResult(body);
            }

            @Override
            public void onEroor(Call errorBody) {
                System.out.println("连接失败");
            }
        });

    }
}

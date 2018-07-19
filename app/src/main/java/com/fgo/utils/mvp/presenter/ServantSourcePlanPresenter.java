package com.fgo.utils.mvp.presenter;

import com.fgo.utils.base.BaseRetrofitService;
import com.fgo.utils.base.retrofit.StringCallBack;
import com.fgo.utils.bean.BaseCommonBean;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.mvp.view.ServantSourcePlanView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lvfu on 2018/4/16.
 */

public class ServantSourcePlanPresenter extends BasePresenter<ServantSourcePlanView> {

    public void getServantSouceList(int id, int userId) {

        BaseRetrofitService.getInstance().getServantSouceList(id, userId, new StringCallBack() {
            @Override
            public void onResponse(Response respBody) {
                BaseCommonBean body = (BaseCommonBean) respBody.body();
                getView().showServantsSouceData(body);
            }

            @Override
            public void onEroor(Call errorBody) {
                System.out.println("连接失败");
            }
        });
    }

    /**
     * 设置技能素材
     * @param jsonObject
     */
    public void setServantsSourceData(JSONObject jsonObject) {
        BaseRetrofitService.getInstance().setServantsSourceData(jsonObject, new StringCallBack() {
            @Override
            public void onResponse(Response respBody) {
                BaseCommonBean body = (BaseCommonBean) respBody.body();
                getView().showInsertSouceData(body);
            }

            @Override
            public void onEroor(Call errorBody) {
                System.out.println("连接失败");
            }
        });
    }
}

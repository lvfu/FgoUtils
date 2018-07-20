package com.fgo.utils.mvp.presenter;

import android.content.Context;
import android.database.Cursor;

import com.fgo.utils.base.BaseRetrofitService;
import com.fgo.utils.base.retrofit.StringCallBack;
import com.fgo.utils.bean.BaseCommonBean;
import com.king.frame.mvp.base.BasePresenter;
import com.fgo.utils.bean.SourcePlanBean;
import com.fgo.utils.mvp.view.SourcePlanView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @date 2017/7/5
 */

public class SourcePlanPresenter extends BasePresenter<SourcePlanView> {

    /**
     * 素材列表
     * @param userId
     */
    public void initSourceList(int userId) {
        BaseRetrofitService.getInstance().getSourceList(userId, new StringCallBack() {
            @Override
            public void onResponse(Response respBody) {
                BaseCommonBean body = (BaseCommonBean) respBody.body();
                getView().parseSouceListData(body);
        }

            @Override
            public void onEroor(Call errorBody) {
                System.out.println("连接失败");
            }
        });
    }
}

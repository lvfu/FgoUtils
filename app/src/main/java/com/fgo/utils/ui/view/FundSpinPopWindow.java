package com.fgo.utils.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.fgo.utils.R;
import com.fgo.utils.bean.ProductCategroyBean;
import com.fgo.utils.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

/**
 * 资金管理年月日列表的显示
 */
public class FundSpinPopWindow extends PopupWindow implements AdapterView.OnItemClickListener {

    // 用于保存PopupWindow的宽度
    private int width;
    // 用于保存PopupWindow的高度
    private int height;
    private Context mContext;
    private ListView mListView;
    private FundPopAdapter mAdapter;

    public FundSpinPopWindow(Context context, int count) {

        super(context);
        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_write_fund_layout, null);
        setContentView(view);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);

        //设置popwindow背景
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);

        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setDivider(null);
        mAdapter = new FundPopAdapter(mContext);
        mListView.setAdapter(mAdapter);
        ViewGroup.LayoutParams params = mListView.getLayoutParams();

        if (count >= 6) {
            count = 6;
        }

        float a = 50;
        int f = CommonUtils.dip2px(context, a) * count;
        params.height = f;

        mListView.setLayoutParams(params);
        mListView.setOnItemClickListener(this);
        mandatoryDraw();
    }


    private FundPopAdapter.ItemSelectListenner mItemSelectListener;

    public void setItemListener(FundPopAdapter.ItemSelectListenner listener) {
        mItemSelectListener = listener;
    }

    /**
     * 强制绘制popupWindowView，并且初始化popupWindowView的尺寸
     */
    private void mandatoryDraw() {
        this.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        /**
         * 强制刷新后拿到PopupWindow的宽高
         */
        this.width = this.getContentView().getMeasuredWidth();
        this.height = this.getContentView().getMeasuredHeight();
    }


    public void refreshData(List<String> list, int selIndex) {
        if (list != null && selIndex != -1) {
            mAdapter.refreshData(list, selIndex);
        }
    }

    public void refreshsData(List<ProductCategroyBean> list, int selIndex) {
        if (list != null && selIndex != -1) {
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                stringList.add(list.get(i).getCategoryName());
            }
            mAdapter.refreshData(stringList, selIndex);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 显示在控件的下中方
     *
     * @param parent parent
     */
    public void showAtDropDownCenter(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        dismiss();
        if (mItemSelectListener != null) {
            mItemSelectListener.onItemClick(position);
            mAdapter.setSelectItem(position);
        }
    }

    public void setSelect(int position) {
        if (mAdapter != null) {
            mAdapter.setSelectItem(position);
        }
    }
}

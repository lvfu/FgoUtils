package com.fgo.utils.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.fgo.utils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

/**
 * 资金选择年月份的适配器
 */
public class FundPopAdapter extends BaseAdapter {

    private int position = -1;

    public void setSelectItem(int position) {
        this.position = position;
    }

    public interface ItemSelectListenner {
         void onItemClick(int position);
    }

    private Context mContext;
    private List<String> itemList = new ArrayList<>();
    private int mSelectItem = 0;
    private LayoutInflater mInflater;

    public FundPopAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void refreshData(List<String> list, int index) {
        itemList = list;
        if (index < 0) {
            index = 0;
        }
        if (index >= itemList.size()) {
            index = itemList.size() - 1;
        }
        mSelectItem = index;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if (convertView == null) {
            mViewHolder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.spiner_fund_item_layout, parent,false);
            mViewHolder.mTextview = (TextView) convertView.findViewById(R.id.textView);
            mViewHolder.mIvarrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            mViewHolder.line =  convertView.findViewById(R.id.line);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        String item = (String) getItem(position);
        mViewHolder.mTextview.setText(item);
        if (position == 0)
        {
            mViewHolder.line.setVisibility(View.INVISIBLE);
        }else {
            mViewHolder.line.setVisibility(View.VISIBLE);
        }
        if (this.position == position) {
            mViewHolder.mTextview.setTextColor(Color.parseColor("#f64747"));
            mViewHolder.mIvarrow.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mTextview.setTextColor(Color.parseColor("#8A000000"));
            mViewHolder.mIvarrow.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public static class ViewHolder {
        private TextView mTextview;
        private ImageView mIvarrow;
        private View line;
    }
}

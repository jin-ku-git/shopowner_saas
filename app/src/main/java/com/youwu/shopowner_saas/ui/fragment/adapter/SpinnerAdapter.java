package com.youwu.shopowner_saas.ui.fragment.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youwu.shopowner_saas.R;

import com.youwu.shopowner_saas.ui.fragment.bean.SpinnerBean;



import java.util.List;


/**
 * 2023/02/24
 */

public class SpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private List<SpinnerBean> mList;

    int type=0;

    RelativeLayout layout_top;
    public SpinnerAdapter(Context context, List<SpinnerBean> goodsEntityList) {
        mContext = context;
        this.mList = goodsEntityList;//实体类数据ArrayList
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 针对convertView做一个简单的优化
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_layout, null);
        }
        TextView name = convertView.findViewById(R.id.name);
        layout_top=convertView.findViewById(R.id.layout_top);
        if (position==0&&type==0){

            layout_top.setBackgroundColor(mContext.getResources().getColor(R.color.null_gray));
            name.setTextColor(mContext.getResources().getColor(R.color.gray_a9));
            layout_top.setOnClickListener(null);
        }
        ImageView  duigou =convertView.findViewById(R.id.duigou);
        if (mList.get(position).isSelect()){
            layout_top.setBackgroundColor(mContext.getResources().getColor(R.color.main_yellow));

        }else {
            layout_top.setBackgroundColor(mContext.getResources().getColor(R.color.main_touming));

        }


        name.setText(mList.get(position).getName());

        return convertView;
    }
}

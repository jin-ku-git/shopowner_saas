package com.youwu.shopowner_saas.ui.zaocan.adapter;


import static me.goldze.mvvmhabit.base.BaseActivity.subZeroAndDot;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.ui.zaocan.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 店铺详情的adapter
 * 因为使用的是ExpandableListView，所以继承BaseExpandableListAdapter
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {


    private Context mContext;
    private static List<GoodsBean.GoodsListBean> mList;
    Intent intent;


    public GoodsAdapter(Context context, List<GoodsBean.GoodsListBean> list, int position) {
        mContext = context;
        mList = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.goods_name.setText(mList.get(position).getGoods_name());


    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView goods_name;//商品名称
        TextView total_package_num;//商品数量


        ViewHolder(View view) {
            super(view);
            goods_name=view.findViewById(R.id.goods_name);
            total_package_num=view.findViewById(R.id.total_package_num);





//            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(view, mList);
                    }
                }
            });
        }
    }
    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, List<GoodsBean.GoodsListBean> data);
    }
    //需要外部访问，所以需要设置set方法，方便调用
    private static OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

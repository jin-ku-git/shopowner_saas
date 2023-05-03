package com.youwu.shopowner_saas.ui.zaocan.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.ui.zaocan.bean.GoodsTotalInfo;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * 店铺详情的adapter
 * 因为使用的是ExpandableListView，所以继承BaseExpandableListAdapter
 */
public class AllGoodsAdapter extends RecyclerView.Adapter<AllGoodsAdapter.ViewHolder> {

    //新增itemType
    public static final int ITEM_TYPE = 100;

    private Context mContext;
    private static List<GoodsTotalInfo> mList;
    Intent intent;
    String panduanzhi="";

    public AllGoodsAdapter(Context context, List<GoodsTotalInfo> list) {
        mContext = context;
        mList = list;
    }

    //重写改方法，设置ItemViewType
    @Override
    public int getItemViewType(int position) {
        //返回值与使用时设置的值需保持一致
        return ITEM_TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_ding, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GoodsTotalInfo GoodsTotalInfo=mList.get(position);


        holder.tv_position.setText(position+1+"");
        holder.goodsName.setText(GoodsTotalInfo.getGoodsName());
        holder.tv_number.setText(GoodsTotalInfo.getNumber()+"");



    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_position;//序号
        TextView goodsName;//商品名称
        TextView tv_number;//商品数量




        ViewHolder(View view) {
            super(view);
            tv_position=view.findViewById(R.id.tv_position);
            goodsName=view.findViewById(R.id.goodsName);
            tv_number=view.findViewById(R.id.tv_number);




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
        public void OnItemClick(View view, List<GoodsTotalInfo> data);
    }
    //需要外部访问，所以需要设置set方法，方便调用
    private static OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

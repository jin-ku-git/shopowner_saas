package com.youwu.shopowner_saas.ui.goods_operate.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.utils_view.CustomRoundAngleImageView;

import java.util.Collections;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 商品排序适配器
 */
public class GoodsDragAdapter extends RecyclerView.Adapter<GoodsDragAdapter.myViewHodler> {
    private Context context;
    private List<String> mList;
    private int currentIndex = 0;

    //创建构造函数
    public GoodsDragAdapter(Context context, List<String> goodsEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.mList = goodsEntityList;//实体类数据ArrayList
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public GoodsDragAdapter.myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
//        View itemView = View.inflate(context, R.layout.adapter_drag_touch_list_item, null);
        View itemView =  LayoutInflater.from(context).inflate(R.layout.adapter_goods_drag_item, parent, false);
        return new GoodsDragAdapter.myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final GoodsDragAdapter.myViewHodler holder, @SuppressLint("RecyclerView") final int position) {

        //根据点击位置绑定数据
        String data = mList.get(position);
        holder.goods_name.setText(data);
        if (position==0){
            holder.ll_top.setVisibility(View.GONE);
        }

        //置顶
        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mClickListener!=null){
//                    mClickListener.onClick(data,position);
//                }
                mList.remove(data);
                mList.add(0,data);
                notifyDataSetChanged();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxToast.normal("第"+position+"个数据");
            }
        });


    }


    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        notifyDataSetChanged();
    }

    /**
     * List拖拽移动
     *
     * @param srcHolder
     * @param targetHolder
     * @return
     */
    public boolean onMoveItem(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
        return onMoveItemList(srcHolder, targetHolder);
    }

    /**
     * List拖拽移动
     *
     * @param srcHolder
     * @param targetHolder
     */
    public boolean onMoveItemList(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
        KLog.i("111111111111111111");
        // 不同的ViewType不能拖拽换位置。
        if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) {
            return false;
        }
        int fromPosition = srcHolder.getAdapterPosition();
        int toPosition = targetHolder.getAdapterPosition();

        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        // 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
        return true;
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {



        private TextView goods_name,goods_price;//商品名称,商品价格

        private CustomRoundAngleImageView goods_image;//商品图片

        private TextView MonthSell,stock;//月售/库存

        private LinearLayout ll_top;




        public myViewHodler(View itemView) {
            super(itemView);



            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            goods_price = (TextView) itemView.findViewById(R.id.goods_price);

            goods_image =  itemView.findViewById(R.id.goods_image);

            MonthSell =  itemView.findViewById(R.id.MonthSell);
            stock =  itemView.findViewById(R.id.stock);
            ll_top=itemView.findViewById(R.id.ll_top);



            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理

                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, mList.get(getLayoutPosition()),getLayoutPosition());
                    }
                }
            });
        }


    }


    //按钮的监听的回调
    public interface OnClickListener {
        void onClick(String lists, int position);
    }

    public void setOnClickListener(GoodsDragAdapter.OnClickListener listener) {
        mClickListener = listener;
    }

    private GoodsDragAdapter.OnClickListener mClickListener;

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
        public void OnItemClick(View view, String data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private GoodsDragAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(GoodsDragAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * 第几个
     *
     * @param srcHolder
     */
    public int onGetItem(RecyclerView.ViewHolder srcHolder) {
        int position = srcHolder.getAdapterPosition();
        return position;
    }
    /**
     * 侧滑删除
     *
     * @param srcHolder
     */
    public int onRemoveItem(RecyclerView.ViewHolder srcHolder) {
        int position = srcHolder.getAdapterPosition();
        mList.remove(position);

        return position;
    }

}
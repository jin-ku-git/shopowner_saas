package com.youwu.shopowner_saas.ui.fragment.adapter;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.youwu.shopowner_saas.app.AppApplication.subZeroAndDot;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;
import com.youwu.shopowner_saas.ui.order_goods.ApplyReturnGoodsActivity;
import com.youwu.shopowner_saas.utils_view.AnimationUtils;
import com.youwu.shopowner_saas.utils_view.CountDownView;
import com.youwu.shopowner_saas.utils_view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 售后信息适配器
 */
public class ShouHouOrderAdapter extends RecyclerView.Adapter<ShouHouOrderAdapter.myViewHodler> {
    private Context context;
    private List<OrderBean.OrderRefundBean> mList;
    private int currentIndex = 0;
    private int type;


    //创建构造函数
    public ShouHouOrderAdapter(Context context, List<OrderBean.OrderRefundBean> goodsEntityList, int type) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.mList = goodsEntityList;//实体类数据ArrayList
        this.type = type;//状态 0 全部 1、待接单 2、待出餐
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.item_shou_hou_order_layout, null);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final myViewHodler holder, @SuppressLint("RecyclerView") final int position) {

        //根据点击位置绑定数据
        OrderBean.OrderRefundBean data = mList.get(position);




        renewView(data,holder,position);

        if (data.getStatus()==1){//待审核
            holder.tv_refuse.setVisibility(View.VISIBLE);
            holder.tv_agree.setVisibility(View.VISIBLE);
        }else {
            holder.tv_refuse.setVisibility(View.GONE);
            holder.tv_agree.setVisibility(View.GONE);
        }
        if (data.getStatus()==3){
            holder.refuse_Layout.setVisibility(View.VISIBLE);
        }


        holder.tv_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener!=null){
                    mClickListener.onClick(data,position,5);
                }

            }
        });
        holder.tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener!=null){
                    mClickListener.onClick(data,position,6);
                }


            }
        });


    }

    private void renewView(OrderBean.OrderRefundBean data, myViewHodler holder,int position) {


        if (data!=null){


            holder.RefundMethod.setText(data.getType_name());
            holder.guke_refund_name.setText("顾客申请"+data.getType_name());
            holder.refund_total_price.setText(data.getTotal_price()+"");
            holder.refund_time.setText(data.getCreated_at());
            holder.refund_mark.setText(data.getRefund_reason()+"；"+data.getMark());
            holder.refuse_mark.setText(data.getRemark());
            /*
         1.把内部RecyclerView的adapter和集合数据通过holder缓存
         2.使内部RecyclerView的adapter提供设置position的方法
         */
            holder.RefundList.clear();
            holder.RefundList.addAll(mList.get(position).getRefund_goods());

            if (holder.refundOrderDetailsAdapter == null) {
                holder.refundOrderDetailsAdapter = new RefundOrderDetailsAdapter(context, holder.RefundList,position);
                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
                holder.ReturnGoodsRecyclerview.setLayoutManager(layoutManage);
                holder.ReturnGoodsRecyclerview.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
                holder.ReturnGoodsRecyclerview.setAdapter(holder.refundOrderDetailsAdapter);

            } else {
                holder.refundOrderDetailsAdapter.setPosition(position);
                holder.refundOrderDetailsAdapter.notifyDataSetChanged();
            }

            //图片列表
            if (holder.refundImageAdapter == null) {
                holder.refundImageAdapter = new RefundImageAdapter(context, mList.get(position).getImage(), position);
                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
                holder.image_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
                if (holder.image_recyclerview.getItemDecorationCount()==0) {
                    holder.image_recyclerview.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
                }
                holder.image_recyclerview.setAdapter(holder.refundImageAdapter);


            } else {
                holder.refundImageAdapter.setPosition(position);
                holder.refundImageAdapter.notifyDataSetChanged();
            }

        }
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        notifyDataSetChanged();
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


        private TextView tv_refuse,tv_agree;


        private TextView RefundMethod;//退款方式
        private TextView refund_total_price;//退款金额
        private TextView refund_mark;//退款理由
        private TextView guke_refund_name;//
        private TextView refund_time;////申请时间

        private LinearLayout refuse_Layout;//
        private TextView refuse_mark;//拒绝理由



        RecyclerView ReturnGoodsRecyclerview;//退货商品信息
        RecyclerView image_recyclerview;//图片



        private RefundOrderDetailsAdapter refundOrderDetailsAdapter;//购买商品
        private List<OrderBean.OrderRefundBean.RefundGoodsBean> RefundList = new ArrayList<>();
        private RefundImageAdapter refundImageAdapter;

        public myViewHodler(View itemView) {
            super(itemView);



            tv_refuse=itemView.findViewById(R.id.tv_refuse);
            tv_agree=itemView.findViewById(R.id.tv_agree);



            //售后

            RefundMethod=itemView.findViewById(R.id.RefundMethod);
            refund_total_price=itemView.findViewById(R.id.refund_total_price);
            refuse_Layout=itemView.findViewById(R.id.refuse_Layout);
            refund_mark=itemView.findViewById(R.id.refund_mark);
            refuse_mark=itemView.findViewById(R.id.refuse_mark);
            ReturnGoodsRecyclerview=itemView.findViewById(R.id.ReturnGoodsRecyclerview);
            image_recyclerview=itemView.findViewById(R.id.image_recyclerview);
            guke_refund_name=itemView.findViewById(R.id.guke_refund_name);
            refund_time=itemView.findViewById(R.id.refund_time);

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
        void onClick(OrderBean.OrderRefundBean lists, int position,int type);
    }

    public void setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }

    private OnClickListener mClickListener;


    //按钮的监听的回调
    public interface PhoneOnClickListener {
        void PhoneOnClick(OrderBean.OrderRefundBean lists, int position);
    }

    public void setPhoneOnClickListener(PhoneOnClickListener listener) {
        mPhoneOnClickListener = listener;
    }

    private PhoneOnClickListener mPhoneOnClickListener;


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
        public void OnItemClick(View view, OrderBean.OrderRefundBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

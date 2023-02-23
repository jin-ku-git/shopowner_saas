package com.youwu.shopowner_saas.ui.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.ui.fragment.bean.SaleBillBean;
import com.youwu.shopowner_saas.utils_view.GridSpacingItemDecoration;

import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * 门店订单适配器
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.myViewHodler> {
    private Context context;
    private List<SaleBillBean> mList;


    //创建构造函数
    public OrderAdapter(Context context, List<SaleBillBean> orderEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.mList = orderEntityList;//实体类数据ArrayList
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
        View itemView = View.inflate(context, R.layout.item_order_layout, null);
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
        SaleBillBean data = mList.get(position);

        holder.time.setText(data.getCreated_at());
        holder.order_state_name.setText(data.getShipping_type_name());
        if (data.getOrder_status()==1&& data.getOrder_taking_status_name()!=null){
            holder.state_name.setText(data.getOrder_taking_status_name());
        }else if (data.getOrder_status()==1&&data.getOrder_taking_status_name()==null){//退款状态
            holder.state_name.setText(data.getOrder_status_name());
        }
        if (data.getOrder_status()!=1&&data.getOrder_taking_status_name()!=null){
            holder.state_name.setText(data.getOrder_taking_status_name());
        }else if (data.getOrder_status()!=1&&data.getOrder_taking_status_name()==null){
            holder.state_name.setText(data.getOrder_status_name());
        }


        holder.goods_num.setText("共"+data.getOrder_count()+"件");
        holder.pay_amount.setText("￥"+data.getTotal_amount());

            //图片列表
            if (holder.refundImageAdapter == null) {
                holder.refundImageAdapter = new ImageAdapter(context, mList.get(position).getOrder_details(), position);
                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
                holder.RefundImageRecycle.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
                if (holder.RefundImageRecycle.getItemDecorationCount()==0) {
                    holder.RefundImageRecycle.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
                }
                holder.RefundImageRecycle.setAdapter(holder.refundImageAdapter);


            } else {
                holder.refundImageAdapter.setPosition(position);
                holder.refundImageAdapter.notifyDataSetChanged();
            }

        holder.refundImageAdapter.setOnReasonListener(new ImageAdapter.OnReasonListener() {
            @Override
            public void onReason() {
               if (mChangeListener!=null){
                   mChangeListener.onChange(data,position);
               }
            }
        });
        holder.RefundImageRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChangeListener!=null){
                    mChangeListener.onChange(data,position);
                }
            }
        });



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


        private TextView time;
        private TextView order_state_name;
        private TextView state_name;
        private TextView goods_num;
        private TextView pay_amount;

        RecyclerView RefundImageRecycle;//图片


        private ImageAdapter refundImageAdapter;

        public myViewHodler(View itemView) {
            super(itemView);



            RefundImageRecycle = itemView.findViewById(R.id.RefundImageRecycle);
            time = itemView.findViewById(R.id.time);
            order_state_name = itemView.findViewById(R.id.order_state_name);
            state_name = itemView.findViewById(R.id.state_name);
            pay_amount = itemView.findViewById(R.id.pay_amount);
            goods_num = itemView.findViewById(R.id.goods_num);


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

    //加减的监听的回调
    public interface OnChangeListener {
        void onChange(SaleBillBean data, int position);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mChangeListener = listener;
    }

    private OnChangeListener mChangeListener;



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
        public void OnItemClick(View view, SaleBillBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}


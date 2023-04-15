package com.youwu.shopowner_saas.ui.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner_saas.utils_view.AnimationUtils;
import com.youwu.shopowner_saas.utils_view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 售后订单订货适配器
 */
public class AfterSalesOrderAdapter extends RecyclerView.Adapter<AfterSalesOrderAdapter.myViewHodler> {
    private Context context;
    private List<XXCOrderBean> mList;
    private int currentIndex = 0;

    //创建构造函数
    public AfterSalesOrderAdapter(Context context, List<XXCOrderBean> goodsEntityList) {
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
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.item_after_sales_order_layout, null);
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
        XXCOrderBean data = mList.get(position);

        holder.PickUpNumber.setText(data.getId()+"");

        if (position%2==0){
            holder.order_type.setText("早餐");
            holder.order_type.setTextColor(context.getResources().getColor(R.color.TextF2Color));
        }else {
            holder.order_type.setText("食材");
            holder.order_type.setTextColor(context.getResources().getColor(R.color.color_lv));
        }
          /*
         1.把内部RecyclerView的adapter和集合数据通过holder缓存
         2.使内部RecyclerView的adapter提供设置position的方法
         */
        holder.list.clear();
        holder.list.addAll(mList.get(position).getGoods_list());

            //退货商品
            if (holder.mRvAdapter == null) {
                holder.mRvAdapter = new CommodityAdapter(context, holder.list, position);
                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
                holder.ReturnGoodsRecyclerview.setLayoutManager(layoutManage);
                holder.ReturnGoodsRecyclerview.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
                holder.ReturnGoodsRecyclerview.setAdapter(holder.mRvAdapter);
            } else {
                holder.mRvAdapter.setPosition(position);
                holder.mRvAdapter.notifyDataSetChanged();
            }

        //购买商品
        if (holder.mAdapter == null) {
            holder.mAdapter = new CommodityAdapter(context, holder.list, position);
            GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
            holder.GoodsRecyclerview.setLayoutManager(layoutManage);
            holder.GoodsRecyclerview.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
            holder.GoodsRecyclerview.setAdapter(holder.mAdapter);
        } else {
            holder.mAdapter.setPosition(position);
            holder.mAdapter.notifyDataSetChanged();
        }


        //图片列表
        if (holder.refundImageAdapter == null) {
            holder.refundImageAdapter = new RetreatImageAdapter(context, mList.get(position).getOrder_details(), position);
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

        holder.tv_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxToast.normal("拒绝");
            }
        });
        holder.tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxToast.normal("同意");
            }
        });



        holder.develop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.type==1){
                    data.type=2;
                    //旋转90°
                    holder.develop_image.animate().rotation(180);
                    holder.develop.setText("收起");
                    //显示动画
                    AnimationUtils.visibleAnimator(holder.xiala_layout);

                }else {
                    //回正
                    holder.develop_image.animate().rotation(0);
                    data.type=1;
                    holder.develop.setText("展开完整信息");
                    //隐藏动画
                    AnimationUtils.invisibleAnimator(holder.xiala_layout);

                }
            }
        });



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


        RecyclerView ReturnGoodsRecyclerview;//
        RecyclerView GoodsRecyclerview;//

        private TextView develop;//展开
        private TextView PickUpNumber;//取餐号

        private LinearLayout xiala_layout;
        private View view_one,view_two;

        private TextView tv_refuse,tv_agree;
        private TextView order_type;//订单类型  早餐/食材
        ImageView develop_image;

        private CommodityAdapter mRvAdapter;
        private CommodityAdapter mAdapter;
        private List<XXCOrderBean.GoodsListBean> list = new ArrayList<>();


        RecyclerView RefundImageRecycle;//图片

        private RetreatImageAdapter refundImageAdapter;

        public myViewHodler(View itemView) {
            super(itemView);


            ReturnGoodsRecyclerview = itemView.findViewById(R.id.ReturnGoodsRecyclerview);
            GoodsRecyclerview = itemView.findViewById(R.id.GoodsRecyclerview);
            develop=itemView.findViewById(R.id.develop);
            xiala_layout=itemView.findViewById(R.id.xiala_layout);
            PickUpNumber=itemView.findViewById(R.id.PickUpNumber);
            develop_image=itemView.findViewById(R.id.develop_image);

            RefundImageRecycle=itemView.findViewById(R.id.image_recyclerview);

            view_one=itemView.findViewById(R.id.view_one);
            view_two=itemView.findViewById(R.id.view_two);


            tv_refuse=itemView.findViewById(R.id.tv_refuse);
            tv_agree=itemView.findViewById(R.id.tv_agree);
            order_type=itemView.findViewById(R.id.order_type);

            //关闭硬件加速显示虚线
            view_one.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            view_two.setLayerType(View.LAYER_TYPE_SOFTWARE,null);




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
        void onClick(XXCOrderBean lists, int position, int status);
    }

    public void setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }

    private OnClickListener mClickListener;


    //按钮的监听的回调
    public interface SelectionStatusOnClickListener {
        void SelectionStatusonClick(XXCOrderBean lists, int position, int status);
    }

    public void setSelectionStatusOnClickListener(SelectionStatusOnClickListener listener) {
        mSelectionStatusClickListener = listener;
    }

    private SelectionStatusOnClickListener mSelectionStatusClickListener;


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
        public void OnItemClick(View view, XXCOrderBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
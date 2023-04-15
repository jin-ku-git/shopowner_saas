package com.youwu.shopowner_saas.ui.order_goods.adapter;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.button.AnimShopButton;
import com.youwu.shopowner_saas.button.IOnAddDelListener;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.XXCOrderBean;

import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 订单商品适配器
 */
public class RetreatOrderGoodsAdapter extends RecyclerView.Adapter<RetreatOrderGoodsAdapter.myViewHodler> {
    private Context context;
    private List<XXCOrderBean.GoodsListBean> mList;
    private int currentIndex = 0;


    //创建构造函数
    public RetreatOrderGoodsAdapter(Context context, List<XXCOrderBean.GoodsListBean> goodsEntityList) {
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
        View itemView = View.inflate(context, R.layout.item_goods, null);
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
        XXCOrderBean.GoodsListBean data = mList.get(position);

        holder.CommodityName.setText(data.getGoods_name());
        holder.CommodityPrice.setText(data.getGoods_price()+"");
        holder.CommodityNum.setText("x"+data.getGoods_quantity());
        holder.btnEleList.setMaxCount(data.getGoods_quantity());
        holder.btnEleList.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int count) {//加
                KLog.i("onAddSuccess:"+count);
                if (!holder.yes_check.isChecked()&&holder.btnEleList.getCount()==1){
                    holder.yes_check.setChecked(true);
                }
                data.setSelect_quantity(count);
                if (mClickListener!=null){
                    mClickListener.onClick(data,position);
                }
            }

            @Override
            public void onAddFailed(int count, FailType failType) {//到顶
                KLog.i("onAddFailed:"+count);
                RxToast.normal("退款商品数量不能再增加了");
            }

            @Override
            public void onDelSuccess(int count) {//减
                KLog.i("onDelSuccess:"+count);
                if (count==0){
                    holder.yes_check.setChecked(false);
                }
                data.setSelect_quantity(count);
                if (mClickListener!=null){
                    mClickListener.onClick(data,position);
                }
            }

            @Override
            public void onDelFaild(int count, FailType failType) {
                KLog.i("onDelFaild:"+count);
            }
        });
        holder.yes_check.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                KLog.d("选中状态"+isChecked);
                data.setStatus(isChecked);
                if (isChecked){
                    KLog.i("btnEleList.getCount()"+holder.btnEleList.getCount());
                    if (holder.btnEleList.getCount()==0){

                        holder.btnEleList.onCountAddSuccess();
                        holder.btnEleList.setCount(data.getGoods_quantity());
                    }
                    data.setSelect_quantity(holder.btnEleList.getCount());

                }
                if (mClickListener!=null){
                    mClickListener.onClick(data,position);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.yes_check.setChecked(!holder.yes_check.isChecked());
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


        TextView CommodityName;
        TextView CommodityPrice;//单价
        TextView CommodityNum;//数量
        AnimShopButton btnEleList;
        SmoothCheckBox yes_check;

        View cutApartView;

        public myViewHodler(View itemView) {
            super(itemView);


            CommodityName = itemView.findViewById(R.id.CommodityName);
            CommodityPrice = itemView.findViewById(R.id.CommodityPrice);
            CommodityNum = itemView.findViewById(R.id.CommodityNum);
            btnEleList = itemView.findViewById(R.id.btnEleList);
            yes_check = itemView.findViewById(R.id.yes_check);
            cutApartView = itemView.findViewById(R.id.cutApartView);

            cutApartView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);


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
        void onClick(XXCOrderBean.GoodsListBean lists, int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }

    private OnClickListener mClickListener;


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
        public void OnItemClick(View view, XXCOrderBean.GoodsListBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
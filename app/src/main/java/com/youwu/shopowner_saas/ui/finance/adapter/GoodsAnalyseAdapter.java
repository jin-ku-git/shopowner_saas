package com.youwu.shopowner_saas.ui.finance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;

import java.util.List;

/**
 * 商品分析适配器
 */
public class GoodsAnalyseAdapter extends RecyclerView.Adapter<GoodsAnalyseAdapter.myViewHodler> {
    private Context context;
    private List<String> mList;
    private int currentIndex = 0;



    //创建构造函数
    public GoodsAnalyseAdapter(Context context, List<String> goodsEntityList) {
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
//        View itemView = View.inflate(context, R.layout.item_account_layout, null);
        View itemView =  LayoutInflater.from(context).inflate(R.layout.item_godos_analyse_layout, parent, false);
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
        String data = mList.get(position);

        holder.goods_top.setText((position+1)+"");


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



        private TextView goods_top;//排名
        private TextView goods_name;//名称
        private TextView goods_sales;//销量
        private TextView goods_order_price;//销售额



        public myViewHodler(View itemView) {
            super(itemView);




            //订单信息
            goods_top=itemView.findViewById(R.id.goods_top);
            goods_name=itemView.findViewById(R.id.goods_name);
            goods_sales=itemView.findViewById(R.id.goods_sales);
            goods_order_price=itemView.findViewById(R.id.goods_order_price);




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
        void onClick(OrderBean lists, int position,int type);
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
        public void OnItemClick(View view, String data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

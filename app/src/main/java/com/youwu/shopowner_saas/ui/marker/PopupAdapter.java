package com.youwu.shopowner_saas.ui.marker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.utils_view.ColorUtil;

import java.util.List;


public class PopupAdapter extends RecyclerView.Adapter<PopupAdapter.myViewHodler> {
    private Context context;
    private List<String> goodsEntityList;
    private int currentIndex = 0;

    //创建构造函数
    public PopupAdapter(Context context, List<String> goodsEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.goodsEntityList = goodsEntityList;//实体类数据ArrayList
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
        View itemView = View.inflate(context, R.layout.item_popup_layout, null);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHodler holder, @SuppressLint("RecyclerView") final int position) {

        //根据点击位置绑定数据
        holder.tv_value.setText(goodsEntityList.get(position));
        int[] colors= ColorUtil.SALE_COLORS;

        if (position==0){
            holder.view_yuan.setBackgroundResource(R.drawable.bth_yuan_5087ec);
        }else {
            holder.view_yuan.setBackgroundResource(R.drawable.bth_yuan_68bbc4);
        }

    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return goodsEntityList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {

        private TextView tv_value;//
        private View view_yuan;




        public myViewHodler(View itemView) {
            super(itemView);

            tv_value = (TextView) itemView.findViewById(R.id.tv_value);
            view_yuan =  itemView.findViewById(R.id.view_yuan);



            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, goodsEntityList.get(getLayoutPosition()),getLayoutPosition());
                    }
                }
            });


        }

    }

    //图片的监听的回调
    public interface OnCouPonListener {
        void onCouPon(String data, int position);
    }

    public void setOnCouPonListener(OnCouPonListener listener) {
        mCouPonListener = listener;
    }

    private OnCouPonListener mCouPonListener;



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
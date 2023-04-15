package com.youwu.shopowner_saas.ui.finance.adapter;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.youwu.shopowner_saas.app.AppApplication.subZeroAndDot;
import static com.youwu.shopowner_saas.utils_view.TimeUtil.isToday;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.adapter.OrderDetailsAdapter;
import com.youwu.shopowner_saas.ui.fragment.adapter.ShouHouOrderAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;
import com.youwu.shopowner_saas.ui.order_goods.ApplyReturnGoodsActivity;
import com.youwu.shopowner_saas.utils_view.AnimationUtils;
import com.youwu.shopowner_saas.utils_view.CountDownView;
import com.youwu.shopowner_saas.utils_view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 对账适配器
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.myViewHodler> {
    private Context context;
    private List<String> mList;
    private int currentIndex = 0;



    //创建构造函数
    public AccountAdapter(Context context, List<String> goodsEntityList) {
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
        View itemView =  LayoutInflater.from(context).inflate(R.layout.item_account_layout, parent, false);
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

        holder.order_sn.setText("充值单号："+data);


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



        //用户信息
        private TextView order_sn;//充值单号



        public myViewHodler(View itemView) {
            super(itemView);




            //订单信息
            order_sn=itemView.findViewById(R.id.order_sn);




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

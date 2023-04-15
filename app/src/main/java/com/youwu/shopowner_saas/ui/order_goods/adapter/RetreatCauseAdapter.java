package com.youwu.shopowner_saas.ui.order_goods.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.button.AnimShopButton;
import com.youwu.shopowner_saas.button.IOnAddDelListener;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.GroupBean;
import com.youwu.shopowner_saas.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner_saas.ui.order_goods.bean.CauseItemBean;

import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 申请退货原因适配器
 */
public class RetreatCauseAdapter extends RecyclerView.Adapter<RetreatCauseAdapter.myViewHodler> {
    private Context context;
    private List<CauseItemBean> mList;
    private int currentIndex = 0;


    //创建构造函数
    public RetreatCauseAdapter(Context context, List<CauseItemBean> goodsEntityList) {
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
        View itemView = View.inflate(context, R.layout.item_retreat_cause, null);
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
        CauseItemBean data = mList.get(position);
//        holder.bindData(mList.get(position), position, currentIndex);
        holder.causeName.setText(data.getCause_name());

        holder.yes_check.setChecked(data.isCause_status());
        KLog.i("yes_check:"+holder.yes_check.isChecked());

        holder.yes_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setCurrentIndex(position);
                if (data.isCause_status()){
                    data.setCause_status(false);
                }else {
                    data.setCause_status(true);
                }
                if (mClickListener!=null){
                    mClickListener.onClick(data,position);
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setCurrentIndex(position);
                if (data.isCause_status()){
                    data.setCause_status(false);
                }else {
                    data.setCause_status(true);
                }
                if (mClickListener!=null){
                    mClickListener.onClick(data,position);
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


        TextView causeName;

        SmoothCheckBox yes_check;


        public myViewHodler(View itemView) {
            super(itemView);


            causeName = itemView.findViewById(R.id.causeName);

            yes_check = itemView.findViewById(R.id.yes_check);



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

        public void bindData(CauseItemBean goodsEntity, int position, int currentIndex) {
            if (position == currentIndex) {
                yes_check.setChecked(true);
                mList.get(position).setCause_status(true);

            } else {
                yes_check.setChecked(false);
                mList.get(position).setCause_status(false);


            }

        }


    }


    //按钮的监听的回调
    public interface OnClickListener {
        void onClick(CauseItemBean lists, int position);
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
        public void OnItemClick(View view, CauseItemBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
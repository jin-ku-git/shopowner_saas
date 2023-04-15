package com.youwu.shopowner_saas.ui.finance.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.ui.fragment.adapter.RefundImageAdapter;
import com.youwu.shopowner_saas.utils_view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 营业明细适配器
 */
public class MingXiYingYeAdapter extends RecyclerView.Adapter<MingXiYingYeAdapter.myViewHodler> {
    private Context context;
    private List<String> mList;
    private int currentIndex = 0;

    //创建构造函数
    public MingXiYingYeAdapter(Context context, List<String> goodsEntityList) {
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
    public MingXiYingYeAdapter.myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
//        View itemView = View.inflate(context, R.layout.adapter_drag_touch_list_item, null);
        View itemView =  LayoutInflater.from(context).inflate(R.layout.adapter_ying_ye_ming_xi_item, parent, false);
        return new MingXiYingYeAdapter.myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MingXiYingYeAdapter.myViewHodler holder, @SuppressLint("RecyclerView") final int position) {

        //根据点击位置绑定数据
        String data = mList.get(position);

        holder.order_sn.setText(data);


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



        private TextView order_sn;//




        public myViewHodler(View itemView) {
            super(itemView);



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


    /**
     * type 1、回复 2、
     */
    //按钮的监听的回调
    public interface OnClickListener {
        void onClick(String lists, int position,int type);
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
    private MingXiYingYeAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(MingXiYingYeAdapter.OnItemClickListener onItemClickListener) {
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
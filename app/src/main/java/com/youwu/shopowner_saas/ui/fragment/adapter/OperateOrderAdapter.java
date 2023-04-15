package com.youwu.shopowner_saas.ui.fragment.adapter;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

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
import com.youwu.shopowner_saas.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner_saas.ui.order_goods.ApplyReturnGoodsActivity;
import com.youwu.shopowner_saas.utils_view.AnimationUtils;
import com.youwu.shopowner_saas.utils_view.CountDownView;
import com.youwu.shopowner_saas.utils_view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单适配器
 */
public class OperateOrderAdapter extends RecyclerView.Adapter<OperateOrderAdapter.myViewHodler> {
    private Context context;
    private List<XXCOrderBean> mList;
    private int currentIndex = 0;


    //创建构造函数
    public OperateOrderAdapter(Context context, List<XXCOrderBean> goodsEntityList) {
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
        View itemView = View.inflate(context, R.layout.item_operate_order_layout, null);
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
            holder.order_type.setTextColor(context.getResources().getColor(R.color.main_white));
            holder.order_type.setBackgroundResource(R.drawable.radius_lv_top_right_10dp);
        }

        holder.cdv_count_down_one.setTimeTextSize(13);
        holder.cdv_count_down_one.setTimeTextStyle(false);
        holder.cdv_count_down_one.setTimeTextColor(context.getResources().getColor(R.color.TextOrangeColor));
        holder.cdv_count_down_one.setColonTextColor(context.getResources().getColor(R.color.TextOrangeColor));
        holder.cdv_count_down_one.initEndTime("2023-02-27 14:50:00")
                .calcTime_two()
                .startRun(2)
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        holder.cdv_count_down_one
                                .setTvHourText("00")
                                .setTvMinuteText("00")
                                .setTvMinuteText("00");
                    }
                });
        holder.cdv_count_down.setTimeTextSize(13);
        holder.cdv_count_down.setTimeTextStyle(false);
        holder.cdv_count_down.setTimeTextColor(context.getResources().getColor(R.color.TextOrangeColor));
        holder.cdv_count_down.setColonTextColor(context.getResources().getColor(R.color.TextOrangeColor));
        holder.cdv_count_down.initEndTime("2023-02-27 14:5" +
                        "0:00")
                .calcTime_two()
                .startRun(2)
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        holder.cdv_count_down
                                .setTvHourText("00")
                                .setTvMinuteText("00")
                                .setTvMinuteText("00");
                    }
                });

        holder.cdv_reckon_time.setTimeTextSize(13);
        holder.cdv_reckon_time.setTimeTextColor(context.getResources().getColor(R.color.main_white));
        holder.cdv_reckon_time.setTimeBackGroundResource(R.color.TextF2Color);
        holder.cdv_reckon_time.setTimeBackGroundResource(R.color.TextF2Color);
        holder.cdv_reckon_time.setColonTextColor(context.getResources().getColor(R.color.TextOrangeColor));
        holder.cdv_reckon_time.initEndTime("2023-02-27 17:00:00")
                .calcTime()
                .startRun(1)
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        holder.cdv_reckon_time
                                .setTvHourText("00")
                                .setTvMinuteText("00")
                                .setTvMinuteText("00");
                    }
                });

        if (data.type==1){
            holder.develop_image.animate().rotation(0);
            holder.develop.setText("展开完整信息");
            //隐藏动画
            AnimationUtils.invisibleAnimator(holder.xiala_layout);
        }else if (data.type==2){
            //旋转90°
            holder.develop_image.animate().rotation(180);
            holder.develop.setText("收起");
            //显示动画
            AnimationUtils.visibleAnimator(holder.xiala_layout);
        }



          /*
         1.把内部RecyclerView的adapter和集合数据通过holder缓存
         2.使内部RecyclerView的adapter提供设置position的方法
         */
        holder.list.clear();
        holder.list.addAll(mList.get(position).getGoods_list());



            if (holder.mRvAdapter == null) {
                holder.mRvAdapter = new CommodityAdapter(context, holder.list, position);
                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
                holder.GoodsRecyclerview.setLayoutManager(layoutManage);
                holder.GoodsRecyclerview.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
                holder.GoodsRecyclerview.setAdapter(holder.mRvAdapter);

            } else {
                holder.mRvAdapter.setPosition(position);
                holder.mRvAdapter.notifyDataSetChanged();
            }



        //出餐
        holder.DiningOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RxToast.normal("出餐完成");
                }
            });
        holder.DiningOutOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RxToast.normal("出餐完成");
                }
            });
        //展开/收起
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
        //展开/收起
        holder.develop_image.setOnClickListener(new View.OnClickListener() {
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
        //复制单号
            holder.btCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(holder.OrderSn.getText());
                    RxToast.success("复制成功!");
                }
            });
            //取消订单
            holder.cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new  XPopup.Builder(context)

                            .asConfirm("提示", "请确认是否取消订单", "暂不", "确认取消", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    RxToast.normal("确认取消");

                                }
                            }, null,false)
                            .show();
                }
            });
            //拒单
            holder.Refusal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener!=null){
                        mClickListener.onClick(data,position);
                    }
                }
            });
            //部分退款
            holder.Partial_refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name= new Gson().toJson(data.getGoods_list());

                    Intent intent = new Intent();
                    intent.setClass(context, ApplyReturnGoodsActivity.class);
                    intent.putExtra("name",name);
                    startActivity(intent);

                }
            });

        holder.tv_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener!=null){
                    mClickListener.onClick(data,position);
                }
                RxToast.normal("拒绝");
            }
        });
        holder.tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxToast.normal("同意");
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


        private TextView tv_refuse,tv_agree;


        private TextView PickUpNumber;//取餐号
        private TextView btCopy;//复制
        private TextView OrderSn;//单号
        private LinearLayout xiala_layout;//订单商品信息
        private LinearLayout ShouHouLayout;//售后信息
        private View view_one,view_two,view_three,view_four;
        private TextView DiningOut;//出餐完成
        private TextView DiningOutOne;//出餐完成
        private TextView Refusal;//拒单
        private TextView cancel_order;//取消订单
        private TextView Partial_refund;//部分退款
        private TextView order_type;//订单类型  早餐/食材
        private TextView develop;//展开
        private ImageView develop_image;//展开

        private  CountDownView cdv_count_down;//计时时间
        private  CountDownView cdv_count_down_one;//计时时间
        private  CountDownView cdv_reckon_time;//倒计时时间

        RecyclerView GoodsRecyclerview;//商品信息

        private CommodityAdapter mRvAdapter;//购买商品

        private List<XXCOrderBean.GoodsListBean> list = new ArrayList<>();


        public myViewHodler(View itemView) {
            super(itemView);


            GoodsRecyclerview = itemView.findViewById(R.id.GoodsRecyclerview);

            develop=itemView.findViewById(R.id.develop);
            xiala_layout=itemView.findViewById(R.id.xiala_layout);
            ShouHouLayout=itemView.findViewById(R.id.ShouHouLayout);
            PickUpNumber=itemView.findViewById(R.id.PickUpNumber);
            develop_image=itemView.findViewById(R.id.develop_image);
            btCopy=itemView.findViewById(R.id.btCopy);
            OrderSn=itemView.findViewById(R.id.OrderSn);
            view_one=itemView.findViewById(R.id.view_one);
            view_two=itemView.findViewById(R.id.view_two);
            view_three=itemView.findViewById(R.id.view_three);
            view_four=itemView.findViewById(R.id.view_four);
            cdv_count_down=itemView.findViewById(R.id.cdv_count_down);
            cdv_count_down_one=itemView.findViewById(R.id.cdv_count_down_one);
            DiningOut=itemView.findViewById(R.id.DiningOut);
            DiningOutOne=itemView.findViewById(R.id.DiningOutOne);
            cdv_reckon_time=itemView.findViewById(R.id.cdv_reckon_time);
            cancel_order=itemView.findViewById(R.id.cancel_order);
            Partial_refund=itemView.findViewById(R.id.Partial_refund);
            Refusal=itemView.findViewById(R.id.Refusal);
            order_type=itemView.findViewById(R.id.order_type);
            tv_refuse=itemView.findViewById(R.id.tv_refuse);
            tv_agree=itemView.findViewById(R.id.tv_agree);

            //关闭硬件加速显示虚线
            view_one.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            view_two.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            view_three.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            view_four.setLayerType(View.LAYER_TYPE_SOFTWARE,null);


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
        void onClick(XXCOrderBean lists, int position);
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
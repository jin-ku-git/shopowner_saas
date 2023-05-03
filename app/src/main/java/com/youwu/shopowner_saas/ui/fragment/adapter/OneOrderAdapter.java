package com.youwu.shopowner_saas.ui.fragment.adapter;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.youwu.shopowner_saas.app.AppApplication.subZeroAndDot;
import static com.youwu.shopowner_saas.utils_view.TimeUtil.isToday;
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
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;
import com.youwu.shopowner_saas.ui.order_goods.ApplyReturnGoodsActivity;
import com.youwu.shopowner_saas.ui.zaocan.bean.ChildData;
import com.youwu.shopowner_saas.ui.zaocan.bean.MergeInfo;
import com.youwu.shopowner_saas.utils_view.AnimationUtils;
import com.youwu.shopowner_saas.utils_view.CountDownView;
import com.youwu.shopowner_saas.utils_view.GridSpacingItemDecoration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 订单适配器
 */
public class OneOrderAdapter extends RecyclerView.Adapter<OneOrderAdapter.myViewHodler> {
    private Context context;
    private List<OrderBean> mList;
    private int currentIndex = 0;
    private int type;//状态 0 全部 1、待接单 2、待出餐


    //创建构造函数
    public OneOrderAdapter(Context context, List<OrderBean> goodsEntityList,int type) {
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
        OrderBean data = mList.get(position);

        holder.PickUpNumber.setText(data.getDining_code());
        holder.user_name.setText(data.getMember_name());
        holder.user_name_one.setText(data.getMember_name());
        holder.order_status_name.setText(data.getStatus_name());
        if (data.getMember_phone()!=null&&!"".equals(data.getMember_phone())){

            String phone=data.getMember_phone().substring(data.getMember_phone().length()-4);
            holder.user_phone.setText("手机尾号"+phone);
            holder.user_phone_one.setText("手机尾号"+phone);
        }
        holder.distance.setText(data.getJuli()+"KM");
        holder.user_address.setText(data.getPickup_address());

        holder.remarks.setText((data.getTableware_number()==0?"无需餐具；":data.getTableware_number()+"份餐具；")+data.getMark());

        //商品信息
        holder.goods_amount.setText(subZeroAndDot("￥"+data.getItem_amount()));
        holder.packing_fee.setText(subZeroAndDot("￥"+data.getPacking_fee()));
        holder.shipping_fee.setText(subZeroAndDot("￥"+data.getShipping_fee()));
        holder.reduced_amount.setText(subZeroAndDot("-￥"+data.getReduced_amount()));

        holder.pay_amount.setText(subZeroAndDot("￥"+data.getPay_amount()));
        holder.pay_channel.setText(data.getPay_channel());

        holder.pay_channel_name.setText(data.getPay_channel());
        holder.pay_amount_one.setText("￥"+subZeroAndDot(data.getPay_amount()+""));

        holder.OrderTime.setText("下单 "+data.getCreated_at());
        holder.OrderSn.setText(data.getOrder_sn());


        renewView(data,holder,position);

        holder.order_type.setText(data.getChannel_name());
        if ("早餐".equals(data.getChannel_name())){
            holder.order_type.setTextColor(context.getResources().getColor(R.color.TextF2Color));
            holder.order_type.setBackgroundResource(R.drawable.radius_yellow_top_right_10dp);
        }else {
            holder.order_type.setTextColor(context.getResources().getColor(R.color.main_white));
            holder.order_type.setBackgroundResource(R.drawable.radius_lv_top_right_10dp);
        }


        holder.goods_sum.setText(subZeroAndDot(data.getGoods_kind_num()+"")+"种商品，共"+subZeroAndDot(data.getGoods_num()+"")+"件");

//        holder.cdv_count_down_one.setTimeTextSize(13);
//        holder.cdv_count_down_one.setTimeTextStyle(false);
//        holder.cdv_count_down_one.setTimeTextColor(context.getResources().getColor(R.color.TextOrangeColor));
//        holder.cdv_count_down_one.setColonTextColor(context.getResources().getColor(R.color.TextOrangeColor));
//        holder.cdv_count_down_one.initEndTime(data.getCreated_at())
//                .calcTime_two()
//                .startRun(2)
//                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
//                    @Override
//                    public void onCountDownEnd() {
//                        holder.cdv_count_down_one
//                                .setTvHourText("00")
//                                .setTvMinuteText("00")
//                                .setTvMinuteText("00");
//                    }
//                });
//        holder.cdv_count_down.setTimeTextSize(13);
//        holder.cdv_count_down.setTimeTextStyle(false);
//        holder.cdv_count_down.setTimeTextColor(context.getResources().getColor(R.color.TextOrangeColor));
//        holder.cdv_count_down.setColonTextColor(context.getResources().getColor(R.color.TextOrangeColor));
//        holder.cdv_count_down.initEndTime(data.getCreated_at())
//                .calcTime_two()
//                .startRun(2)
//                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
//                    @Override
//                    public void onCountDownEnd() {
//                        holder.cdv_count_down
//                                .setTvHourText("00")
//                                .setTvMinuteText("00")
//                                .setTvMinuteText("00");
//                    }
//                });






        if (data.type==0){
            holder.develop_image.animate().rotation(0);
            holder.develop.setText("展开完整信息");
            holder.xiala_layout.setVisibility(View.GONE);
            //隐藏动画
//            AnimationUtils.invisibleAnimator(holder.xiala_layout);


            holder.pay_channel_name.setVisibility(View.VISIBLE);
            holder.pay_amount_one.setVisibility(View.VISIBLE);


        }else if (data.type==1){
            //旋转90°
            holder.develop_image.animate().rotation(180);
            holder.develop.setText("收起");
            holder.xiala_layout.setVisibility(View.VISIBLE);
            //显示动画
//            AnimationUtils.visibleAnimator(holder.xiala_layout);


            holder.pay_channel_name.setVisibility(View.GONE);
            holder.pay_amount_one.setVisibility(View.GONE);
        }



          /*
         1.把内部RecyclerView的adapter和集合数据通过holder缓存
         2.使内部RecyclerView的adapter提供设置position的方法
         */
        holder.list.clear();
        //合并重复的数据
        holder.list.addAll(mList.get(position).getOrder_details());

            if (holder.mRvAdapter == null) {
                holder.mRvAdapter = new OrderDetailsAdapter(context, holder.list, position);
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
                    if ("确认接单".equals(holder.DiningOut.getText())){
                        if (mClickListener!=null){
                            mClickListener.onClick(data,position,2);
                        }

                    }else if ("出餐完成".equals(holder.DiningOut.getText())){

                        if (mClickListener!=null){
                            mClickListener.onClick(data,position,4);
                        }

                    }

                }
            });
        holder.DiningOutOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener!=null){
                        mClickListener.onClick(data,position,4);
                    }

                }
            });
        //展开/收起
        holder.develop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.type==0){
                    data.type=1;
                    //旋转90°
                    holder.develop_image.animate().rotation(180);
                    holder.develop.setText("收起");
                    holder.xiala_layout.setVisibility(View.VISIBLE);
                    //显示动画
//                    AnimationUtils.visibleAnimator(holder.xiala_layout);

                    holder.pay_channel_name.setVisibility(View.GONE);
                    holder.pay_amount_one.setVisibility(View.GONE);

                }else {
                    //回正
                    holder.develop_image.animate().rotation(0);
                    data.type=0;
                    holder.develop.setText("展开完整信息");
                    holder.xiala_layout.setVisibility(View.GONE);
                    //隐藏动画
//                    AnimationUtils.invisibleAnimator(holder.xiala_layout);

                    holder.pay_channel_name.setVisibility(View.VISIBLE);
                    holder.pay_amount_one.setVisibility(View.VISIBLE);

                }
                renewView(data,holder,position);
            }
        });
        //展开/收起
        holder.develop_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.type==0){
                    data.type=1;
                    //旋转90°
                    holder.develop_image.animate().rotation(180);
                    holder.xiala_layout.setVisibility(View.VISIBLE);
                    holder.develop.setText("收起");
                    //显示动画
//                    AnimationUtils.visibleAnimator(holder.xiala_layout);

                    holder.pay_channel_name.setVisibility(View.GONE);
                    holder.pay_amount_one.setVisibility(View.GONE);

                }else {
                    //回正
                    holder.develop_image.animate().rotation(0);
                    data.type=0;
                    holder.develop.setText("展开完整信息");
                    holder.xiala_layout.setVisibility(View.GONE);
                    //隐藏动画
//                    AnimationUtils.invisibleAnimator(holder.xiala_layout);

                    holder.pay_channel_name.setVisibility(View.VISIBLE);
                    holder.pay_amount_one.setVisibility(View.VISIBLE);
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
                        mClickListener.onClick(data,position,3);
                    }
                    KLog.i("退款拒绝");
                }
            });
            //部分退款
            holder.Partial_refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name= new Gson().toJson(data.getOrder_details());

                    Intent intent = new Intent();
                    intent.setClass(context, ApplyReturnGoodsActivity.class);
                    intent.putExtra("name",name);
                    startActivity(intent);

                }
            });

        //打电话
        holder.iv_phone_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mPhoneOnClickListener!=null){
                        mPhoneOnClickListener.PhoneOnClick(data,position);
                    }
            }
        });
        holder.iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mPhoneOnClickListener!=null){
                        mPhoneOnClickListener.PhoneOnClick(data,position);
                    }
            }
        });
        //打印
        holder.iv_dayin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStampOnClickListener!=null){
                    mStampOnClickListener.StampOnClick(data,position);
                }
            }
        });


    }

    private void renewView(OrderBean data, myViewHodler holder,int position) {

        //0、待支付 1、已支付 2、已取消 3、已退款
        switch (data.getOrder_status()){
            case 0:
                holder.cancel_cause_layout.setVisibility(View.VISIBLE);
                holder.tv_cancel_cause.setText("取消原因：用户超时未支付");
                break;
            case 1:
                holder.cancel_cause_layout.setVisibility(View.GONE);
                switch (data.getOrder_taking_status()){
                    //门店接单状态:1.待接单2,接单-待出餐3.拒单4.出餐
                    case 1:
                        holder.Order_taking_name.setText("已下单");
                        holder.Order_taking_name_one.setText("已下单");
                        holder.DiningOut.setText("确认接单");
                        holder.Refusal.setVisibility(View.VISIBLE);
                        holder.DiningOut.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        holder.Order_taking_name.setText("备餐中，已用时");
                        holder.Order_taking_name_one.setText("备餐中");
                        holder.DiningOut.setText("出餐完成");
                        holder.DiningOut.setVisibility(View.VISIBLE);
                        if (data.getType()==0&&type==2){//展开还是收起 0、关闭 1、打开
//                holder.close_time_layout.setVisibility(View.VISIBLE);
                            holder.DiningOutOne.setVisibility(View.VISIBLE);
                            holder.top_user_info_layout.setVisibility(View.VISIBLE);
                            holder.layout_all.setVisibility(View.GONE);
                            holder.order_status_name.setVisibility(View.GONE);
                        }else {
                            holder.close_time_layout.setVisibility(View.GONE);
                            holder.DiningOutOne.setVisibility(View.GONE);
                            holder.top_user_info_layout.setVisibility(View.GONE);
                            holder.layout_all.setVisibility(View.VISIBLE);
                        }
                        break;
                    default :
                        holder.DiningOut.setVisibility(View.GONE);
                        holder.Order_taking_name.setText("已上报出餐完成");
                        break;
                }
                break;
            case 2://已取消
                holder.cancel_cause_layout.setVisibility(View.VISIBLE);
                holder.tv_cancel_cause.setText("取消原因：用户取消订单");
                break;
            case 3:
                holder.cancel_cause_layout.setVisibility(View.VISIBLE);
                holder.tv_cancel_cause.setText("取消原因：用户退款申请被系统自动通过");
                break;

        }
        switch (data.getDelivery_method_id()){
            //配送方式 3、自提 4、堂食 5、外卖配送 6、配送到柜
            case 3://自提
                holder.order_status_name_two.setText("自提");

                if (isToday(data.getAppointment_time()+" 00:00:00")){//判断是否是今天的订单
                    holder.serviceTime.setText("预计/今日"+" "+data.getAppointment_hour()+"到店取餐");

                }else {
                    holder.serviceTime.setText("预计/"+data.getAppointment_time()+" "+data.getAppointment_hour()+"到店取餐");
                }

                String time=" "+data.getAppointment_hour().substring(data.getAppointment_hour().indexOf("-"))+":00";

                holder.ziti_layout.setVisibility(View.VISIBLE);
                holder.cdv_reckon_time.setTimeTextSize(13);
                holder.cdv_reckon_time.setTimeTextColor(context.getResources().getColor(R.color.main_white));
                holder.cdv_reckon_time.setTimeBackGroundResource(R.color.TextF2Color);
                holder.cdv_reckon_time.setTimeBackGroundResource(R.color.TextF2Color);
                holder.cdv_reckon_time.setColonTextColor(context.getResources().getColor(R.color.TextOrangeColor));
                holder.cdv_reckon_time.initEndTime(data.getAppointment_time()+time)
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

                holder.cdv_reckon_time.setOnTimeEnd(new CountDownView.OnTimeEnd() {
                    @Override
                    public void onTimeEndt() {
                        holder.ziti_layout.setVisibility(View.GONE);
                    }
                });
                break;
            case 4://堂食
                holder.serviceTime.setVisibility(View.GONE);
                holder.order_status_name_two.setText("堂食");
                break;
            case 5://外卖配送
                holder.serviceTime.setText("立即送达/"+data.getAppointment_time()+" "+data.getAppointment_hour()+"前送达");
                holder.DeliveryInformationLayout.setVisibility(View.VISIBLE);
                holder.order_status_name_two.setText("外卖配送");
                break;
            case 6://配送到柜
                holder.order_status_name_two.setText("配送到柜");
                if (isToday(data.getAppointment_time()+" 00:00:00")){//判断是否是今天的订单
                    holder.serviceTime.setText("预计/今日"+" "+data.getAppointment_hour()+"到店取餐");

                }else {
                    holder.serviceTime.setText("预计/"+data.getAppointment_time()+" "+data.getAppointment_hour()+"到店取餐");
                }
                break;
        }

        //0、待支付 1、已支付 2、已取消 3、已退款
//        if (data.getOrder_status()==0){
//
//            holder.cancel_cause_layout.setVisibility(View.VISIBLE);
//            holder.tv_cancel_cause.setText("取消原因：用户超时未支付");
//
//        }else if (data.getOrder_status()==1){//已支付
//            //门店接单状态:1.待接单2,接单-待出餐3.拒单4.出餐
//            if (data.getOrder_taking_status()==1){
//                holder.Order_taking_name.setText("已下单");
//                holder.Order_taking_name_one.setText("已下单");
//                holder.DiningOut.setText("确认接单");
//                holder.Refusal.setVisibility(View.VISIBLE);
//                holder.DiningOut.setVisibility(View.VISIBLE);
//
//            }else if (data.getOrder_taking_status()==2){
//
//                holder.Order_taking_name.setText("备餐中，已用时");
//                holder.Order_taking_name_one.setText("备餐中");
//                holder.DiningOut.setText("出餐完成");
//                holder.DiningOut.setVisibility(View.VISIBLE);
//                if (data.getType()==0&&type==2){//展开还是收起 0、关闭 1、打开
////                holder.close_time_layout.setVisibility(View.VISIBLE);
//                    holder.DiningOutOne.setVisibility(View.VISIBLE);
//                    holder.top_user_info_layout.setVisibility(View.VISIBLE);
//                    holder.layout_all.setVisibility(View.GONE);
//                    holder.order_status_name.setVisibility(View.GONE);
//                }else {
//                    holder.close_time_layout.setVisibility(View.GONE);
//                    holder.DiningOutOne.setVisibility(View.GONE);
//                    holder.top_user_info_layout.setVisibility(View.GONE);
//                    holder.layout_all.setVisibility(View.VISIBLE);
//                }
//            }else {
//                holder.DiningOut.setVisibility(View.GONE);
//                holder.Order_taking_name.setText("已上报出餐完成");
//
//            }
//
//        }else if (data.getOrder_status()==2){//已取消
//            holder.cancel_cause_layout.setVisibility(View.VISIBLE);
//            holder.tv_cancel_cause.setText("取消原因：用户取消订单");
//
//        }else if (data.getOrder_status()==3){//已退款
//            holder.cancel_cause_layout.setVisibility(View.VISIBLE);
//            holder.tv_cancel_cause.setText("取消原因：用户退款申请被系统自动通过");
//
//        }
//        //配送方式 3、自提 4、堂食 5、外卖配送 6、配送到柜
//        if (data.getDelivery_method_id() == 3) {//自提
//
//            holder.order_status_name_two.setText("自提");
//
//            if (isToday(data.getAppointment_time()+" 00:00:00")){//判断是否是今天的订单
//                holder.serviceTime.setText("预计/今日"+" "+data.getAppointment_hour()+"到店取餐");
//
//            }else {
//                holder.serviceTime.setText("预计/"+data.getAppointment_time()+" "+data.getAppointment_hour()+"到店取餐");
//            }
//
//            String time=" "+data.getAppointment_hour().substring(data.getAppointment_hour().indexOf("-"))+":00";
//
//            holder.ziti_layout.setVisibility(View.VISIBLE);
//            holder.cdv_reckon_time.setTimeTextSize(13);
//            holder.cdv_reckon_time.setTimeTextColor(context.getResources().getColor(R.color.main_white));
//            holder.cdv_reckon_time.setTimeBackGroundResource(R.color.TextF2Color);
//            holder.cdv_reckon_time.setTimeBackGroundResource(R.color.TextF2Color);
//            holder.cdv_reckon_time.setColonTextColor(context.getResources().getColor(R.color.TextOrangeColor));
//            holder.cdv_reckon_time.initEndTime(data.getAppointment_time()+time)
//                    .calcTime()
//                    .startRun(1)
//                    .setCountDownEndListener(new CountDownView.CountDownEndListener() {
//                        @Override
//                        public void onCountDownEnd() {
//                            holder.cdv_reckon_time
//                                    .setTvHourText("00")
//                                    .setTvMinuteText("00")
//                                    .setTvMinuteText("00");
//                        }
//                    });
//
//            holder.cdv_reckon_time.setOnTimeEnd(new CountDownView.OnTimeEnd() {
//                @Override
//                public void onTimeEndt() {
//                    holder.ziti_layout.setVisibility(View.GONE);
//                }
//            });
//        }else if (data.getDelivery_method_id()==4){//堂食
//            holder.serviceTime.setVisibility(View.GONE);
//            holder.order_status_name_two.setText("堂食");
//        }else if (data.getDelivery_method_id()==5){//外卖配送
//            holder.serviceTime.setText("立即送达/"+data.getAppointment_time()+" "+data.getAppointment_hour()+"前送达");
//            holder.DeliveryInformationLayout.setVisibility(View.VISIBLE);
//            holder.order_status_name_two.setText("外卖配送");
//        }else if (data.getDelivery_method_id()==6){//配送到柜
//            holder.order_status_name_two.setText("配送到柜");
//            if (isToday(data.getAppointment_time()+" 00:00:00")){//判断是否是今天的订单
//                holder.serviceTime.setText("预计/今日"+" "+data.getAppointment_hour()+"到店取餐");
//
//            }else {
//                holder.serviceTime.setText("预计/"+data.getAppointment_time()+" "+data.getAppointment_hour()+"到店取餐");
//            }
//        }
        //售后信息
//        if (mList.get(position).getOrder_refund()!=null){
//            holder.RefundList.clear();
//            holder.RefundList.addAll(mList.get(position).getOrder_refund());
//
//            if (holder.shouHouOrderAdapter == null) {
//                holder.shouHouOrderAdapter = new ShouHouOrderAdapter(context, holder.RefundList, position);
//                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
//                holder.SHRecyclerView.setLayoutManager(layoutManage);
//                holder.SHRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
//                holder.SHRecyclerView.setAdapter(holder.shouHouOrderAdapter);
//
//                holder.shouHouOrderAdapter.setOnClickListener(new ShouHouOrderAdapter.OnClickListener() {
//                    @Override
//                    public void onClick(OrderBean.OrderRefundBean lists, int position, int type) {
//
//                        if (mDrawbackClickListener!=null){
//                            mDrawbackClickListener.DrawbackonOnClick(lists,position,type);
//                        }
//
//                    }
//                });
//
//            } else {
//                holder.shouHouOrderAdapter.notifyDataSetChanged();
//            }
//        }


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
        private TextView user_name,user_name_one;//用户昵称
        private TextView user_phone,user_phone_one;//用户手机号
        private TextView distance;//距离
        private TextView user_address;//用户地址

        //商品信息
        private TextView goods_amount;//商品合计
        private TextView packing_fee;//打包费
        private TextView shipping_fee;//配送费
        private TextView reduced_amount;//优惠
        private TextView pay_channel;//支付方式
        private TextView pay_amount;//实付

        //订单信息
        private TextView Order_taking_name,Order_taking_name_one;


        private TextView remarks;//备注
        private LinearLayout remarks_layout;//备注

        private LinearLayout close_time_layout;//顶部时间显示
        private LinearLayout ziti_layout;//自提显示
        private LinearLayout layout_all;//待出餐显示/隐藏
        private LinearLayout top_user_info_layout;//待出餐显示/隐藏
        private TextView serviceTime;//送达时间
        private TextView goods_sum;//商品信息
        private TextView order_status_name;//订单状态名称


        private TextView PickUpNumber;//取餐号
        private TextView btCopy;//复制
        private TextView OrderSn;//单号
        private LinearLayout xiala_layout;//订单商品信息

        private LinearLayout DeliveryInformationLayout;//配送地址和距离
        private LinearLayout cancel_cause_layout;//取消订单展示
        private TextView tv_cancel_cause;//取消原因
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

        private  TextView OrderTime;//下单时间
        private  TextView pay_channel_name;//支付方式
        private  TextView pay_amount_one;//实付价格
        private  TextView order_status_name_two;//


        private ImageView iv_phone_one,iv_phone;//打电话
        private ImageView iv_dayin;//打印



        RecyclerView GoodsRecyclerview;//商品信息
        RecyclerView SHRecyclerView;//售后信息




        private OrderDetailsAdapter mRvAdapter;//购买商品


        private List<OrderBean.OrderDetailsBean> list = new ArrayList<>();

        private ShouHouOrderAdapter shouHouOrderAdapter;//售后
        private List<OrderBean.OrderRefundBean> RefundList = new ArrayList<>();


        public myViewHodler(View itemView) {
            super(itemView);


            GoodsRecyclerview = itemView.findViewById(R.id.GoodsRecyclerview);

            develop=itemView.findViewById(R.id.develop);
            xiala_layout=itemView.findViewById(R.id.xiala_layout);

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

            //用户信息
            user_name=itemView.findViewById(R.id.user_name);
            user_name_one=itemView.findViewById(R.id.user_name_one);
            user_phone=itemView.findViewById(R.id.user_phone);
            user_phone_one=itemView.findViewById(R.id.user_phone_one);
            distance=itemView.findViewById(R.id.distance);
            user_address=itemView.findViewById(R.id.user_address);
            remarks=itemView.findViewById(R.id.remarks);
            remarks_layout=itemView.findViewById(R.id.remarks_layout);
            serviceTime=itemView.findViewById(R.id.serviceTime);
            order_status_name=itemView.findViewById(R.id.order_status_name);
            //商品信息
            goods_amount=itemView.findViewById(R.id.goods_amount);
            packing_fee=itemView.findViewById(R.id.packing_fee);
            shipping_fee=itemView.findViewById(R.id.shipping_fee);
            reduced_amount=itemView.findViewById(R.id.reduced_amount);
            pay_channel=itemView.findViewById(R.id.pay_channel);
            pay_amount=itemView.findViewById(R.id.pay_amount);
            OrderTime=itemView.findViewById(R.id.OrderTime);
            pay_channel_name=itemView.findViewById(R.id.pay_channel_name);
            pay_amount_one=itemView.findViewById(R.id.pay_amount_one);
            //订单信息
            Order_taking_name=itemView.findViewById(R.id.Order_taking_name);
            Order_taking_name_one=itemView.findViewById(R.id.Order_taking_name_one);
            layout_all=itemView.findViewById(R.id.layout_all);
            top_user_info_layout=itemView.findViewById(R.id.top_user_info_layout);
            DeliveryInformationLayout=itemView.findViewById(R.id.DeliveryInformationLayout);
            cancel_cause_layout=itemView.findViewById(R.id.cancel_cause_layout);
            tv_cancel_cause=itemView.findViewById(R.id.tv_cancel_cause);

            iv_phone_one=itemView.findViewById(R.id.iv_phone_one);
            iv_phone=itemView.findViewById(R.id.iv_phone);

            ziti_layout=itemView.findViewById(R.id.ziti_layout);
            iv_dayin=itemView.findViewById(R.id.iv_dayin);

            goods_sum=itemView.findViewById(R.id.goods_sum);
            order_status_name_two=itemView.findViewById(R.id.order_status_name_two);

            close_time_layout=itemView.findViewById(R.id.close_time_layout);
            SHRecyclerView=itemView.findViewById(R.id.SHRecyclerView);

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
        void onClick(OrderBean lists, int position,int type);
    }

    public void setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }

    private OnClickListener mClickListener;


    //退款的监听的回调
    public interface DrawbackOnClickListener {
        void DrawbackonOnClick(OrderBean.OrderRefundBean lists, int position,int type);
    }

    public void setDrawbackOnClickListener(DrawbackOnClickListener listener) {
        mDrawbackClickListener = listener;
    }

    private DrawbackOnClickListener mDrawbackClickListener;


    //打电话的监听的回调
    public interface PhoneOnClickListener {
        void PhoneOnClick(OrderBean lists, int position);
    }

    public void setPhoneOnClickListener(PhoneOnClickListener listener) {
        mPhoneOnClickListener = listener;
    }

    private PhoneOnClickListener mPhoneOnClickListener;


    //打印的监听的回调
    public interface StampOnClickListener {
        void StampOnClick(OrderBean lists, int position);
    }

    public void setStampOnClickListener(StampOnClickListener listener) {
        mStampOnClickListener = listener;
    }

    private StampOnClickListener mStampOnClickListener;


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
        public void OnItemClick(View view, OrderBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

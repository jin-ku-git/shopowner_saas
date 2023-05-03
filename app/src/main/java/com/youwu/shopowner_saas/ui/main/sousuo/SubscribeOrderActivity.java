package com.youwu.shopowner_saas.ui.main.sousuo;


import static com.youwu.shopowner_saas.utils_view.TimeUtil.getBeginDayOfTomorrow;
import static com.youwu.shopowner_saas.utils_view.TimeUtil.getDayBegin;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.XPopupUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityOrderSubscribeBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.adapter.OneOrderAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;
import com.youwu.shopowner_saas.ui.fragment.custom.CustomBubbleAttachPopup;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;
import com.youwu.shopowner_saas.utils_view.TimeUtil;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * 2023/02/23
 * 预订单
 * 金库
 */

public class SubscribeOrderActivity extends BaseActivity<ActivityOrderSubscribeBinding, SubscribeOrderViewModel> {

    OneOrderAdapter oneOrderAdapter;
    private ArrayList<OrderBean> orderBeans = new ArrayList<>();

    Context mContext;

    private String appointment_time;//预约配送日期

    int num=0;
    int default_option=1;//默认降序


    int page=1;//页数
    int limit=10;//每页多少条
    @Override
    public SubscribeOrderViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SubscribeOrderViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_subscribe;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initParam() {
        super.initParam();
        if (num!=0){
            initDatainfo();
        }
    }


    @Override
    public void initData() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色
        // 可以调用该方法，设置不允许滑动退出
        setSwipeBackEnable(false);
        mContext=this;
        viewModel.open_close.set("展开");

        viewModel.null_type.set(1);
        viewModel.DidData.set(1);
        appointment_time=getTime(getBeginDayOfTomorrow());
        initDatainfo();

        initWMRecyclerView();
        binding.mainSmartrefreshlayout.setEnableAutoLoadMore(false);
        binding.mainSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                initDatainfo();
                refreshLayout.finishRefresh(true);
            }
        });
        //加载
        binding.mainSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                //获取订单列表
                initDatainfo();
                refreshLayout.finishLoadMore(true);//加载完成
            }
        });

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initDatainfo() {
        num++;
            viewModel.new_order_list(appointment_time,page,limit);
    }

    /**
     * 小程序订单
     */
    private void initWMRecyclerView() {

        sortList(default_option);


        //创建adapter
        oneOrderAdapter = new OneOrderAdapter(this, orderBeans,0);
        //给RecyclerView设置adapter
        binding.orderRecyclerView.setAdapter(oneOrderAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.orderRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.orderRecyclerView.getItemDecorationCount() == 0) {
            binding.orderRecyclerView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        oneOrderAdapter.setOnClickListener(new OneOrderAdapter.OnClickListener() {
            @Override
            public void onClick(OrderBean data, int position, int type) {
                switch (type){
                    case 2://接单
                        viewModel.new_update_order(type,data.getOrder_sn(),"");
                        break;
                    case 3://拒单
                        SelectionDialog(data,type);
                        break;
                    case 4://出餐
                        viewModel.new_update_order(type,data.getOrder_sn(),"");
                        break;
                }

            }

        });
        oneOrderAdapter.setPhoneOnClickListener(new OneOrderAdapter.PhoneOnClickListener() {
            @Override
            public void PhoneOnClick(OrderBean lists, int position) {
                callPhone(lists.getMember_phone());
            }
        });

    }


    @Override
    public void initViewObservable() {

        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){

                    case 1://排序
                        KLog.i("排序");
                        new XPopup.Builder(mContext)
//                        .isCenterHorizontal(true)
//                                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                                .atView(binding.bthSort)
                                .hasShadowBg(false) // 去掉半透明背景
//                        .offsetX(XPopupUtils.dp2px(getContext(), 20))
                                .offsetY(XPopupUtils.dp2px(mContext, 6))
                                .asCustom(new CustomBubbleAttachPopup(mContext)
//                                .setArrowOffset(-XPopupUtils.dp2px(getContext(), 40))  //气泡箭头偏移
                                                .setBubbleBgColor(getResources().getColor(R.color.main_white))  //气泡背景
                                                .setArrowWidth(XPopupUtils.dp2px(mContext, 5))
                                                .setArrowHeight(XPopupUtils.dp2px(mContext, 6))
//                                .setBubbleRadius(100)
                                                .setArrowRadius(XPopupUtils.dp2px(mContext, 3))
                                )
                                .show();

                        CustomBubbleAttachPopup popup= (CustomBubbleAttachPopup) new XPopup.Builder(mContext)

                                .atView(binding.bthSort)
                                .hasShadowBg(false) // 去掉半透明背景
                                .offsetY(XPopupUtils.dp2px(mContext, 6))
                                .asCustom(new CustomBubbleAttachPopup(mContext)
                                        .setBubbleBgColor(getResources().getColor(R.color.main_white))  //气泡背景
                                        .setArrowWidth(XPopupUtils.dp2px(mContext, 5))
                                        .setArrowHeight(XPopupUtils.dp2px(mContext, 6))
                                        .setArrowRadius(XPopupUtils.dp2px(mContext, 3))
                                );
                        popup.setOnChoiceener(new CustomBubbleAttachPopup.OnChoiceener() {
                            @Override
                            public void onChoice(int data) {
                                KLog.i("传输的数据"+data);
                                sortList(data);
                                default_option=data;

                            }
                        });
                        popup.show();

                        break;
                    case 2://展开
                        for (int i=0;i<orderBeans.size();i++){
                            orderBeans.get(i).setType(1);
                        }

                        KLog.d("展开了");
                        oneOrderAdapter.notifyDataSetChanged();
                        break;
                    case 3://收起
                        for (int i=0;i<orderBeans.size();i++){
                            orderBeans.get(i).setType(0);
                        }

                        KLog.d("收起了");
                        oneOrderAdapter.notifyDataSetChanged();
                        break;

                    case 4://出餐成功
                    case 5://接单成功
                    case 6://拒单
                        initDatainfo();
                        break;
                }
            }
        });

        viewModel.getOrder_list.observe(this, new Observer<ArrayList<OrderBean>>() {
            @Override
            public void onChanged(ArrayList<OrderBean> List) {


                if (page==1){
                    orderBeans.clear();
                    orderBeans.addAll(List);
                }else {
                    orderBeans.addAll(List);

                }
                if (orderBeans.size()==0){
                    viewModel.null_type.set(0);
                }else {
                    viewModel.null_type.set(1);
                }
                if (List.size()<limit){
                    viewModel.DidData.set(0);
                    binding.mainSmartrefreshlayout.setEnableLoadMore(false);//是否启用上拉加载功能
                }else {
                    viewModel.DidData.set(1);
                    binding.mainSmartrefreshlayout.setEnableLoadMore(true);//是否启用上拉加载功能
                }

                sortList(default_option);

                oneOrderAdapter.notifyDataSetChanged();


            }
        });

    }

    /**
     * 排序
     * @param data  //1、降序  2、升序
     */
    private void sortList(int data) {
        if (data==1){//降序
            //通过比较器比较时间
            Collections.sort(orderBeans, new Comparator<OrderBean>() {
                @Override
                public int compare(OrderBean o1, OrderBean o2) {
                    Date date1 = TimeUtil.stringToDate(o1.getCreated_at());
                    Date date2 = TimeUtil.stringToDate(o2.getCreated_at());
                    //按照降序排列，如果按升序排列用after即可
                    if (date1.before(date2)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });

        }else {//升序
            Collections.sort(orderBeans, new Comparator<OrderBean>() {
                @Override
                public int compare(OrderBean o1, OrderBean o2) {
                    Date date1 = TimeUtil.stringToDate(o1.getCreated_at());
                    Date date2 = TimeUtil.stringToDate(o2.getCreated_at());
                    //按照降序排列，如果按升序排列用after即可
                    if (date1.after(date2)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
        }
        if (oneOrderAdapter!=null){
            oneOrderAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 拒单弹窗
     */
    private void SelectionDialog(OrderBean data,int type) {

        final Dialog dialog = new Dialog(this, R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_resist_order, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);
        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths * 0.8);
        layoutParams.height = (int) (height * 0.4);
        //将界面填充到AlertDiaLog容器
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);//点击外部消失弹窗
        dialog.show();

        //初始化控件
        TextView cancel_text = (TextView) dialogView.findViewById(R.id.cancel_text);//返回
        TextView determine_text = (TextView) dialogView.findViewById(R.id.determine_text);//
        EditText edit_text = (EditText) dialogView.findViewById(R.id.edit_text);//


        //返回
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        determine_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(edit_text.getText().toString())||edit_text.getText().toString()==null){
                    RxToast.normal("请填写拒绝原因");
                }else {
                    dialog.dismiss();
                    viewModel.new_update_order(type,data.getOrder_sn(),edit_text.getText().toString());

                    KLog.i("拒单理由："+edit_text.getText().toString());
                }
            }
        });

    }

}

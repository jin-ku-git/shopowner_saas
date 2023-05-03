package com.youwu.shopowner_saas.ui.fragment;


import static com.youwu.shopowner_saas.utils_view.TimeUtil.getTimeList;
import static com.youwu.shopowner_saas.utils_view.TimeUtil.getTimeYearList;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.FragmentOneBinding;
import com.youwu.shopowner_saas.service.Bean;
import com.youwu.shopowner_saas.service.Constant;
import com.youwu.shopowner_saas.service.HttpCallback;
import com.youwu.shopowner_saas.service.HttpHelper;
import com.youwu.shopowner_saas.service.MyHashMap;
import com.youwu.shopowner_saas.toast.RxToast;

import com.youwu.shopowner_saas.ui.fragment.adapter.OneOrderAdapter;
import com.youwu.shopowner_saas.ui.fragment.adapter.OneShouHouOrderAdapter;
import com.youwu.shopowner_saas.ui.fragment.adapter.SpinnerAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.MqttBean;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;
import com.youwu.shopowner_saas.ui.fragment.bean.SpinnerBean;
import com.youwu.shopowner_saas.ui.fragment.custom.CustomBubbleAttachPopup;
import com.youwu.shopowner_saas.ui.main.sousuo.OrderSouSuoActivity;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.TimeUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * 2022/09/12
 */

public class OneFragment extends BaseFragment<FragmentOneBinding,OneViewModel> {


    private int order_aftermarket=1;// 1订单 2售后
    private String appointment_time;//预约配送日期
    List<String> TimeList=new ArrayList<>();

    String  StoreId;//店铺id


    OneOrderAdapter oneOrderAdapter;
    OneShouHouOrderAdapter oneShouHouOrderAdapter;//售后

    int widths;//屏幕长
    int height;//屏幕宽

    private ArrayList<OrderBean> orderBeans = new ArrayList<>();


    private ArrayList<OrderBean> RefundOrderBeans = new ArrayList<>();


    int num=0;

    int page=1;//页数
    int limit=10;//每页多少条

    int default_option=1;


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_one;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public OneViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(OneViewModel.class);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (num!=0){
            KLog.a("onResume();");
            initDatainfo();
        }

        viewModel.new_store_info();
    }
    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 0://搜索
                        Bundle bundle=new Bundle();
                        bundle.putInt("type",order_aftermarket);
                        startActivity(OrderSouSuoActivity.class,bundle);
                        break;
                    case 1://排序
                        CustomBubbleAttachPopup popup= (CustomBubbleAttachPopup) new XPopup.Builder(getContext())

                                .atView(binding.bthSort)
                                .hasShadowBg(false) // 去掉半透明背景
                                .offsetY(XPopupUtils.dp2px(getContext(), 6))
                                .asCustom(new CustomBubbleAttachPopup(getContext())
                                                .setBubbleBgColor(getResources().getColor(R.color.main_white))  //气泡背景
                                                .setArrowWidth(XPopupUtils.dp2px(getContext(), 5))
                                                .setArrowHeight(XPopupUtils.dp2px(getContext(), 6))
                                                .setArrowRadius(XPopupUtils.dp2px(getContext(), 3))
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
                    case 7://退款审核同意
                    case 8://退款审核拒绝
                        page=1;
                        initDatainfo();
                        break;
                    case 9:
                    case 10:
                        page=1;
                        break;



                }
            }
        });
        //全部、待接单、带出餐
        viewModel.status_order.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==0){
                    binding.wholeLayout.setVisibility(View.VISIBLE);
                }else {
                    binding.wholeLayout.setVisibility(View.GONE);
                }
                page=1;
                initDatainfo();
            }
        });

        //订单
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
        //售后订单
        viewModel.RefundOrderBeans_list.observe(this, new Observer<ArrayList<OrderBean>>() {
            @Override
            public void onChanged(ArrayList<OrderBean> orderBeans) {
                KLog.d("page:"+page);
                if (page==1){
                    RefundOrderBeans.clear();
                    RefundOrderBeans.addAll(orderBeans);
                }else {
                    for(int i=0;i<orderBeans.size();i++){
                        RefundOrderBeans.add(orderBeans.get(i));
                    }
                }

                if (RefundOrderBeans.size()==0){
                    viewModel.null_type.set(0);
                }else {
                    viewModel.null_type.set(1);
                }

                if (orderBeans.size()<limit){
                    viewModel.SHDidData.set(0);
                    binding.refundSmartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
                }else {
                    viewModel.SHDidData.set(1);
                    binding.refundSmartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
                }

                oneShouHouOrderAdapter.notifyDataSetChanged();


            }
        });
        //全部、已取消
        viewModel.status_order_one.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                page=1;
                initDatainfo();
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

    @Override
    public void initData() {
        super.initData();

        viewModel.bth_one.set(1);
        viewModel.bth_two.set(0);
        viewModel.null_type.set(1);
        viewModel.bth_AfterSales.set(1);
        getScreenSize();
        viewModel.getMe();

        viewModel.DJD_num.set(0);
        viewModel.DCC_num.set(0);



       StoreId= AppApplication.spUtils.getString("StoreId");

       viewModel.open_close.set("展开");

        initTabData();
        initSpinner();

//        Collections.reverse(orderBeans); 反转数据

        viewModel.DidData.set(1);
        viewModel.SHDidData.set(1);


        initWMRecyclerView();
        initASRecyclerView();
        binding.refundSmartRefreshLayout.setEnableAutoLoadMore(false);
        binding.mainSmartrefreshlayout.setEnableAutoLoadMore(false);
        binding.refundSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                initDatainfo();
                refreshLayout.finishRefresh(true);
            }
        });
        //加载
        binding.refundSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                //获取订单列表
                initDatainfo();
                refreshLayout.finishLoadMore(true);//加载完成
            }
        });

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

        //检查是否已经注册
        if(!EventBus.getDefault().isRegistered(this)){//是否注册eventbus的判断
            EventBus.getDefault().register(this);
        }

    }
    //时间下拉框选择
    private void initSpinner() {

        List<String> lists= getTimeList(7);

        TimeList= getTimeYearList(7);
        TimeList.add(0,"只可以选择7天以内日期");
        appointment_time=TimeList.get(1);
        List<SpinnerBean> list=new ArrayList<>();
        SpinnerBean Bean=new SpinnerBean();
        Bean.setId(0);
        Bean.setName("只可以选择7天以内日期");
        list.add(Bean);
        for (int i=0;i<lists.size();i++){
            KLog.d("时间："+lists.get(i));
            SpinnerBean spinnerBean=new SpinnerBean();
            spinnerBean.setId(i+1);
            spinnerBean.setName(lists.get(i));
            spinnerBean.setSelect(false);
            list.add(spinnerBean);
        }



        SpinnerAdapter adapter=new SpinnerAdapter(getContext(),list);


        binding.DropdownBox.setAdapter(adapter);
        binding.DropdownBox.setSelection(1);

        binding.DropdownBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (int i=0;i<list.size();i++){
                    list.get(i).setSelect(false);
                }
                list.get(pos).setSelect(true);

                appointment_time=TimeList.get(pos);

                initDatainfo();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    private void initTabData() {


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //0订单 1售后

                switch (tab.getPosition()){
                    case 0://订单
                        page=1;
                        order_aftermarket=1;
                        binding.AfterSalesLayout.setVisibility(View.GONE);
                        binding.OrderLayout.setVisibility(View.VISIBLE);
                        initDatainfo();
                        break;
                    case 1://售后
                        page=1;
                        order_aftermarket=2;
                        binding.AfterSalesLayout.setVisibility(View.VISIBLE);
                        binding.OrderLayout.setVisibility(View.GONE);
                        initDatainfo();
                        break;
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initDatainfo() {
        num++;
        if (order_aftermarket==1){
            viewModel.getOrderNum(appointment_time);
            viewModel.new_order_list(appointment_time,viewModel.bth_one.get(),viewModel.bth_two.get(),page,limit);
        }else {
            viewModel.new_refund_order_list(page,limit);
        }


    }

    /**
     * 获取屏幕长和高
     */
    private void getScreenSize() {
        //获取屏幕宽高
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widths = size.x;
        height = size.y;
    }



    //MainActivity传递的数据
    @Subscribe
    public void onString(String  type) {
        KLog.d("MainActivity传递1："+type);
        if ("1".equals(type)){
            page=1;
            initDatainfo();
        }
    };
    //MainActivity传递的数据
    @Subscribe
    public void onMQttBean(MqttBean mqttBean) {
        KLog.d("MainActivity传递："+mqttBean);
        page=1;
        initDatainfo();
    };


    /**
     * 订单列表
     */
    private void initWMRecyclerView() {


        //创建adapter
        oneOrderAdapter = new OneOrderAdapter(getContext(), orderBeans,viewModel.bth_two.get());
        //给RecyclerView设置adapter
        binding.mainRecyclerView.setAdapter(oneOrderAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        binding.mainRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //设置item的分割线
        if (binding.mainRecyclerView.getItemDecorationCount() == 0) {
            binding.mainRecyclerView.addItemDecoration(new DividerItemDecorations(getContext(), DividerItemDecorations.VERTICAL));
        }
        binding.mainRecyclerView.setFocusableInTouchMode(false);
        binding.mainRecyclerView.requestFocus();



        oneOrderAdapter.setOnClickListener(new OneOrderAdapter.OnClickListener() {
            @Override
            public void onClick(OrderBean data, int position,int type) {
                switch (type){
                    case 2://接单
                        viewModel.new_update_order(type,data.getOrder_sn(),"");
                        break;
                    case 3://拒单
                        SelectionDialog(data,null,type);
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
        oneOrderAdapter.setStampOnClickListener(new OneOrderAdapter.StampOnClickListener() {
            @Override
            public void StampOnClick(OrderBean lists, int position) {
                PrintOrder(lists.getOrder_sn());

            }
        });
    }

    /**
     * 打印
     * @param order_sn  订单id
     */
    private void PrintOrder(String order_sn) {
        //访问网络
        String url = Constant.URL_ZONG + "saas_order_info/printOrder";
        Log.i("打印小票",url);
        MyHashMap<String> mParams = new MyHashMap<String>();
        mParams.put("order_id",order_sn);

        //访问网络
        HttpHelper.obtain().post(url,
                mParams, new HttpCallback<Bean>() {
                    @Override
                    public void onSuccess(Bean expressBean) {
                        String JsonData = new Gson().toJson(expressBean);
                        KLog.i("打印结果："+JsonData);

                        if (expressBean.rc == 0) {
                            Log.i("onSuccess: ", expressBean.result.toString());

                            RxToast.normal("打印成功");
                        } else{
                            RxToast.normal(expressBean.des);
                        }
                    }
                    @Override
                    public void onFailed(String string) {
                        RxToast.normal("网络请求失败，请检查网络");

                    }
                });
    }

    /**
     * 售后订单
     */
    private void initASRecyclerView() {


        //创建adapter
        oneShouHouOrderAdapter = new OneShouHouOrderAdapter(getContext(), RefundOrderBeans,viewModel.bth_two.get());
        //给RecyclerView设置adapter
        binding.AfterSalesRecyclerView.setAdapter(oneShouHouOrderAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.AfterSalesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.AfterSalesRecyclerView.getItemDecorationCount() == 0) {
            binding.AfterSalesRecyclerView.addItemDecoration(new DividerItemDecorations(getContext(), DividerItemDecorations.VERTICAL));
        }

        oneShouHouOrderAdapter.setDrawbackOnClickListener(new OneShouHouOrderAdapter.DrawbackOnClickListener() {
            @Override
            public void DrawbackonOnClick(OrderBean.OrderRefundBean lists, int position, int type) {
                if (type==5){//拒绝
                    SelectionDialog(null,lists,type);
                }else {//同意
                    new  XPopup.Builder(getContext())

                            .asConfirm("提示", "请确认是否同意退款", "取消", "同意退款", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {

                                    viewModel.new_audit_refund(1,lists.getRefund_sn(),"");
                                }
                            }, null,false)
                            .show();
                }

            }
        });
        oneShouHouOrderAdapter.setPhoneOnClickListener(new OneShouHouOrderAdapter.PhoneOnClickListener() {
            @Override
            public void PhoneOnClick(OrderBean lists, int position) {
                callPhone(lists.getMember_phone());
            }
        });



    }


    /**
     * 拒单弹窗
     */
    private void SelectionDialog(OrderBean data,OrderBean.OrderRefundBean lists,int type) {

        final Dialog dialog = new Dialog(getContext(), R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_resist_order, null);
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
        TextView top_text = (TextView) dialogView.findViewById(R.id.top_text);//
        EditText edit_text = (EditText) dialogView.findViewById(R.id.edit_text);//


        if (data==null){
            top_text.setText("拒绝退款");
        }else if (lists==null){
            top_text.setText("拒单");
        }


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
                    if (data==null){//退款审核
                        viewModel.new_audit_refund(2,lists.getRefund_sn(),edit_text.getText().toString());
                    }else if (lists==null){
                        viewModel.new_update_order(type,data.getOrder_sn(),edit_text.getText().toString());
                    }


                    KLog.i("拒单理由："+edit_text.getText().toString());
                }


            }
        });

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
    }

}

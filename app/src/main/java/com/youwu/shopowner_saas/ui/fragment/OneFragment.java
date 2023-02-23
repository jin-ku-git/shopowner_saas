package com.youwu.shopowner_saas.ui.fragment;


import static me.goldze.mvvmhabit.base.BaseActivity.toPrettyFormat;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.FragmentOneBinding;
import com.youwu.shopowner_saas.queue.LogTask;
import com.youwu.shopowner_saas.queue.TaskPriority;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.adapter.WMOrderAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.MqttBean;
import com.youwu.shopowner_saas.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner_saas.ui.order_goods.OrderDetailsActivity;
import com.youwu.shopowner_saas.ui.order_goods.OrderReceivingActivity;
import com.youwu.shopowner_saas.ui.order_goods.RefundOrderDetailsActivity;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * 2022/09/12
 */

public class OneFragment extends BaseFragment<FragmentOneBinding,OneViewModel> {


    String  StoreId;//店铺id


    WMOrderAdapter wmOrderAdapter;
    int widths;//屏幕长
    int height;//屏幕宽

    private ArrayList<XXCOrderBean> xxcOrderBeans = new ArrayList<>();

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
        initWMData();

    }

    @Override
    public void initData() {
        super.initData();
        /**
         * 检查更新
         */
//        viewModel.getAppVersion();
        viewModel.order_status.set(1);
        getScreenSize();
        viewModel.getMe();
        initWMData();
       StoreId= AppApplication.spUtils.getString("StoreId");
        viewModel.goods_count(StoreId);
        //检查是否已经注册
        if(!EventBus.getDefault().isRegistered(this)){//是否注册eventbus的判断
            EventBus.getDefault().register(this);
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

    /**
     * 获取小程序订单列表
     *
     */
    private void initWMData() {
        viewModel.getxcx_order_count();

        viewModel.xcx_order_list(viewModel.order_status.get() + "");
    }

    //MainActivity传递的数据
    @Subscribe
    public void onString(String  type) {
        KLog.d("MainActivity传递1："+type);
        if ("1".equals(type)){
            initWMData();
        }
    };
    //MainActivity传递的数据
    @Subscribe
    public void onMQttBean(MqttBean mqttBean) {
        KLog.d("MainActivity传递："+mqttBean);

        initWMData();
    };

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://待接单

                        viewModel.order_status.set(1);
                        initWMData();
                        break;
                        case 2://待出餐

                            viewModel.order_status.set(2);
                            initWMData();
                        break;
                        case 3://退款
                            viewModel.order_status.set(3);
                            initWMData();
                        break;
                    case 4:

                        new LogTask("DEFAULT","您有一个待处理订单，请及时处理！")
                                .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
                                .enqueue(); //入队
                        break;
                    case 5:
                        initWMData();
                        break;
                    case 6://同意
                        initWMData();
                        RxToast.showTipToast(getActivity(),"已同意！");
                        break;
                    case 7://拒绝
                        initWMData();
                        RxToast.showTipToast(getActivity(),"已拒绝！");
                        break;

                }
            }
        });
        //小程序订单列表返回
        viewModel.xxc_order_list.observe(this, new Observer<ArrayList<XXCOrderBean>>() {
            @Override
            public void onChanged(ArrayList<XXCOrderBean> xxcOrder) {
                xxcOrderBeans = xxcOrder;

                initWMRecyclerView();

            }
        });

    }
    /**
     * 小程序订单
     */
    private void initWMRecyclerView() {


        //创建adapter
        wmOrderAdapter = new WMOrderAdapter(getContext(), xxcOrderBeans);
        //给RecyclerView设置adapter
        binding.wmRecyclerView.setAdapter(wmOrderAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.wmRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.wmRecyclerView.getItemDecorationCount() == 0) {
            binding.wmRecyclerView.addItemDecoration(new DividerItemDecorations(getContext(), DividerItemDecorations.VERTICAL));
        }

        wmOrderAdapter.setOnItemClickListener(new WMOrderAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, XXCOrderBean data, int position) {

                    Bundle bundle=new Bundle();
                    bundle.putString("order_sn",data.getOrder_sn());
                    startActivity(OrderDetailsActivity.class,bundle);

            }
        });

        wmOrderAdapter.setOnClickListener(new WMOrderAdapter.OnClickListener() {
            @Override
            public void onClick(final XXCOrderBean data, int position, final int status) {

                if (status==3){
                    new  XPopup.Builder(getContext())
                            .maxWidth((int) (widths * 0.8))
                            .maxHeight((int) (height*0.5))
                            .asConfirm("提示", "拒单会退款，是否拒单?", "取消", "确认", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    viewModel.edit_order_status(data.getOrder_sn(), status + "");
                                }
                            }, null,false)
                            .show();
                }else if (status==4){
                    new  XPopup.Builder(getContext())
                            .maxWidth((int) (widths * 0.8))
                            .maxHeight((int) (height*0.5))
                            .asConfirm("提示", "是否出餐？", "取消", "确认", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    viewModel.edit_order_status(data.getOrder_sn(), status + "");
                                }
                            }, null,false)
                            .show();

                }else {
                    viewModel.edit_order_status(data.getOrder_sn(), status + "");
                }


            }
        });

        wmOrderAdapter.setSelectionStatusOnClickListener(new WMOrderAdapter.SelectionStatusOnClickListener() {
            @Override
            public void SelectionStatusonClick(XXCOrderBean lists, int position, int status) {

                SelectionDialog(lists.getOrder_sn(),status);
            }
        });

    }
    SmoothCheckBox yes_check;
    SmoothCheckBox no_check;
    /**
     * 退款拒绝还是同意弹窗
     */
    private void SelectionDialog(final String order_sn, final int status) {

        final Dialog dialog = new Dialog(getContext(), R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_selection, null);
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
        TextView close_text = (TextView) dialogView.findViewById(R.id.close_text);//返回
        TextView selection_name = (TextView) dialogView.findViewById(R.id.selection_name);//
        TextView content = (TextView) dialogView.findViewById(R.id.content);//
        LinearLayout refund_layout = (LinearLayout) dialogView.findViewById(R.id.refund_layout);//
        LinearLayout agree_layout = (LinearLayout) dialogView.findViewById(R.id.agree_layout);//
        final EditText reason = (EditText) dialogView.findViewById(R.id.reason);//
        final TextView cancel = (TextView) dialogView.findViewById(R.id.cancel);//取消

        yes_check = dialog.findViewById(R.id.yes_check);
        no_check = dialog.findViewById(R.id.no_check);
        yes_check.setChecked(true);
        no_check.setChecked(false);

        final TextView confirm = (TextView) dialogView.findViewById(R.id.confirm);//确定
        if (status == 1) {
            selection_name.setText("同意");
            agree_layout.setVisibility(View.VISIBLE);
            refund_layout.setVisibility(View.GONE);

        } else {
            selection_name.setText("拒绝");
            agree_layout.setVisibility(View.GONE);
            refund_layout.setVisibility(View.VISIBLE);
        }
        //返回
        close_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yes_check.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {

                if (isChecked){
                    no_check.setChecked(false);
                }
                KLog.d("同意：" + yes_check.isChecked() + "\n拒绝：" + no_check.isChecked());
            }
        });
        no_check.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked){
                    yes_check.setChecked(false);
                }
                KLog.d("同意：" + yes_check.isChecked() + "\n拒绝：" + no_check.isChecked());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refund_reason;
                refund_reason=reason.getText().toString();
                if (refund_reason==null){
                    refund_reason="";
                }
                viewModel.audit_order_refund(status,order_sn,refund_reason,yes_check.isChecked()?"1":"2",dialog);

                KLog.d("同意：" + yes_check.isChecked() + "\n拒绝：" + no_check.isChecked());

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

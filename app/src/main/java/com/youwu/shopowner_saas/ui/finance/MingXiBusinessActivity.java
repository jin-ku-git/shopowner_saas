package com.youwu.shopowner_saas.ui.finance;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityMingXiBusinessBinding;
import com.youwu.shopowner_saas.databinding.ActivityPingJiaBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.finance.adapter.MingXiYingYeAdapter;
import com.youwu.shopowner_saas.ui.ping_jia.PingJiaViewModel;
import com.youwu.shopowner_saas.ui.ping_jia.adapter.PingJiaAdapter;
import com.youwu.shopowner_saas.utils.Constant;
import com.youwu.shopowner_saas.utils_view.CalendarView;
import com.youwu.shopowner_saas.utils_view.DateUtils;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;
import com.youwu.shopowner_saas.utils_view.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 营业明细页面
 * 2023/03/17
 */
public class MingXiBusinessActivity extends BaseActivity<ActivityMingXiBusinessBinding, MingXiBusinessViewModel> {

    MingXiYingYeAdapter mAdapter;
    List<String> list=new ArrayList<>();


    private String starTime;    //开始时间文字
    private String endTime;     //结束时间文字
    private boolean isSelecgOk = false;

    private String StartTime;//开始时间时间戳
    private String EndTime;//结束时间时间戳

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_ming_xi_business;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MingXiBusinessViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MingXiBusinessViewModel.class);
    }

    @Override
    public void initViewObservable() {

        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://搜索
                        RxToast.normal("搜索");
                        break;
                    case 2://自定义
                        showTimeDialog();
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色
        viewModel.null_type.set(0);
        list=getDemoData();
        initView();
    }

    private void initView() {
        if (list.size()==0){
            viewModel.null_type.set(0);
        }else {
            viewModel.null_type.set(1);
        }

        mAdapter=new MingXiYingYeAdapter(this,list);


        //给RecyclerView设置adapter
        binding.pingJiaRecyclerView.setAdapter(mAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.pingJiaRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.pingJiaRecyclerView.getItemDecorationCount() == 0) {
            binding.pingJiaRecyclerView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }

    }

    private List<String> getDemoData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("第" + i + "条数据");
        }
        return list;
    }


    private TextView tv_startime,tv_endtime;


    /**
     * 时间弹窗
     */
    private void showTimeDialog() {



        Dialog dialog = new Dialog(this, R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_start_end_time, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);
        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths);
//        layoutParams.height = (int) (height*0.8);
        //将界面填充到AlertDiaLog容器
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCancelable(false);//点击外部消失弹窗
        dialog.show();

        //初始化控件
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);//取消
        TextView tv_define = (TextView) dialogView.findViewById(R.id.tv_define);//确定
        CalendarView calendarview=dialogView.findViewById(R.id.calendarview);

        calendarview.setMaxSelect(4);
        tv_startime=dialogView.findViewById(R.id.tv_startime);
        tv_endtime=dialogView.findViewById(R.id.tv_endtime);


        calendarview.setETimeSelListener(new CalendarView.CalendatEtimSelListener() {
            @Override
            public void onETimeSelect(Date date) {
                KLog.d("endTime:"+date.getTime());
                if (date != null) {
                    endTime = DateUtils.formatData(date, Constant.TFORMATE_YMD);
                    viewModel.EndTime.set(endTime);
                    tv_endtime.setText(endTime);
                    EndTime= TimeUtil.getTimeStemp(endTime,"yyyy-MM-dd")/1000+"";
                }else {
                    endTime = null;
                }
            }
        });
        calendarview.setSTimeSelListener(new CalendarView.CalendarSTimeSelListener() {
            @Override
            public void onSTimeSelect(Date date) {
                KLog.d("starTime:"+date.getTime());
                if (date != null) {
                    starTime = DateUtils.formatData(date, Constant.TFORMATE_YMD);
                    viewModel.StartTime.set(starTime);
                    tv_startime.setText(starTime);

                    StartTime=TimeUtil.getTimeStemp(starTime,"yyyy-MM-dd")/1000+"";
                }else {
                    starTime = null;
                }
            }
        });
        calendarview.setCalendaSelListener(new CalendarView.CalendaSelListener() {
            @Override
            public void selectStatus(boolean isOk) {

                isSelecgOk = isOk;
                KLog.d("isSelecgOk:"+isSelecgOk);
            }
        });

        //返回
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //交接班并退出
        tv_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelecgOk){
                    RxToast.normal("请选择结束时间");
                    return;
                }
                dialog.dismiss();
                viewModel.bth_one.set(3);
                viewModel.DaysTime.set((TimeUtil.getDays(endTime,starTime)+1)+"");

            }
        });

    }

}


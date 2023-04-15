package com.youwu.shopowner_saas.ui.finance.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.custom.MyAxisValueFormatter;
import com.youwu.shopowner_saas.databinding.FragmentBusinessBinding;
import com.youwu.shopowner_saas.databinding.FragmentMoveAboutBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.marker.DetailsMarkerView;
import com.youwu.shopowner_saas.utils.Constant;
import com.youwu.shopowner_saas.utils_view.CalendarView;
import com.youwu.shopowner_saas.utils_view.ColorUtil;
import com.youwu.shopowner_saas.utils_view.DateUtils;
import com.youwu.shopowner_saas.utils_view.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 2023/03/17
 * 活动页面
 */

public class MoveAboutFragment extends BaseFragment<FragmentMoveAboutBinding, MoveAboutFragmentViewModel>  {


    List<Entry> listOne = new ArrayList<>();
    List<Entry> listTwo = new ArrayList<>();
    //获取日期
    List<String> Datelist= new ArrayList<>();


    private String starTime;    //开始时间文字
    private String endTime;     //结束时间文字
    private boolean isSelecgOk = false;

    private String StartTime;//开始时间时间戳
    private String EndTime;//结束时间时间戳

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_move_about;
    }
    @Override
    public MoveAboutFragmentViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MoveAboutFragmentViewModel.class);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }



    @Override
    public void initViewObservable() {

        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://自定义
                        showTimeDialog();
                        break;
                }
            }
        });
    }
    @Override
    public void initData() {


        viewModel.hd_name.set("活动订单");

        initChart();
        initChartBackground();


    }


    private void initChart() {

        for (int i = 0; i < 10; i++) {
//            Random().nextInt(300));// 是随机0到300之间的数但并不会取到300
         float a=new Random().nextInt(300-100)+100+1;//是1000到800之间的随机数 式子random.nextInt(max - min) + min + 1
         float b=new Random().nextInt(350-150)+150+1;//是1000到800之间的随机数 式子random.nextInt(max - min) + min + 1

            listOne.add(new Entry(i,a));
            listTwo.add(new Entry(i,b));
            Datelist.add(i+"");
        }


        List<LineDataSet> list_data=new ArrayList<>();
        LineDataSet one = new LineDataSet(listOne, "下单新客");//将数据赋值到你的线条上
        LineDataSet two = new LineDataSet(listTwo, "下单老客");//将数据赋值到你的线条上


        list_data.add(one);
        list_data.add(two);


        LineData lineData = new LineData();// //线的总管理
        for (int i=0;i<list_data.size();i++){

            int[] colors= ColorUtil.SALE_COLORS;
            list_data.get(i).setCircleColor(colors[i]);//设置点的颜色
            list_data.get(i).setColor(colors[i]);//设置线的颜色
            list_data.get(i).setDrawValues(false);
            list_data.get(i).setDrawFilled(true); //设置折线图下方颜色填充默认是蓝色
            // 填充背景只支持18以上
            Drawable drawableBlue = ContextCompat.getDrawable(getContext(), R.drawable.bg_tm);
            list_data.get(i).setFillDrawable(drawableBlue);

            lineData.addDataSet(list_data.get(i));
        }

        Description description = new Description();
        description.setText("");//设置该折线图的描述
        binding.chart.setDescription(description);
        binding.chart.setData(lineData);//把线条设置给你的lineChart上
        binding.chart.invalidate();//刷新


    }


    private void initChartBackground() {

        XAxis xAxis = binding.chart.getXAxis();//得到x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的位置，在整个图形的底部
        xAxis.setLabelCount(listOne.size(),false); //设置X轴刻度 第一个参数是想要x轴有多少条刻度,第二个参数true是将刻度数设置为你的第一个参数的数量 ，false是将刻度数设置为你的第一个参数的数量+1（0.0点也要算哦）
        xAxis.setGranularity(1f);//设置x轴坐标间的最小间距
        xAxis.setAxisMaximum(listOne.size()-1);//设置x轴的最大范围
        xAxis.setAxisMinimum(0f);//设置x轴的最小范围
        xAxis.setGridColor(Color.TRANSPARENT);//设置x轴刻度透明

        xAxis.setLabelCount(8);

        //Y轴不是有左右两边嘛，这就是获取左右两边的y轴
        YAxis axisRight = binding.chart.getAxisRight();
        YAxis axisLeft = binding.chart.getAxisLeft();

        axisRight.setEnabled(false);//将右边的y轴隐藏

        //y轴最大值最小值范围
//        axisLeft.setAxisMaximum(Collections.max(max_number));
        axisLeft.setValueFormatter(new MyAxisValueFormatter());
        axisLeft.setAxisMinimum(0f);
        //文字颜色
        axisLeft.setTextColor(Color.parseColor("#000000"));//设置左y轴字的颜色
        axisLeft.setAxisLineColor(Color.BLACK);//y轴颜色
        axisLeft.setGridColor(Color.TRANSPARENT);//y轴线颜色

        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(getContext(), new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue(value);
            }
        },Datelist);


        detailsMarkerView.setChartView(binding.chart);
        binding.chart.setMarker(detailsMarkerView);


    }



    private TextView tv_startime,tv_endtime;


    /**
     * 时间弹窗
     */
    private void showTimeDialog() {



        Dialog dialog = new Dialog(getContext(), R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_start_end_time, null);
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

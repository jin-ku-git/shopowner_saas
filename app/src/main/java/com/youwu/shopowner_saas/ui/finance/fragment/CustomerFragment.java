package com.youwu.shopowner_saas.ui.finance.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.tabs.TabLayout;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.custom.MyAxisValueFormatter;
import com.youwu.shopowner_saas.databinding.FragmentCustomerBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.finance.adapter.GoodsAnalyseAdapter;
import com.youwu.shopowner_saas.ui.fragment.adapter.SpinnerAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.SpinnerBean;
import com.youwu.shopowner_saas.ui.marker.DetailsMarkerView;
import com.youwu.shopowner_saas.utils_view.ColorUtil;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 2023/03/20
 * 顾客页面
 */

public class CustomerFragment extends BaseFragment<FragmentCustomerBinding, CustomerFragmentViewModel> implements OnChartValueSelectedListener {



    List<Entry> listOne = new ArrayList<>();
    List<Entry> listTwo = new ArrayList<>();
    //获取日期
    List<String> Datelist= new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_customer;
    }
    @Override
    public CustomerFragmentViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CustomerFragmentViewModel.class);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }



    @Override
    public void initViewObservable() {

    }
    @Override
    public void initData() {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String dateStr = formatter.format(date);
        viewModel.Time.set(dateStr);


        initChart();
        initChartBackground();

        initTabData();

        //初始化饼状图
        initPieChart();
        setChartData(2, 5);
        //初始化
        initSpinner();

        binding.hpvMathOne.setProgress(50);
        binding.hpvMathTwo.setProgress(16.2f);
        binding.hpvMathThree.setProgress(81.6f);
    }

    private void initSpinner() {
        List<SpinnerBean> list=new ArrayList<>();

        for (int i=0;i<5;i++){
            SpinnerBean Bean=new SpinnerBean();
            Bean.setId(i);
            if (i==0){
                Bean.setName("请选择");
                Bean.setSelect(false);
            }else if (i==1){
                Bean.setName("订单号");
                Bean.setSelect(true);
            }else if (i==2){
                Bean.setName("商品名称");
                Bean.setSelect(false);
            }else if (i==3){
                Bean.setName("手机尾号(4位)");
                Bean.setSelect(false);
            }else if (i==4){
                Bean.setName("顾客地址");
                Bean.setSelect(false);
            }
            list.add(Bean);
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


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.DropdownBoxTwo.setAdapter(adapter);
        binding.DropdownBoxTwo.setSelection(1);

        binding.DropdownBoxTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (int i=0;i<list.size();i++){
                    list.get(i).setSelect(false);
                }
                list.get(pos).setSelect(true);


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initTabData() {

        binding.tabLayout.getTabAt(0).select();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //1 待接单 2 待出餐 3 待退货
                RxToast.normal("tab"+tab.getText());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 初始化图表的样式
     */
    private void initPieChart() {
        binding.chart1.animateY(1000, Easing.EaseInOutQuad);
        binding.chart1.setOnChartValueSelectedListener(this);

        //使用百分百显示
        binding.chart1.setUsePercentValues(true);
        binding.chart1.getDescription().setEnabled(false);
        binding.chart1.setExtraOffsets(5, 10, 5, 5);

        //设置拖拽的阻尼，0为立即停止
        binding.chart1.setDragDecelerationFrictionCoef(0.95f);

        //设置图标中心文字
        binding.chart1.setCenterText(generateCenterSpannableText());
        binding.chart1.setDrawCenterText(true);
        //设置图标中心空白，空心
        binding.chart1.setDrawHoleEnabled(true);
        //设置空心圆的弧度百分比，最大100
        binding.chart1.setHoleRadius(58f);
        binding.chart1.setHoleColor(Color.WHITE);
        //设置透明弧的样式
        binding.chart1.setTransparentCircleColor(Color.WHITE);
        binding.chart1.setTransparentCircleAlpha(110);
        binding.chart1.setTransparentCircleRadius(61f);

        //设置可以旋转
        binding.chart1.setRotationAngle(0);
        binding.chart1.setRotationEnabled(true);
        binding.chart1.setHighlightPerTapEnabled(true);
    }

    protected void setChartData(int count, float range) {
        List<PieEntry> entries = new ArrayList<>();

            //设置数据源
        entries.add(new PieEntry((float) 58.9, "下单新客", getResources().getDrawable(R.drawable.ic_star_green)));

        entries.add(new PieEntry((float) 41.1, "下单老客", getResources().getDrawable(R.drawable.ic_star_green)));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        List<Integer> colors = new ArrayList<>();

        colors.add(ColorUtil.SALE_COLORS[0]);
        colors.add(ColorUtil.SALE_COLORS[1]);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(binding.chart1));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        binding.chart1.setData(data);

        // undo all highlights
        binding.chart1.highlightValues(null);
        binding.chart1.invalidate();
    }


    /**
     * 生成饼图中间的文字
     *
     * @return
     */
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("下单总人数\n17");
        s.setSpan(new RelativeSizeSpan(1.1f), 0, 5, 0);
        s.setSpan(new RelativeSizeSpan(2f), 5, 6, 0);

        return s;
    }


    private void initChart() {

        for (int i = 0; i < 10; i++) {
//            Random().nextInt(300));// 是随机0到300之间的数但并不会取到300
         float a=new Random().nextInt(300-100)+100+1;//是1000到800之间的随机数 式子random.nextInt(max - min) + min + 1
         float b=new Random().nextInt(800-500)+500+1;//是1000到800之间的随机数 式子random.nextInt(max - min) + min + 1

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



    @Override
    public void onValueSelected(Entry e, Highlight h) {
        RxToast.normal("选中了,  x轴值:" + e.getX() + ", y轴值:" + e.getY());
    }

    @Override
    public void onNothingSelected() {

    }
}

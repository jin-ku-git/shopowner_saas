package com.youwu.shopowner_saas.ui.report_form;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivitySalesOverviewBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.utils_view.HorizontalBarView;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 报表页面
 * 2022/06/01
 */
public class SalesOverviewActivity extends BaseActivity<ActivitySalesOverviewBinding, SalesOverviewViewModel> implements OnChartValueSelectedListener {


    String store_id;//店铺id

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sales_overview;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SalesOverviewViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SalesOverviewViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.HoBarEntity.observe(this, new Observer<ArrayList<HorizontalBarView.HoBarEntity>>() {
            @Override
            public void onChanged(ArrayList<HorizontalBarView.HoBarEntity> hoBarEntities) {

                binding.horizontal.setHoBarData(hoBarEntities);
            }
        });
        viewModel.HoBarEntityTwo.observe(this, new Observer<ArrayList<HorizontalBarView.HoBarEntity>>() {
            @Override
            public void onChanged(ArrayList<HorizontalBarView.HoBarEntity> hoBarEntities) {

                binding.horizontalTwo.setHoBarData(hoBarEntities);
            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色

        viewModel.TurnoverEvent.setValue("昨日(元)");
        viewModel.PenEvent.setValue("昨日(笔)");

        viewModel.state.set(1);

        viewModel.sales_situation(viewModel.state.get()+"");

        store_id= AppApplication.spUtils.getString("StoreId");

        viewModel.goods_sale(viewModel.state.get()+"");
        viewModel.package_sale(viewModel.state.get()+"");

        initChart();

    }

    private void initChart() {
        binding.horizontal.setOnItemClickListener(new HorizontalBarView.OnItemClickListener() {
            @Override
            public void onClick(String name, int num) {
                RxToast.normal("商品："+name+"\n销量："+num);
            }
        });
        binding.horizontalTwo.setOnItemClickListener(new HorizontalBarView.OnItemClickListener() {
            @Override
            public void onClick(String name, int num) {
                RxToast.normal("商品："+name+"\n销量："+num);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}

package com.youwu.shopowner_saas.ui.main;


import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityDemoBinding;
import com.youwu.shopowner_saas.databinding.ActivityPingJiaBinding;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;
import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * Demo页面
 * 2023/03/13
 */
public class DemoActivity extends BaseActivity<ActivityDemoBinding, DemoViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_demo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public DemoViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(DemoViewModel.class);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        super.initData();

        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色

    }

}


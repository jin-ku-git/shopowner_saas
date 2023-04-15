package com.youwu.shopowner_saas.ui.set_up;


import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityStoreStatusBinding;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;
import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 门店状态页面
 * 2023/03/22
 */
public class StoreStatusActivity extends BaseActivity<ActivityStoreStatusBinding, StoreStatusViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_status;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public StoreStatusViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(StoreStatusViewModel.class);
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


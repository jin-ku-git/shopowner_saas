package com.youwu.shopowner_saas.ui.set_up;

import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityStoreSetUpBinding;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;
import me.goldze.mvvmhabit.base.BaseActivity;
/**
 * @author: 门店设置页面
 * @date: 2022/8/12
 */
public class StoreSetUpActivity extends BaseActivity<ActivityStoreSetUpBinding, StoreSetUpViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_set_up;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public StoreSetUpViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(StoreSetUpViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){


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

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
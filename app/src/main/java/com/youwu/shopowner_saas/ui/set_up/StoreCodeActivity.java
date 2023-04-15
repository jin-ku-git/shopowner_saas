package com.youwu.shopowner_saas.ui.set_up;


import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityStoreCodeBinding;
import com.youwu.shopowner_saas.utils_view.DonwloadSaveImg;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 门店收款页面
 * 2023/03/21
 */
public class StoreCodeActivity extends BaseActivity<ActivityStoreCodeBinding, StoreCodeViewModel> {


    private String path="https://img2.baidu.com/it/u=1577373388,3492284830&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800";
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_code;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public StoreCodeViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(StoreCodeViewModel.class);
    }

    @Override
    public void initViewObservable() {

        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://下载
                        DonwloadSaveImg.donwloadImg(StoreCodeActivity.this,path);//iPath
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
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色

        Glide.with(this).load(path).placeholder(R.mipmap.loading).into(binding.ivStore);


    }

}


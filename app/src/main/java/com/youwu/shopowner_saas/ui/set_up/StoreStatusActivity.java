package com.youwu.shopowner_saas.ui.set_up;


import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityStoreStatusBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.goods_operate.ReturnGoodsDetailsActivity;
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
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://营业开关
                        String hintName;
                        if (viewModel.TypeEvent.get()){
                            hintName="确认要停止营业嘛？";
                        }else {
                            hintName="确认要开始营业嘛？";
                        }

                        new  XPopup.Builder(StoreStatusActivity.this)
                                .maxWidth((int) (widths * 0.8))
                                .maxHeight((int) (height*0.5))
                                .asConfirm("提示", hintName, "取消", "确认", new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        RxToast.normal("确认");
                                    }
                                }, null,false)
                                .show();
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

        viewModel.TypeEvent.set(true);

    }

}


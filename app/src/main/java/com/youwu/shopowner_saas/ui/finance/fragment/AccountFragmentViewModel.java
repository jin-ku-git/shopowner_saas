package com.youwu.shopowner_saas.ui.finance.fragment;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.entity.FormEntity;
import com.youwu.shopowner_saas.entity.SpinnerItemData;
import com.youwu.shopowner_saas.ui.base.viewmodel.ToolbarViewModel;
import com.youwu.shopowner_saas.ui.finance.ChongZhiActivity;
import com.youwu.shopowner_saas.ui.finance.TiXianActivity;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 2023/03/06
 */

public class AccountFragmentViewModel extends BaseViewModel<DemoRepository> {


    public ObservableField<Integer> bth_one=new ObservableField<>();//状态 1 提现记录 2、充值记录
    public SingleLiveEvent<Integer> status_order_one=new SingleLiveEvent<>();//状态 1 提现记录 2、充值记录

    public ObservableField<Integer> null_type=new ObservableField<>();// 0、显示空视图 1不显示
    public AccountFragmentViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    //全部点击事件
    public BindingCommand bthOneOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(1);
            status_order_one.setValue(1);
        }
    });
    //已取消点击事件
    public BindingCommand bthCanceledOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(2);
            status_order_one.setValue(2);
        }
    });

    //提现点击事件
    public BindingCommand TiXianOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(TiXianActivity.class);
        }
    });
    //充值点击事件
    public BindingCommand ChongzhiOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ChongZhiActivity.class);
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

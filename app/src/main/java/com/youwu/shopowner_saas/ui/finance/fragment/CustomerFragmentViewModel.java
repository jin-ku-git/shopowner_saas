package com.youwu.shopowner_saas.ui.finance.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/20
 */

public class CustomerFragmentViewModel extends BaseViewModel<DemoRepository> {


    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<Integer> null_type=new ObservableField<>();// 0、显示空视图 1不显示

    public ObservableField<Integer> bth_one=new ObservableField<>();//状态 0、下单人数 1、复购人数 2、复购率
    public ObservableField<Integer> bth_two=new ObservableField<>();//状态 0、下单频次 1、距离上次下单时间
    public ObservableField<String> Time=new ObservableField<>();// 今日时间
    public CustomerFragmentViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }


    //下单人数点击事件
    public BindingCommand OrdersNumberOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(0);
        }
    });

    //复购人数点击事件
    public BindingCommand RepurchaseNumberOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(1);
        }
    });

    //复购率点击事件
    public BindingCommand RepurchaseRateOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(2);
        }
    });
    //下单频次点击事件
    public BindingCommand OrderFrequencyOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_two.set(0);
        }
    });
    //距离上次下单时间点击事件
    public BindingCommand OrderDistanceOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_two.set(1);
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

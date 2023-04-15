package com.youwu.shopowner_saas.ui.finance.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.ui.finance.MingXiBusinessActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/17
 */

public class BusinessFragmentViewModel extends BaseViewModel<DemoRepository> {


    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<Integer> null_type=new ObservableField<>();// 0、显示空视图 1不显示

    public ObservableField<Integer> bth_one=new ObservableField<>();//状态 0、昨日 1、近3日 2、近7日 3、自定义
    public ObservableField<Integer> bth_two=new ObservableField<>();//状态 0、按时段 1、按价格订单

    public ObservableField<String> StartTime=new ObservableField<>();//开始时间
    public ObservableField<String> EndTime=new ObservableField<>();//结束时间
    public ObservableField<String> DaysTime=new ObservableField<>();//天数

    public BusinessFragmentViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }


    //昨日点击事件
    public BindingCommand YesterdayOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(0);
        }
    });

    //近3日点击事件
    public BindingCommand LastThreeDaysOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(1);
        }
    });

    //近7日点击事件
    public BindingCommand LastSevenDaysOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(2);
        }
    });

    //自定义点击事件
    public BindingCommand CustomOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //按时段点击事件
    public BindingCommand SessionsOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_two.set(0);
        }
    });
    //按订单价格点击事件
    public BindingCommand OrderPriceOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_two.set(1);
        }
    });
    //明细点击事件
    public BindingCommand MingXiOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(MingXiBusinessActivity.class);
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

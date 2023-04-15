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

public class MoveAboutFragmentViewModel extends BaseViewModel<DemoRepository> {


    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<Integer> null_type=new ObservableField<>();// 0、显示空视图 1不显示

    public ObservableField<Integer> bth_one=new ObservableField<>();//状态 0、昨日 1、近3日 2、近7日 3、自定义
    public ObservableField<String> hd_name=new ObservableField<>();//活动name

    public ObservableField<Integer> bg_one=new ObservableField<>();//状态 0、昨日 1、近3日 2、近7日 3、自定义


    public ObservableField<String> StartTime=new ObservableField<>();//开始时间
    public ObservableField<String> EndTime=new ObservableField<>();//结束时间
    public ObservableField<String> DaysTime=new ObservableField<>();//天数

    public MoveAboutFragmentViewModel(@NonNull Application application, DemoRepository repository) {
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

    //明细点击事件
    public BindingCommand MingXiOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(MingXiBusinessActivity.class);
        }
    });

    //活动订单点击事件
    public BindingCommand BGOneOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bg_one.set(0);
            hd_name.set("活动订单");
        }
    });
    //活动营业额点击事件
    public BindingCommand BGTwoOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bg_one.set(1);
            hd_name.set("活动营业额");
        }
    });
    //活动成本点击事件
    public BindingCommand BGThreeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bg_one.set(2);
            hd_name.set("活动成本");
        }
    });
    //活动客单价点击事件
    public BindingCommand BGFourOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bg_one.set(3);
            hd_name.set("活动客单价");
        }
    });
    //活动下单老客点击事件
    public BindingCommand BGFiveOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bg_one.set(4);
            hd_name.set("活动下单老客");
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

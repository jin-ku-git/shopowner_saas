package com.youwu.shopowner_saas.ui.fragment;

import android.app.Application;
import androidx.annotation.NonNull;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.ui.finance.AccountActivity;
import com.youwu.shopowner_saas.ui.finance.OperatingDataActivity;
import com.youwu.shopowner_saas.ui.goods_operate.GoodsSetTowUpActivity;
import com.youwu.shopowner_saas.ui.ping_jia.PingJiaActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;


/**
 * 2023/03/06
 */

public class DianPuViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();



    public DianPuViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    //财务对账点击事件
    public BindingCommand AccountOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(AccountActivity.class);
        }
    });

    //商品管理
    public BindingCommand GoodsSetUpOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(GoodsSetTowUpActivity.class);
        }
    });

    //顾客评价点击事件
    public BindingCommand PingJiaOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(PingJiaActivity.class);
        }
    });

    //经营数据点击事件
    public BindingCommand OperatingDataOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(OperatingDataActivity.class);
        }
    });

}

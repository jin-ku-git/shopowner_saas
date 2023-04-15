package com.youwu.shopowner_saas.ui.order_goods;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.ui.main.sousuo.OrderSouSuoActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/02/27
 */

public class ApplyReturnGoodsViewModel extends BaseViewModel<DemoRepository> {

    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<String> total_money = new ObservableField<>();

    public ApplyReturnGoodsViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }
    //返回点击事件
    public BindingCommand ReturnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //搜索点击事件
    public BindingCommand submitOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });

}

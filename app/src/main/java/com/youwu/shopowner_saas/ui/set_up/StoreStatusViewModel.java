package com.youwu.shopowner_saas.ui.set_up;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/22
 */

public class StoreStatusViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public ObservableField<Boolean> TypeEvent = new ObservableField<>();


    public StoreStatusViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }

    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //营业开关的点击事件
    public BindingCommand SwitchOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
            TypeEvent.set(!TypeEvent.get());
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

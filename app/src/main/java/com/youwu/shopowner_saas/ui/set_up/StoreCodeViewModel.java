package com.youwu.shopowner_saas.ui.set_up;

import android.app.Application;

import androidx.annotation.NonNull;

import com.youwu.shopowner_saas.data.DemoRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/21
 */

public class StoreCodeViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();


    public StoreCodeViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }

    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });


    //下载的点击事件
    public BindingCommand DownloadOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

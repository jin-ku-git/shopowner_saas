package com.youwu.shopowner_saas.ui.set_up;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;

import java.util.Arrays;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.youwu.shopowner_saas.app.AppApplication.toPrettyFormat;

/**
 * 2022/08/12
 */

public class StoreSetUpViewModel extends BaseViewModel<DemoRepository> {

    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public StoreSetUpViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);


    }


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
        }
    });
    //门店状态的点击事件
    public BindingCommand StoreStatusOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           startActivity(StoreStatusActivity.class);
        }
    });



    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}


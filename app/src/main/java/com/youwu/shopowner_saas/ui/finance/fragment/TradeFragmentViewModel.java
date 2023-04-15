package com.youwu.shopowner_saas.ui.finance.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/17
 */

public class TradeFragmentViewModel extends BaseViewModel<DemoRepository> {


    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public TradeFragmentViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

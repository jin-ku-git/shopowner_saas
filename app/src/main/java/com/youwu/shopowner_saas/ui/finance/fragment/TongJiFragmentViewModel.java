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
 * 2023/03/07
 */

public class TongJiFragmentViewModel extends BaseViewModel<DemoRepository> {



    public TongJiFragmentViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

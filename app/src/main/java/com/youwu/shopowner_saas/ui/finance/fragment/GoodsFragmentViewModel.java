package com.youwu.shopowner_saas.ui.finance.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/21
 */

public class GoodsFragmentViewModel extends BaseViewModel<DemoRepository> {


    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<Integer> null_type=new ObservableField<>();// 0、显示空视图 1不显示

    public ObservableField<String> Time=new ObservableField<>();// 今日时间
    public GoodsFragmentViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

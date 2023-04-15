package com.youwu.shopowner_saas.ui.finance.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;

import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;

import com.youwu.shopowner_saas.databinding.FragmentTradeBinding;


import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 2023/03/17
 * 行业页面
 */

public class TradeFragment extends BaseFragment<FragmentTradeBinding, TradeFragmentViewModel> {



    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_trade;
    }
    @Override
    public TradeFragmentViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(TradeFragmentViewModel.class);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }



    @Override
    public void initViewObservable() {

    }
    @Override
    public void initData() {


        binding.ArcProgressBar.setProgress(33.71f);
        binding.ArcProgressBar.setSecondText("33.71%");


    }


}

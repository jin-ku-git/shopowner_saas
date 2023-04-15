package com.youwu.shopowner_saas.ui.finance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.utils_view.BigDecimalUtils;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/07
 */

public class BankCardViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<String>  Bank=new ObservableField<>();//开户行
    public ObservableField<String>  address=new ObservableField<>();//开户地址
    public ObservableField<String>  BankCardNumber=new ObservableField<>();//银行卡号
    public ObservableField<String>  phone=new ObservableField<>();//手机号
    public ObservableField<String>  sex=new ObservableField<>();//性别
    public ObservableField<String>  userName=new ObservableField<>();//姓名
    public ObservableField<String>  IDNumber=new ObservableField<>();//姓名

    public BankCardViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }
    //返回点击事件
    public BindingCommand ReturnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });


    //确定点击事件
    public BindingCommand DefineOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {


        }
    });
    //开户行点击事件
    public BindingCommand OpeningBankOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //性别点击事件
    public BindingCommand SexOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

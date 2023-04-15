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

public class ChongZhiViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public ObservableField<String>  cardNumber=new ObservableField<>();
    public ObservableField<String>  money=new ObservableField<>();

    public ChongZhiViewModel(@NonNull Application application, DemoRepository repository) {
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
            if(BigDecimalUtils.compare(money.get(),"100")>=0){
                RxToast.normal("充值金额："+money.get());
            }else {
                RxToast.normal("充值金额：不足");
            }
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

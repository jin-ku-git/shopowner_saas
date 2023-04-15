package com.youwu.shopowner_saas.ui.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.ui.goods_operate.GoodsSetUpActivity;

import com.youwu.shopowner_saas.ui.group.GroupSortActivity;
import com.youwu.shopowner_saas.ui.set_up.OrderSetUpActivity;
import com.youwu.shopowner_saas.ui.set_up.StoreCodeActivity;
import com.youwu.shopowner_saas.ui.set_up.StoreSetUpActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;


/**
 * 2023/03/06
 */

public class MyViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<String> nameEvent = new ObservableField<>();//名称
    public ObservableField<String> telEvent = new ObservableField<>();//账号

    public ObservableField<String> IPNameEvent = new ObservableField<>();//ipName
    public ObservableField<String> IPEvent = new ObservableField<>();//ip

    public MyViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }


    //退出登录
    public BindingCommand SignOutOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(88);
        }
    });


    //门店设置
    public BindingCommand StoreSetonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(StoreSetUpActivity.class);
        }
    });
    //订单设置
    public BindingCommand OrderSetonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(OrderSetUpActivity.class);
        }
    });

    //收款码
    public BindingCommand StoreCodeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(StoreCodeActivity.class);
        }
    });
    //测试
    public BindingCommand TestOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(GroupSortActivity.class);
        }
    });

    //测试语音
    public BindingCommand TestYuYinOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });

    //商品管理
    public BindingCommand GoodsSetUpOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(GoodsSetUpActivity.class);
        }
    });


}

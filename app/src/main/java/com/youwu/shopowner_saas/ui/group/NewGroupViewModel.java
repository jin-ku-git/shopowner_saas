package com.youwu.shopowner_saas.ui.group;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/09
 */

public class NewGroupViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public ObservableField<String>  TopName=new ObservableField<>();


    //群组名称的绑定
    public ObservableField<String> GroupName = new ObservableField<>("");
    //群组描述的绑定
    public ObservableField<String> GroupMS = new ObservableField<>("");

    //完成创建/删除的绑定
    public ObservableField<String> FoundAndDelete = new ObservableField<>("");
    //保存/完成的绑定
    public ObservableField<String> PreserveAndAccomplish = new ObservableField<>("");
    public NewGroupViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    //完成的点击事件
    public BindingCommand AccomplishOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //删除或完成创建的点击事件
    public BindingCommand DeleteOnClick = new BindingCommand(new BindingAction() {
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

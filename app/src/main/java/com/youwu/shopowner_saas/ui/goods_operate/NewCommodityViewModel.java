package com.youwu.shopowner_saas.ui.goods_operate;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.group.GroupSortActivity;
import com.youwu.shopowner_saas.ui.group.NewGroupActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2023/03/11
 */

public class NewCommodityViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public ObservableField<String>  typeName=new ObservableField<>();

    public NewCommodityViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }

    //商品名称的绑定
    public ObservableField<String> GoodsName = new ObservableField<>("");
    //群组名称的绑定
    public ObservableField<String> GroupName = new ObservableField<>("");
    //商品分类的绑定
    public ObservableField<String> GroupClassify = new ObservableField<>("");
    //商品份量的绑定
    public ObservableField<String> GoodsWeight = new ObservableField<>("");
    //商品售价的绑定
    public ObservableField<String> GoodsSells = new ObservableField<>("");
    //市场价的绑定
    public ObservableField<String> MarketPrices = new ObservableField<>("");
    //商品库存的绑定
    public ObservableField<String> GoodsStock = new ObservableField<>("");
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


    //删除的点击事件
    public BindingCommand DeleteOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            RxToast.normal("删除");
        }
    });



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

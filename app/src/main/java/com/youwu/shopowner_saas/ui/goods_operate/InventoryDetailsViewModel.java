package com.youwu.shopowner_saas.ui.goods_operate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.MarkBean;

import java.util.ArrayList;
import java.util.List;

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

/**
 * 2021/9/16
 */

public class InventoryDetailsViewModel extends BaseViewModel<DemoRepository> {

    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public InventoryDetailsViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    //总价
    public ObservableField<String> TotalPrice =new ObservableField<>();
    //种类
    public ObservableField<String> TotalType =new ObservableField<>();
    //数量
    public ObservableField<String> TotalQuantity =new ObservableField<>();
    //预计到货时间的绑定
    public ObservableField<String> estimate_time = new ObservableField<>("");
    //备注
    public ObservableField<String> remarks =new ObservableField<>("");


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    //确认订货的点击事件
    public BindingCommand ConfirmOrder = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //选择时间的点击事件
    public BindingCommand choiceTimeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);
        }
    });
    //选备注的点击事件
    public BindingCommand remarksOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(4);
        }
    });


    /**
     * 盘点
     * @param goods_list
     */
    public void sorting_inventory(String goods_list,String type) {
        String submitJson="";
        if ("".equals(remarks.get())||remarks.get()==null){
            KLog.i("remarks为空");
        }else {
            List<MarkBean> list=new ArrayList<>();
            MarkBean markBean=new MarkBean();
            markBean.setName(remarks.get());
            list.add(markBean);
            submitJson = new Gson().toJson(list);
        }

        KLog.d("goods_list:"+goods_list+"\nmark:"+submitJson);
        model.SORTING_INVENTORY(goods_list,type,submitJson)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new DisposableObserver<BaseBean<Object>>() {
                    @Override
                    public void onNext(BaseBean<Object> response) {
                        if (response.isOk()){
                            RxToast.normal("盘点成功！");
                            IntegerEvent.setValue(5);
                        }else {
                            RxToast.normal(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                    @Override
                    public void onComplete() {
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }
}


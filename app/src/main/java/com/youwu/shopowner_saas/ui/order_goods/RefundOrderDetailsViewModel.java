package com.youwu.shopowner_saas.ui.order_goods;

import android.app.Application;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.RefundDetailsBean;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.youwu.shopowner_saas.app.AppApplication.toPrettyFormat;

/**
 * 2022/09/17
 */

public class RefundOrderDetailsViewModel extends BaseViewModel<DemoRepository> {


    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<String> PhoneEvent = new SingleLiveEvent<>();
    //总价
    public ObservableField<String> TotalPrice =new ObservableField<>();

    public RefundOrderDetailsViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }

    public ObservableField<RefundDetailsBean> OrderDetails =new ObservableField<>();


    //订单详情
    public SingleLiveEvent<RefundDetailsBean> OrderDetailsLiveEvent = new SingleLiveEvent<>();


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
        }
    });
    //打印小票的点击事件
    public BindingCommand ConfirmOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);

        }
    });

    //拒绝的点击事件
    public BindingCommand RefuseOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);

        }
    });
    //同意的点击事件
    public BindingCommand AgreeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(3);

        }
    });

    //拨打电话的点击事件
    public BindingCommand PhoneOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (OrderDetails.get().getMember_info().getUser_name()!=null){
                PhoneEvent.setValue(OrderDetails.get().getMember_info().getUser_name());
            }


        }
    });

    /**
     * 订单详情
     * @param order_sn
     */
    public void refund_details(String order_sn) {
        model.REFUND_DETAILS(order_sn)
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
                            String JsonData = new Gson().toJson(response.data);

                            RefundDetailsBean saleBillBean = JSON.parseObject(toPrettyFormat(JsonData), RefundDetailsBean.class);
                            OrderDetailsLiveEvent.setValue(saleBillBean);
                            OrderDetails.set(saleBillBean);

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
    /**
     * 打印
     * @param order_sn
     */
    public void Print(String order_sn, String store_id) {
        model.PRINT(order_sn,store_id)
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
                            RxToast.normal("打印成功");

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
    /**
     * 小程序订单审核
     * @param status
     * @param order_sn
     * @param refund_reason
     * @param modify_stock
     * @param dialog
     */
    public void audit_order_refund(final int status, String order_sn, String refund_reason, String modify_stock, final Dialog dialog) {
        model.AUDIT_ORDER_REFUND(status,order_sn,refund_reason,modify_stock)
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
                            dialog.dismiss();
                            if (status==1){
                                IntegerEvent.setValue(4);
                            }else {
                                IntegerEvent.setValue(4);
                            }


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


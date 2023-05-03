package com.youwu.shopowner_saas.ui.main.sousuo;

import static com.youwu.shopowner_saas.app.AppApplication.toPrettyFormat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.OneRowsOrdersBean;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;

import java.util.ArrayList;

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
 * 2023/02/23
 */

public class SubscribeOrderViewModel extends BaseViewModel<DemoRepository> {

    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<Integer> null_type=new ObservableField<>();
    public ObservableField<Integer> DidData=new ObservableField<>();//状态 下拉刷新有没有数据
    public SubscribeOrderViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }



    public ObservableField<String> open_close = new ObservableField<>();

    public ObservableField<String> order_num=new ObservableField<>();//共多少单
    public SingleLiveEvent<ArrayList<OrderBean>> getOrder_list=new SingleLiveEvent<>();

    //返回点击事件
    public BindingCommand ReturnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //排序点击事件
    public BindingCommand bthSortOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            IntegerEvent.setValue(1);
        }
    });

    //展开点击事件
    public BindingCommand btOpenOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if ("展开".equals(open_close.get())){
                open_close.set("收起");
                IntegerEvent.setValue(2);
            }else {
                open_close.set("展开");
                IntegerEvent.setValue(3);
            }

        }
    });



    /**
     * 订单列表
     * @param appointment_time       预约配送日期
     *
     */
    public void new_order_list(String appointment_time,int page,int limit) {

        model.NEW_ORDER_LIST(appointment_time,"0","0","","","","",page+"",limit+"")
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



                            OneRowsOrdersBean OneRowsOrdersBean= JSON.parseObject(toPrettyFormat(JsonData), OneRowsOrdersBean.class);
                            ArrayList<OrderBean> orderBeans=new ArrayList<>();
                            for (int i=0;i<OneRowsOrdersBean.getRows().size();i++){
                                orderBeans.add(OneRowsOrdersBean.getRows().get(i));
                            }
                            KLog.i("订单列表："+orderBeans.size());

                            getOrder_list.setValue(orderBeans);

                            order_num.set(OneRowsOrdersBean.getTotal()+"");


                        }else {
                            RxToast.normal(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            RxToast.normal(((ResponseThrowable) throwable).message);
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
     * 修改订单状态
     * @param type
     * @param order_sn
     */
    public void new_update_order(int type, String order_sn,String remark) {
        model.NEW_UPDATE_ORDER(type+"",order_sn)
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

                            if (type==2){
                                RxToast.success("已接单!");
                                IntegerEvent.setValue(5);
                            }else if (type==3){
                                RxToast.normal("已拒单!");
                                IntegerEvent.setValue(6);
                            }else if (type==4){
                                RxToast.success("已出餐!");
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

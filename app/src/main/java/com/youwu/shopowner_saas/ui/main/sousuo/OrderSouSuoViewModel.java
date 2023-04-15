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
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;
import com.youwu.shopowner_saas.ui.fragment.bean.RowsMainOrdersBean;

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

public class OrderSouSuoViewModel extends BaseViewModel<DemoRepository> {

    //搜索内容的绑定
    public ObservableField<String> SearchContent = new ObservableField<>("");

    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();


    public ObservableField<Integer> null_type=new ObservableField<>();

    public OrderSouSuoViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    public ObservableField<String> order_num=new ObservableField<>();//共多少单
    public SingleLiveEvent<ArrayList<OrderBean>> getOrder_list=new SingleLiveEvent<>();

    public SingleLiveEvent<ArrayList<OrderBean>> RefundOrderBeans_list=new SingleLiveEvent<>();

    //返回点击事件
    public BindingCommand ReturnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //搜索点击事件
    public BindingCommand SouSuoClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            IntegerEvent.setValue(1);
        }
    });
    //排序点击事件
    public BindingCommand bthSortOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            IntegerEvent.setValue(2);
        }
    });



    /**
     * 订单列表
     * @param appointment_time       预约配送日期
     * @param type                   订单类型 1全部 2已取消
     * @param order_taking_status    门店接单状态 0全部 1待接单 2待出餐
     *
     */
    public void new_order_list(String appointment_time,int type,int order_taking_status,int select_type) {
        String order_sn="",goods_name="",member_phone="",member_address="";

        if (select_type==1){
            order_sn=SearchContent.get();
        }else if (select_type==2){
            goods_name=SearchContent.get();
        }else if (select_type==3){
            member_phone=SearchContent.get();
        }else if (select_type==4){
            member_address=SearchContent.get();
        }
        if (type==2){//当type==2时，门店接单状态为0
            order_taking_status=0;
        }
        model.NEW_ORDER_LIST(appointment_time,type+"",order_taking_status+"",order_sn,goods_name,member_phone,member_address)
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
                            ArrayList<OrderBean> orderBeans = AppApplication.getObjectList(JsonData, OrderBean.class);
                            KLog.i("订单列表："+orderBeans.size());


                            getOrder_list.setValue(orderBeans);

                            order_num.set(getOrder_list.getValue().size()+"");
                            KLog.i("order_list："+getOrder_list.getValue().size());
                            if (getOrder_list.getValue().size()==0){
                                null_type.set(0);
                            }else {
                                null_type.set(1);
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
     * 退款订单列表
     */
    public void new_refund_order_list(int select_type,int page,int limit) {
        String order_sn="",goods_name="",member_phone="",member_address="";

        if (select_type==1){
            order_sn=SearchContent.get();
        }else if (select_type==2){
            goods_name=SearchContent.get();
        }else if (select_type==3){
            member_phone=SearchContent.get();
        }else if (select_type==4){
            member_address=SearchContent.get();
        }
        model.NEW_REFUND_ORDER_LIST("0",order_sn,goods_name,member_phone,member_address,page+"",limit+"")
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
                            RowsMainOrdersBean rowsMainOrdersBean= JSON.parseObject(toPrettyFormat(JsonData), RowsMainOrdersBean.class);
                            ArrayList<OrderBean> orderBeans=new ArrayList<>();
                            for (int i=0;i<rowsMainOrdersBean.getRows().size();i++){
                                orderBeans.add(rowsMainOrdersBean.getRows().get(i));
                            }

//                            ArrayList<OrderBean> orderBeans = AppApplication.getObjectList(JsonData, OrderBean.class);
                            KLog.i("退款订单列表："+orderBeans.size());


                            RefundOrderBeans_list.setValue(orderBeans);

                            KLog.i("RefundOrderBeans_list："+RefundOrderBeans_list.getValue().size());





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

    /**
     *  审核退款订单
     * @param audit_status      // 审核退款 1同意 2拒绝
     * @param refund_sn            退款单号
     * @param remark                审核备注
     */
    public void new_audit_refund(int audit_status, String refund_sn,String remark) {
        model.NEW_AUDIT_REFUND(audit_status+"",refund_sn,remark)
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

                            if (audit_status==1){
                                RxToast.success("已同意!");
                                IntegerEvent.setValue(7);

                            }else if (audit_status==2){
                                RxToast.normal("已拒绝!");
                                IntegerEvent.setValue(8);

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

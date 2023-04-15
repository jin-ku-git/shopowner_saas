package com.youwu.shopowner_saas.ui.fragment;

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
import com.youwu.shopowner_saas.ui.fragment.bean.UserBean;
import com.youwu.shopowner_saas.ui.main.sousuo.SubscribeOrderActivity;
import com.youwu.shopowner_saas.ui.set_up.OrderSetUpActivity;
import com.youwu.shopowner_saas.ui.set_up.StoreBean;
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

import static com.youwu.shopowner_saas.app.AppApplication.toPrettyFormat;

import java.util.ArrayList;


/**
 * 2022/09/12
 */

public class OneViewModel extends BaseViewModel<DemoRepository> {


    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<String> ordersAccepting=new ObservableField<>();//手动接单/自动接单

    public ObservableField<Integer> bth_one=new ObservableField<>();//状态 1 全部 2、已取消
    public SingleLiveEvent<Integer> status_order_one=new SingleLiveEvent<>();//状态 1 全部 2、已取消
    public ObservableField<Integer> bth_two=new ObservableField<>();//状态 0 全部 1、待接单 2、待出餐
    public SingleLiveEvent<Integer> status_order=new SingleLiveEvent<>();//状态 0 全部 1、待接单 2、待出餐



    public ObservableField<String> order_num=new ObservableField<>();//共多少单
    public ObservableField<String> order_in_progress=new ObservableField<>();//进行中多少单
    public ObservableField<String> order_Canceled=new ObservableField<>();//已取消多少单

    public ObservableField<Integer> DJD_num=new ObservableField<>();//待接单数量
    public ObservableField<Integer> DCC_num=new ObservableField<>();//待出餐数量


    public ObservableField<Integer> bth_AfterSales=new ObservableField<>();//状态 1 待退款 2、已处理退款



    public ObservableField<String> open_close = new ObservableField<>();

    public OneViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    public ObservableField<String> store_name = new ObservableField<>("");

    public ObservableField<Integer> order_wait_count = new ObservableField<>();
    public ObservableField<Integer> order_mak_count = new ObservableField<>();
    public ObservableField<Integer> order_refund_count = new ObservableField<>();

    public ObservableField<Integer> null_type=new ObservableField<>();

    //订单列表
    public SingleLiveEvent<ArrayList<OrderBean>> order_list = new SingleLiveEvent<>();

    public SingleLiveEvent<ArrayList<OrderBean>> getOrder_list=new SingleLiveEvent<>();
    public SingleLiveEvent<ArrayList<OrderBean>> RefundOrderBeans_list=new SingleLiveEvent<>();


    //全部点击事件
    public BindingCommand bthOneOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(1);
            status_order_one.setValue(1);
        }
    });
    //已取消点击事件
    public BindingCommand bthCanceledOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_one.set(2);
            status_order_one.setValue(2);
        }
    });

    //全部点击事件
    public BindingCommand bthTwoOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_two.set(0);
            status_order.setValue(0);
        }
    });
    //待接单点击事件
    public BindingCommand bthPendingOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_two.set(1);
            status_order.setValue(1);
        }
    });
    //待出餐点击事件
    public BindingCommand bthToServedOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_two.set(2);
            status_order.setValue(2);
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
    //待退款点击事件
    public BindingCommand bthAfterSalesOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_AfterSales.set(1);
            IntegerEvent.setValue(9);
            new_refund_order_list(1,15);
        }
    });
    //已处理退款点击事件
    public BindingCommand bthProcessedOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            bth_AfterSales.set(2);
            IntegerEvent.setValue(10);
            new_refund_order_list(1,15);

        }
    });

    //搜索点击事件
    public BindingCommand ivSouSuoClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(0);

        }
    });
    //设置点击事件
    public BindingCommand SetUpOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(OrderSetUpActivity.class);
        }
    });
    //预订单点击事件
    public BindingCommand subscribeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(SubscribeOrderActivity.class);
        }
    });


    /**
     * 获取个人信息
     */
    public void getMe() {
        model.GET_ME()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        showDialog();
                    }
                })
                .subscribe(new DisposableObserver<BaseBean<Object>>() {
                    @Override
                    public void onNext(BaseBean<Object> response) {
                        if (response.isOk()){
                            String submitJsonData = new Gson().toJson(response.data);

                            UserBean userBean= JSON.parseObject(toPrettyFormat(submitJsonData), UserBean.class);

                            AppApplication.spUtils.put("StoreId", userBean.getStore_id()+"");
                            AppApplication.spUtils.put("StoreName", userBean.getStore_name());
                            AppApplication.spUtils.put("Id", userBean.getId()+"");
                            AppApplication.spUtils.put("Name", userBean.getName());
                            AppApplication.spUtils.put("topic", userBean.getTopic());
                            AppApplication.spUtils.put("tel", userBean.getTel());

                            AppApplication.spUtils.put("is_order", userBean.getIs_order());
                            AppApplication.spUtils.put("start_time", userBean.getStart());
                            AppApplication.spUtils.put("end_time", userBean.getEnd());

                            store_name.set(userBean.getStore_name());


                        }else {
                            RxToast.normal(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
//                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                    @Override
                    public void onComplete() {
                        //关闭对话框
//                        dismissDialog();
                    }
                });
    }


    /**
     * 订单列表
     * @param appointment_time  预约配送日期
     * @param type  预约配送日期  订单类型 1全部 2已取消
     * @param order_taking_status  预约配送日期  	门店接单状态 0全部 1待接单 2待出餐
     *
     */
    public void new_order_list(String appointment_time,int type,int order_taking_status) {
        int finalOrder_taking_status = order_taking_status;
        if (type==2){
            order_taking_status=0;
        }

        model.NEW_ORDER_LIST(appointment_time,type+"",order_taking_status+"","","","","")
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
                            int Canceled_num=0;//已取消
                            int in_progress=0;//进行中

                            for (int i=0;i<orderBeans.size();i++){
                                //必须在待接单或待出餐状态同时支付状态是已支付状态
                                if ((orderBeans.get(i).getOrder_taking_status()==1||orderBeans.get(i).getOrder_taking_status()==2)&&orderBeans.get(i).getOrder_status()==1){
                                    in_progress++;
                                }
                                if (orderBeans.get(i).getOrder_status()==2){
                                    Canceled_num++;
                                }


                            }
                            order_in_progress.set(in_progress+"");
                            order_Canceled.set(Canceled_num+"");

                            if (finalOrder_taking_status ==0){
                                int order_wait_count=0;//待接单
                                int order_mak_count=0;//待接单
                                for (int i=0;i<orderBeans.size();i++){

                                        if (orderBeans.get(i).getOrder_taking_status()==1&&orderBeans.get(i).getOrder_status()==1){
                                            order_wait_count++;
                                        }
                                        if (orderBeans.get(i).getOrder_taking_status()==2){
                                            order_mak_count++;
                                        }

                                        DJD_num.set(order_wait_count);
                                        DCC_num.set(order_mak_count);

                                }
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
                                DJD_num.set(DJD_num.get()-1);
                                DCC_num.set(DCC_num.get()+1);
                            }else if (type==3){
                                RxToast.normal("已拒单!");
                                IntegerEvent.setValue(6);
                                DJD_num.set(DJD_num.get()-1);
                            }else if (type==4){

                                RxToast.success("已出餐!");
                                IntegerEvent.setValue(4);
                                DCC_num.set(DCC_num.get()-1);
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

    /**
     * 退款订单列表
     */
    public void new_refund_order_list(int page,int limit) {
        model.NEW_REFUND_ORDER_LIST(bth_AfterSales.get()+"","","","","",page+"",limit+"")
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
     * 获取门店信息
     */
    public void new_store_info() {
        model.NEW_STORE_INFO()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new DisposableObserver<BaseBean<Object>>() {
                    @Override
                    public void onNext(BaseBean<Object> response) {
                        if (response.isOk()){
                            String JsonData = new Gson().toJson(response.data);
                            KLog.d("获取门店信息："+JsonData);

                            StoreBean storeBean= JSON.parseObject(toPrettyFormat(JsonData), StoreBean.class);

                            if (storeBean.getIs_order()==1){
                                ordersAccepting.set("自动接单中");
                            }else {
                                ordersAccepting.set("手动接单中");
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

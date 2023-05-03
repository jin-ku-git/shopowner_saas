package com.youwu.shopowner_saas.ui.zaocan;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.zaocan.bean.MergeInfo;
import com.youwu.shopowner_saas.ui.zaocan.bean.PeriodInfo;
import com.youwu.shopowner_saas.ui.zaocan.bean.PreparationInfo;

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
 * 2023/04/25
 */

public class PeriodTimeViewModel extends BaseViewModel<DemoRepository> {



    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public ObservableField<String> TopNameField = new ObservableField<>();

    public ObservableField<String> TimeField = new ObservableField<>();

    public ObservableField<Integer> null_type=new ObservableField<>();
    public SingleLiveEvent<ArrayList<PeriodInfo>> PeriodInfoEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<ArrayList<PreparationInfo>> PreparationInfoEvent = new SingleLiveEvent<>();

    public PeriodTimeViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);


    }


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
        }
    });



    //选择日期的点击事件
    public BindingCommand TimeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //确认的点击事件
    public BindingCommand ConfirmOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
        IntegerEvent.setValue(2);

        }
    });



    //重置的点击事件
    public BindingCommand ResetOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);
        }
    });


    /**
     * 时段制作明细
     **/
    public void getTimeGoodsInfo(String channel_id,String store_id,String appointment_time) {

        model.GET_TIME_GOODS_INFO(channel_id,store_id,appointment_time)
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
                    public void onNext(BaseBean response) {
                        String submitJson = new Gson().toJson(response.data);
                        KLog.a("时段制作明细返回数据："+submitJson);
                        if (response.isOk()){
                            ArrayList<PeriodInfo> mergeInfos = AppApplication.getObjectList(submitJson, PeriodInfo.class);

                            null_type.set(mergeInfos.size());
                            PeriodInfoEvent.setValue(mergeInfos);

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
     * 时段备料明细
     **/
    public void getTimeGoodsNum(String channel_id,String store_id,String appointment_time) {

        model.GET_TIME_GOODS_NUM(channel_id,store_id,appointment_time)
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
                    public void onNext(BaseBean response) {
                        String submitJson = new Gson().toJson(response.data);
                        KLog.a("时段备料明细返回数据："+submitJson);
                        if (response.isOk()){
                            ArrayList<PreparationInfo> mergeInfos = AppApplication.getObjectList(submitJson, PreparationInfo.class);

                            null_type.set(mergeInfos.size());
                            PreparationInfoEvent.setValue(mergeInfos);

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

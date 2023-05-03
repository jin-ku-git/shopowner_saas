package com.youwu.shopowner_saas.ui.zaocan;

import static com.youwu.shopowner_saas.app.AppApplication.toPrettyFormat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.bean.UpDateBean;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.CommunityBean;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;
import com.youwu.shopowner_saas.ui.zaocan.bean.CommunityListBean;
import com.youwu.shopowner_saas.ui.zaocan.bean.MergeInfo;

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
 * 2023/04/24
 */

public class DeliveryDetailsViewModel extends BaseViewModel<DemoRepository> {



    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<ArrayList<CommunityListBean>> CommunityEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<ArrayList<MergeInfo>> MergeInfoEvent = new SingleLiveEvent<>();

    public ObservableField<String> TopNameField = new ObservableField<>();

    public ObservableField<String> TimeField = new ObservableField<>();
    public ObservableField<String> CommunityNameField = new ObservableField<>();

    public ObservableField<Integer> null_type=new ObservableField<>();
    public DeliveryDetailsViewModel(@NonNull Application application, DemoRepository repository) {
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
            IntegerEvent.setValue(2);
        }
    });
    //选择社区
    public BindingCommand CommunityOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //确认的点击事件
    public BindingCommand ConfirmOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(3);

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
     * 获取社区
     **/
    public void getCommunity(String store_id,String name) {

        model.GET_COMMUNITY(store_id,name)
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
                        if (response.isOk()){
                            ArrayList<CommunityListBean> communityBeans = AppApplication.getObjectList(submitJson, CommunityListBean.class);
                            CommunityEvent.setValue(communityBeans);
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
     * 时段配送明细
     **/
    public void getTimeOrderInfo(String channel_id,String store_id,String appointment_time,String community_id) {

        model.GET_TIME_ORDER_INFO(channel_id,store_id,appointment_time,community_id)
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
                        KLog.a("时间配送明细返回数据："+submitJson);
                        if (response.isOk()){
                            ArrayList<MergeInfo> mergeInfos = AppApplication.getObjectList(submitJson, MergeInfo.class);

                            null_type.set(mergeInfos.size());
                            MergeInfoEvent.setValue(mergeInfos);

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

package com.youwu.shopowner_saas.ui.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.GroupBean;
import com.youwu.shopowner_saas.ui.fragment.bean.ScrollBean;

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
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;


/**
 * 2022/09/12
 */

public class TwoViewModel extends BaseViewModel<DemoRepository> {


    //    public ObservableField<UserBean.RoleBean> entity = new ObservableField<>();
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    //商品分类列表
    public SingleLiveEvent<ArrayList<GroupBean>> groupList = new SingleLiveEvent<>();
    //订货商品列表
    public SingleLiveEvent<ArrayList<ScrollBean>> OrderListBean = new SingleLiveEvent<>();
    //总价
    public ObservableField<String> TotalPrice =new ObservableField<>();
    //种类
    public ObservableField<String> TotalType =new ObservableField<>();
    //数量
    public ObservableField<String> TotalQuantity =new ObservableField<>();

    //购物车是否显示
    public ObservableField<Integer> shopping_visibility =new ObservableField<>();




    public TwoViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }


    //打开购物车弹窗的点击事件
    public BindingCommand OpenPopUpOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //提交的点击事件
    public BindingCommand OconfirmOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);
        }
    });

    /**
     * 获取盘点分类
     */
    public void getGoodsCategory() {

        model.GOODS_CATEGORY()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new DisposableObserver<BaseBean<ArrayList<GroupBean>>>() {
                    @Override
                    public void onNext(BaseBean<ArrayList<GroupBean>> response) {
                        if (response.isOk()){
                            String submitJsonData = new Gson().toJson(response.data);
                            ArrayList<GroupBean> list=  AppApplication.getObjectList(submitJsonData,GroupBean.class);

                            groupList.setValue(list);

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
     * 获取订货商品列表
     * @param storeId
     * @param name
     * @param id
     */
    public void initOrder_info(String storeId, String name, String id) {
        model.GOODS_LIST(storeId,id+"")
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
                            ArrayList<ScrollBean.SAASOrderBean> saasOrderBean = AppApplication.getObjectList(JsonData, ScrollBean.SAASOrderBean.class);

                            ArrayList<ScrollBean> list=new ArrayList<>();

                            for (int i=0;i<saasOrderBean.size();i++){
                                ScrollBean.SAASOrderBean dataBean=new ScrollBean.SAASOrderBean();
                                dataBean=saasOrderBean.get(i);
                                dataBean.setStore_name(name);
                                if (i==0){
                                    list.add(new ScrollBean(true, name,storeId));
                                }
                                list.add(new ScrollBean(dataBean));
                            }
                            OrderListBean.setValue(list);
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

package com.youwu.shopowner_saas.ui.fragment;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;


import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.goods_operate.GoodsSetUpActivity;

import com.youwu.shopowner_saas.ui.order_record.RecordActivity;
import com.youwu.shopowner_saas.ui.report_form.SalesOverviewActivity;
import com.youwu.shopowner_saas.ui.set_up.ModifyPasswordActivity;
import com.youwu.shopowner_saas.ui.set_up.OrderSetUpActivity;
import com.youwu.shopowner_saas.ui.set_up.SettingsActivity;
import com.youwu.shopowner_saas.ui.zaocan.DeliveryDetailsActivity;
import com.youwu.shopowner_saas.ui.zaocan.GoodsTotalActivity;
import com.youwu.shopowner_saas.ui.zaocan.PeriodTimeActivity;
import com.youwu.shopowner_saas.ui.zaocan.PreparationTimeActivity;


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

public class FourViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<String> nameEvent = new ObservableField<>();//名称
    public ObservableField<String> telEvent = new ObservableField<>();//账号


    public FourViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    //设置
    public BindingCommand SetonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(SettingsActivity.class);
        }
    });

    //门店全部
    public BindingCommand StoreAllonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(0);
        }
    });
    //待接单
    public BindingCommand OneOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //待接单
    public BindingCommand TwoOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);
        }
    });
    //待接单
    public BindingCommand ThreeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(3);
        }
    });
    //待接单
    public BindingCommand FourOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(4);
        }
    });
    //核销设置
    public BindingCommand WriteOffOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(5);
        }
    });
//    //订货
//    public BindingCommand bookGoodsOnClick = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            IntegerEvent.setValue(6);
//        }
//    });
//    //退货
//    public BindingCommand retreatGoodsOnClick = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            IntegerEvent.setValue(7);
//        }
//    });
    //报损
    public BindingCommand damageGoodsOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(8);
        }
    });
    //盘点
    public BindingCommand InventoryGoodsOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(9);
        }
    });
    //沽清
    public BindingCommand SellOffGoodsOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(10);
        }
    });
    //退出登录
    public BindingCommand SignOutOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(88);
        }
    });
//    //订货记录
//    public BindingCommand OrderOnClick = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            Bundle bundle=new Bundle();
//            bundle.putInt("type",1);
//            startActivity(OrderGoodsListActivity.class,bundle);
//        }
//    });
//    //退货记录
//    public BindingCommand returnGoodsOnClick = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            Bundle bundle=new Bundle();
//            bundle.putInt("type",2);
//            startActivity(OrderGoodsListActivity.class,bundle);
//        }
//    });
    //报损记录
    public BindingCommand LossOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putInt("type",1);
            startActivity(RecordActivity.class,bundle);
        }
    });
    //盘点记录
    public BindingCommand InventoryOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putInt("type",2);
            startActivity(RecordActivity.class,bundle);
        }
    });
    //沽清记录
    public BindingCommand SellOffOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putInt("type",3);
            startActivity(RecordActivity.class,bundle);
        }
    });

    //门店设置
    public BindingCommand StoreSetonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(OrderSetUpActivity.class);
        }
    });
    //报表
    public BindingCommand reportFormOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(SalesOverviewActivity.class);
        }
    });
    //修改密码设置
    public BindingCommand ModifyPasswordOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ModifyPasswordActivity.class);
        }
    });


    //配送明细
    public BindingCommand ZCDeliveryDetailsOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString("TopName","早餐时段配送明细");
            bundle.putString("channel_id","1");
            startActivity(DeliveryDetailsActivity.class,bundle);
        }
    });
    public BindingCommand ZCPeriodTimeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString("TopName","早餐时段制作明细");
            bundle.putString("channel_id","1");
            startActivity(PeriodTimeActivity.class,bundle);
        }
    });
    public BindingCommand ZCPreparationTimeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString("TopName","早餐时段备料明细");
            bundle.putString("channel_id","1");
            startActivity(PreparationTimeActivity.class,bundle);
        }
    });
    public BindingCommand ZCGoodsTotalOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString("TopName","早餐商品总单");
            bundle.putString("channel_id","1");
            startActivity(GoodsTotalActivity.class,bundle);
        }
    });

    //配送明细
    public BindingCommand SCDeliveryDetailsOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString("TopName","食材时段配送明细");
            bundle.putString("channel_id","2");
            startActivity(DeliveryDetailsActivity.class,bundle);
        }
    });
    public BindingCommand SCPreparationTimeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString("TopName","食材时段备料明细");
            bundle.putString("channel_id","2");
            startActivity(PreparationTimeActivity.class,bundle);
        }
    });
    public BindingCommand SCPeriodTimeOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString("TopName","食材时段制作明细");
            bundle.putString("channel_id","2");
            startActivity(PeriodTimeActivity.class,bundle);
        }
    });
    public BindingCommand SCGoodsTotalOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString("TopName","食材商品总单");
            bundle.putString("channel_id","2");
            startActivity(GoodsTotalActivity.class,bundle);
        }
    });

    //商品管理
    public BindingCommand GoodsSetUpOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(GoodsSetUpActivity.class);
        }
    });


    /**
     * 核销
     * @param order_sn
     */
    public void close_order(String order_sn) {

        model.CLOSE_ORDER(order_sn)
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
                            IntegerEvent.setValue(66);

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

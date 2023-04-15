package com.youwu.shopowner_saas.ui.group;

import static com.youwu.shopowner_saas.app.AppApplication.toPrettyFormat;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner_saas.app.UserUtils;
import com.youwu.shopowner_saas.bean.UpDateBean;
import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.login.LogBean;
import com.youwu.shopowner_saas.ui.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 2023/03/06
 */

public class GroupManageViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public ObservableField<String>  typeName=new ObservableField<>();

    public GroupManageViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }

    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    //排序&批量操作的点击事件
    public BindingCommand SortOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(GroupSortActivity.class);
        }
    });
    //新建群组的点击事件
    public BindingCommand NewGroupOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putInt("type",1);
            startActivity(NewGroupActivity.class,bundle);
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

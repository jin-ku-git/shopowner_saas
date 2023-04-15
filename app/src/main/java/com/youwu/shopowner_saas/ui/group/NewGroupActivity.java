package com.youwu.shopowner_saas.ui.group;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.gson.Gson;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityNewGroupBinding;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 新建/编辑群组
 * 2023/03/09
 */
public class NewGroupActivity extends BaseActivity<ActivityNewGroupBinding, NewGroupViewModel> {

    private int type;//1、新建 2、编辑
    private String name;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_new_group;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {


        super.initParam();

        type=getIntent().getIntExtra("type",0);
        name=getIntent().getStringExtra("name");
    }

    @Override
    public NewGroupViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(NewGroupViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://完成


                        break;
                }
            }
        });
    }

    @Override


    public void initData() {
        super.initData();

        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色

        if (type==1){
            viewModel.FoundAndDelete.set("完成创建");
            viewModel.PreserveAndAccomplish.set("保存，继续创建群组");
            viewModel.TopName.set("新建群组");
        }else {
            viewModel.FoundAndDelete.set("删除");
            viewModel.PreserveAndAccomplish.set("完成");
            viewModel.TopName.set("编辑群组");
            viewModel.GroupName.set(name);
        }

    }



}


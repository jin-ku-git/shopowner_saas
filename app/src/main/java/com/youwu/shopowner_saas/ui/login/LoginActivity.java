package com.youwu.shopowner_saas.ui.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.UpData;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.bean.UpDateBean;
import com.youwu.shopowner_saas.databinding.ActivityLoginBinding;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

import static com.youwu.shopowner_saas.utils_view.NotificationsUtils.isNotificationEnabled;
import static com.youwu.shopowner_saas.utils_view.NotificationsUtils.openNotification;

/**
 * 一个MVVM模式的登陆界面
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {
    //ActivityLoginBinding类是databinding框架自定生成的,对应activity_login.xml
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    public void initViewObservable() {
        //监听ViewModel中pSwitchObservable的变化, 当ViewModel中执行【uc.pSwitchObservable.set(!uc.pSwitchObservable.get());】时会回调该方法
        viewModel.uc.pSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                //pSwitchObservable是boolean类型的观察者,所以可以直接使用它的值改变密码开关的图标
                if (viewModel.uc.pSwitchEvent.getValue()) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw);
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw_press);
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        viewModel.upDateEvent.observe(this, new Observer<UpDateBean>() {
            @Override
            public void onChanged(UpDateBean upDateBean) {
                UpData.UpData(upDateBean);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.login_type.set(2);
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色
        /**
         * 检查更新
         */
        viewModel.getAppVersion();
        if (!isNotificationEnabled(getBaseContext())){
            KLog.d("有权限");
            openNotification(this);
        }
    }
}


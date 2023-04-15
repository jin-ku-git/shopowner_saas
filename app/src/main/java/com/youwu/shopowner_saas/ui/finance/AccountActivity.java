package com.youwu.shopowner_saas.ui.finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.tabs.TabLayout;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityAccountBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.finance.fragment.AccountFragment;
import com.youwu.shopowner_saas.ui.finance.fragment.TongJiFragment;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 财务对账
 * 2023/03/06
 */
public class AccountActivity extends BaseActivity<ActivityAccountBinding, AccountViewModel> {


    List<String> mTitle=new ArrayList<>();
    List<Fragment> mFragment=new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_account;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public AccountViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        super.initData();

        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色

        setSwipeBackEnable(false);

        mTitle.add("对账");
        mTitle.add("统计");
        mFragment.add(new AccountFragment());
        mFragment.add(new TongJiFragment());


        binding.viewPager.setOffscreenPageLimit(mFragment.size());
        /**
         * 设置适配器
         */
        binding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return mFragment.get(position);
            }
            @Override
            public int getCount() {
                return mFragment.size();
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                RxToast.normal("选中："+tab.getText());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}


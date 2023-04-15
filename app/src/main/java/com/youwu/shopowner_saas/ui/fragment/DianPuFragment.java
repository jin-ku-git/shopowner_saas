package com.youwu.shopowner_saas.ui.fragment;


import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.xuexiang.xui.widget.shadow.ShadowDrawable;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.FragmentDianPuBinding;
import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 店铺
 * 2023/03/06
 */

public class DianPuFragment extends BaseFragment<FragmentDianPuBinding,DianPuViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_dian_pu;
    }

    @Override
    public DianPuViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(DianPuViewModel.class);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 0://

                        break;

                }
            }
        });

    }

    @Override
    public void initData() {
        super.initData();

        ShadowDrawable.setShadowDrawable(binding.layoutTop, Color.parseColor("#ffffff"), dpToPx(10),
                R.color.main_white, dpToPx(5), 2, 3);
        ShadowDrawable.setShadowDrawable(binding.layoutTwo, Color.parseColor("#ffffff"), dpToPx(10),
                R.color.main_white, dpToPx(5), 2, 3);



    }



    private int dpToPx(int dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp + 0.5f);
    }
}
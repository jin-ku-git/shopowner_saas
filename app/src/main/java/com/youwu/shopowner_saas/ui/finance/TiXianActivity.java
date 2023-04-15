package com.youwu.shopowner_saas.ui.finance;

import static com.youwu.shopowner_saas.app.AppApplication.getStrCunt;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityAccountBinding;
import com.youwu.shopowner_saas.databinding.ActivityTiXianBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.finance.fragment.AccountFragment;
import com.youwu.shopowner_saas.ui.finance.fragment.TongJiFragment;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;
import com.youwu.shopowner_saas.utils_view.StringReplaceUtil;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 提现页面
 * 2023/03/07
 */
public class TiXianActivity extends BaseActivity<ActivityTiXianBinding, TiXianViewModel> {


    String beforeText;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_ti_xian;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public TiXianViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(TiXianViewModel.class);
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


        viewModel.cardNumber.set(StringReplaceUtil.idCardReplaceWithStar("62171111117289"));

        binding.editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (!TextUtils.isEmpty(arg0.toString())
                        && !arg0.toString().equals(beforeText)
                        && !arg0.toString().equals(".")) {
                    //判断数据不为空，数据有改变，数据首位不为点
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                beforeText=arg0.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editStr = editable.toString().trim();
                int posDot = editStr.indexOf(".");

                if (posDot < 0) {
                    return;
                }else if (posDot == 0) {
                    RxToast.normal("首位不可为小数点");
                    //首位是点，去掉点
                    editable.delete(posDot, posDot + 1);
                }

                //不允许输入3位小数,超过三位就删掉
                if (editStr.length() - posDot - 1 > 2) {
                    RxToast.normal("最多输入小数点后两位");
                    editable.delete(posDot + 3, posDot + 4);
                }
                //最多输入一位小数点，多的删除
                int posDotTow = editable.toString().trim().lastIndexOf(".");
                if (getStrCunt(editable.toString().trim(),".")>1){
                    RxToast.normal("最多输入一个小数点");
                    editable.delete(posDotTow, posDotTow + 1);
                }
            }
        });
    }




}


package com.youwu.shopowner_saas.ui.finance;

import static com.youwu.shopowner_saas.app.AppApplication.getStrCunt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnOptionsSelectListener;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.app.UserUtils;
import com.youwu.shopowner_saas.databinding.ActivityBankCardBinding;
import com.youwu.shopowner_saas.databinding.ActivityTiXianBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.login.LoginActivity;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;
import com.youwu.shopowner_saas.utils_view.StringReplaceUtil;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 我的银行卡
 * 2023/03/07
 */
public class BankCardActivity extends BaseActivity<ActivityBankCardBinding, BankCardViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_bank_card;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public BankCardViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(BankCardViewModel.class);
    }

    @Override
    public void initViewObservable() {
            viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                        switch (integer){//开户行
                            case 1:
                                showSelectDialog(1);
                                break;
                            case 2://性别
                                showSelectDialog(2);

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



    }





    /**
     * 选择性别/银行弹窗
     */
    private void showSelectDialog(int type) {

        final Dialog dialog = new Dialog(this, R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);


        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths);
        layoutParams.height = (int) (height*0.5);


//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCancelable(true);//点击外部消失弹窗
        dialog.show();
        //初始化控件

        TextView define = (TextView) dialogView.findViewById(R.id.define);//确定
        TextView cancel = dialogView.findViewById(R.id.cancel);//取消

        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //确定
        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
    }


}


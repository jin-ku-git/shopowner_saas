package com.youwu.shopowner_saas.toast;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.youwu.shopowner_saas.R;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class TipDialog extends CenterPopupView {


    TextView tvName;
    private String message;
    private Disposable subscribe;
    private CustomView custom_view;

    public TipDialog(@NonNull Context context, String message) {
        super(context);
        this.message = message;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.toast_tip;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        tvName=findViewById(R.id.tv_name);
        custom_view=findViewById(R.id.custom_view);
        custom_view.circleAnimation();
        tvName.setText(message);
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong >=1) {
                            subscribe.dispose();
                            dismiss();
                        }
                    }
                });
    }

}

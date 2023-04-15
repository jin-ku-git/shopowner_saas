package com.youwu.shopowner_saas.ui.fragment.custom;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.core.BubbleAttachPopupView;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.toast.RxToast;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * Description: 自定义气泡Attach弹窗
 * Create by lxj, at 2019/3/13
 */
public class CustomBubbleAttachPopup extends BubbleAttachPopupView {
    public CustomBubbleAttachPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_bubble_attach_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        final TextView tv_one = findViewById(R.id.tv_one);
        final TextView tv_two = findViewById(R.id.tv_two);

        tv_one.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.i(tv_one.getText().toString());
                if (mChoiceener!=null){
                    mChoiceener.onChoice(1);
                }

                dismiss();
            }
        });
        tv_two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.i(tv_two.getText().toString());
                if (mChoiceener!=null){
                    mChoiceener.onChoice(2);
                }
                dismiss();
            }
        });
    }

    private OnChoiceener mChoiceener;
    //选择的回调
    public interface OnChoiceener {
        void onChoice(int data);
    }

    public void setOnChoiceener(OnChoiceener listener) {
        mChoiceener = listener;
    }

}

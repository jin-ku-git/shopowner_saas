package com.youwu.shopowner_saas.ui.ping_jia;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityPingJiaBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.group.adapter.GroupManageAdapter;
import com.youwu.shopowner_saas.ui.order_goods.ApplyReturnGoodsActivity;
import com.youwu.shopowner_saas.ui.ping_jia.adapter.PingJiaAdapter;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;


import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 顾客评价页面
 * 2023/03/13
 */
public class PingJiaActivity extends BaseActivity<ActivityPingJiaBinding, PingJiaViewModel> {

    PingJiaAdapter mAdapter;
    List<String> list=new ArrayList<>();

    private int max = 300;//限制的最大字数
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_ping_jia;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public PingJiaViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(PingJiaViewModel.class);
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
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色
        viewModel.null_type.set(0);
        list=getDemoData();
        initView();
    }

    private void initView() {
        if (list.size()==0){
            viewModel.null_type.set(0);
        }else {
            viewModel.null_type.set(1);
        }

        mAdapter=new PingJiaAdapter(this,list);


        //给RecyclerView设置adapter
        binding.pingJiaRecyclerView.setAdapter(mAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.pingJiaRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.pingJiaRecyclerView.getItemDecorationCount() == 0) {
            binding.pingJiaRecyclerView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        mAdapter.setOnClickListener(new PingJiaAdapter.OnClickListener() {
            @Override
            public void onClick(String lists, int position, int type) {
                switch (type){
                    case 1://回复
                        showReplyDialog(lists);
                        break;
                }
            }
        });
    }

    private List<String> getDemoData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("第" + i + "条数据");
        }
        return list;
    }
    TextView num_text;
    EditText edit_text;

    /**
     * 回复弹窗
     */
    private void showReplyDialog(String type) {

        final Dialog dialog = new Dialog(this, R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_reply, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);


        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths);



//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCancelable(true);//点击外部消失弹窗
        dialog.show();
        //初始化控件

         edit_text = (EditText) dialogView.findViewById(R.id.edit_text);//
         num_text = dialogView.findViewById(R.id.num_text);//取消
        TextView tv_reply = dialogView.findViewById(R.id.tv_reply);//回复

        edit_text.addTextChangedListener(passwordListener());
        //回复
        tv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                RxToast.normal(edit_text.getText().toString());

            }
        });
    }
    private TextWatcher passwordListener() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                num_text.setText(length + "/" + max);
                edit_text.setFilters(new InputFilter[]{new MyLengthFilter(max,getBaseContext())});
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

     class MyLengthFilter implements InputFilter {

        private final int mMax;
        private Context context;

        public MyLengthFilter(int max, Context context) {
            mMax = max;
            this.context = context;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            int keep = mMax - (dest.length() - (dend - dstart));
            if (keep <= 0) {
                //这里，用来给用户提示
//                RxToast.normal("字数不能超过" + mMax);
                RxToast.normal("输入字数已达上限");
                return "";
            } else if (keep >= end - start) {
                return null; // keep original
            } else {
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }

        /**
         * @return the maximum length enforced by this input filter
         */
        public int getMax() {
            return mMax;
        }
    }
}


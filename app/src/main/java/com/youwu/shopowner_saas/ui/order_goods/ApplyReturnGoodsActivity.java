package com.youwu.shopowner_saas.ui.order_goods;



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

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.gson.Gson;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityApplyReturnGoodsBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner_saas.ui.order_goods.adapter.RetreatCauseAdapter;
import com.youwu.shopowner_saas.ui.order_goods.adapter.RetreatOrderGoodsAdapter;
import com.youwu.shopowner_saas.ui.order_goods.bean.CauseItemBean;
import com.youwu.shopowner_saas.utils_view.BigDecimalUtils;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * 2023/02/27
 * 退货
 * 金库
 */

public class ApplyReturnGoodsActivity extends BaseActivity<ActivityApplyReturnGoodsBinding, ApplyReturnGoodsViewModel>  {


    RetreatOrderGoodsAdapter retreatOrderGoodsAdapter;
    RetreatCauseAdapter retreatCauseAdapter;
    List<CauseItemBean> CauseList=new ArrayList<>();

    String date;
    List<XXCOrderBean.GoodsListBean> list=new ArrayList<>();
    List<XXCOrderBean.GoodsListBean> SelectList=new ArrayList<>();
    String cause;

    private int max = 30;//限制的最大字数
    @Override
    public ApplyReturnGoodsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ApplyReturnGoodsViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_apply_return_goods;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initParam() {
        super.initParam();

        date=getIntent().getStringExtra("name");
        KLog.d("传递内容"+date);
    }


    @Override
    public void initData() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色
        // 可以调用该方法，设置不允许滑动退出
        setSwipeBackEnable(false);
        viewModel.total_money.set("￥0");

        list= AppApplication.getObjectList(date, XXCOrderBean.GoodsListBean.class);


        initGoodsRecyclerView();

        initDate();
    }

    private void initDate() {
        for (int i=0;i<3;i++){
            CauseItemBean causeItemBean=new CauseItemBean();
            causeItemBean.setCause_id(i+"");
            causeItemBean.setCause_name("拒绝理由"+i);
            causeItemBean.setCause_status(false);
            CauseList.add(causeItemBean);
        }
        CauseItemBean causeItemBean1=new CauseItemBean();
        causeItemBean1.setCause_id("4");
        causeItemBean1.setCause_name("其他");
        causeItemBean1.setCause_status(false);
        CauseList.add(causeItemBean1);
        initCauseRecyclerView();
    }


    @Override
    public void initViewObservable() {

        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        String name= new Gson().toJson(SelectList);

                        RxToast.normal("选中的商品："+name+"\n理由："+  cause);

                        break;

                }
            }
        });
    }

    /**
     * 订单商品
     */
    private void initGoodsRecyclerView() {


        //创建adapter
        retreatOrderGoodsAdapter = new RetreatOrderGoodsAdapter(this, list);
        //给RecyclerView设置adapter
        binding.goodsRecycleView.setAdapter(retreatOrderGoodsAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.goodsRecycleView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.goodsRecycleView.getItemDecorationCount() == 0) {
            binding.goodsRecycleView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        retreatOrderGoodsAdapter.setOnClickListener(new RetreatOrderGoodsAdapter.OnClickListener() {
            @Override
            public void onClick(XXCOrderBean.GoodsListBean lists, int position) {
                String name= new Gson().toJson(list);
                KLog.i("name:"+name);
                SelectList.clear();
                double total_money=0.0;
                for (int i=0;i<list.size();i++){
                    if (list.get(i).isStatus()){
                        total_money+= BigDecimalUtils.multiply(list.get(i).getSelect_quantity(),list.get(i).getGoods_price());
                        SelectList.add(list.get(i));
                    }
                }
                viewModel.total_money.set("￥"+BigDecimalUtils.formatZero(total_money,2));

            }
        });

    }


    /**
     * 小程序订单
     */
    private void initCauseRecyclerView() {


        //创建adapter
        retreatCauseAdapter = new RetreatCauseAdapter(this, CauseList);
        //给RecyclerView设置adapter
        binding.causeRecycleView.setAdapter(retreatCauseAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.causeRecycleView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.causeRecycleView.getItemDecorationCount() == 0) {
            binding.causeRecycleView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        retreatCauseAdapter.setOnClickListener(new RetreatCauseAdapter.OnClickListener() {
            @Override
            public void onClick(CauseItemBean lists, int position) {

                if ("其他".equals(lists.getCause_name())){
                    for (int i=0;i<CauseList.size();i++){
                        CauseList.get(i).setCause_status(false);
                    }
                    CauseDialog(position);
                    retreatCauseAdapter.notifyDataSetChanged();
                }else {
                    for (int i=0;i<CauseList.size();i++){

                        if(i==position){
                            if (lists.isCause_status()){
                                cause=lists.getCause_name();
                            }else {
                                cause="";
                            }
                        }else {
                            CauseList.get(i).setCause_status(false);
                        }
                        KLog.i("第"+i+"个"+CauseList.get(i).isCause_status());
                    }
                    retreatCauseAdapter.notifyDataSetChanged();
                }



            }
        });


    }

    TextView num_text;
    EditText edit_text;
    /**
     * 退款原因弹窗
     * @param position
     */
    private void CauseDialog(int position) {

        final Dialog dialog = new Dialog(this, R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_cause, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);
        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths * 0.8);
        layoutParams.height = (int) (height * 0.4);
        //将界面填充到AlertDiaLog容器
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);//点击外部消失弹窗
        dialog.show();

        //初始化控件
        TextView cancel_text = (TextView) dialogView.findViewById(R.id.cancel_text);//取消
        TextView determine_text = (TextView) dialogView.findViewById(R.id.determine_text);//
         edit_text = (EditText) dialogView.findViewById(R.id.edit_text);//
         num_text = (TextView) dialogView.findViewById(R.id.num_text);//


        edit_text.addTextChangedListener(passwordListener());
        //取消
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cause="";
                CauseList.get(position).setCause_status(false);
                retreatCauseAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        //确定
        determine_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cause=edit_text.getText().toString();
                CauseList.get(position).setCause_status(true);
                retreatCauseAdapter.notifyDataSetChanged();
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

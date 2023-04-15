package com.youwu.shopowner_saas.ui.set_up;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityOrderDetailsBinding;
import com.youwu.shopowner_saas.databinding.ActivityOrderSetUpBinding;
import com.youwu.shopowner_saas.databinding.ActivityStoreSetUpBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author: 订单设置页面
 * @date: 2022/8/12
 */
public class OrderSetUpActivity extends BaseActivity<ActivityOrderSetUpBinding, OrderSetUpViewModel> {


    private int type;//是否自动接单 1是 2否
    private int feie_type;//1 连接 2 不连接
    private TimePickerView pvCustomTime;//时间选择器

    private int time_state;//1 开始 2 结束
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_set_up;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public OrderSetUpViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(OrderSetUpViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://开始时间点击事件
                        time_state=1;
                        pvCustomTime.show(); //弹出自定义时间选择器
                        break;
                    case 2://结束时间点击事件
                        time_state=2;
                        pvCustomTime.show(); //弹出自定义时间选择器
                        break;
                    case 3://点击确认


                        initUpdate();
                        break;
                    case 4://
                        RxToast.showTipToast(OrderSetUpActivity.this, "更新成功");
                        AppApplication.spUtils.put("is_order",type);
//                        AppApplication.spUtils.put("start_time",viewModel.start_time.get());
//                        AppApplication.spUtils.put("end_time",viewModel.end_time.get());
                        break;
                    case 5:
                        type=  AppApplication.spUtils.getInt("ReceivingOrdersType");

                        setHide_radius();
                        break;

                }
            }
        });
    }

    private void initUpdate() {
        KLog.d("是否自动接单："+((type==1)?"是":"否")+"\n营业时间："+viewModel.start_time.get()+"至"+viewModel.end_time.get());
//        List<Integer> list=new ArrayList<>();
//
//        if (binding.modeOne.isChecked()){
//            list.add(3);
//        }
//        if (binding.modeTwo.isChecked()){
//            list.add(4);
//        }
//        if (binding.modeThree.isChecked()){
//            list.add(5);
//        }
//        if (binding.modeFour.isChecked()){
//            list.add(6);
//        }
//        String delivery_method="";
//        for (int i=0;i<list.size();i++){
//            delivery_method+=list.get(i)+",";
//        }
//        if (!"".equals(delivery_method)){
//            //去掉最后一个逗号
//            delivery_method=delivery_method.substring(0,delivery_method.length()-1);
//        }
//
//        viewModel.update_store(type,delivery_method,feie_type+"");

        viewModel.new_update_store(type);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色

//        viewModel.setting_list();
        viewModel.new_store_info();

        viewModel.state.setValue(false);

        type=  AppApplication.spUtils.getInt("is_order");
        viewModel.start_time.set(AppApplication.spUtils.getString("start_time"));
        viewModel.end_time.set(AppApplication.spUtils.getString("end_time"));
        setHide_radius();

        binding.hideRadiusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hide_radius_yes:
                        type=1;
                        break;

                    case R.id.hide_radius_no:
                        type=2;
                        break;

                    default:
                        break;
                }
            }
        });

        binding.feieRadiusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.feie_radius_yes:
                        feie_type=1;
                        viewModel.state.setValue(true);
                        break;

                    case R.id.feie_radius_no:
                        feie_type=2;
                        viewModel.state.setValue(false);
                        break;

                    default:
                        break;
                }
            }
        });

        initCustomTimePicker();
        setHide_radius();
    }


    private void setHide_radius() {
        if (type==1){
            binding.hideRadiusYes.setChecked(true);
        }else {
            binding.hideRadiusNo.setChecked(true);
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:00");
        return format.format(date);
    }


    private void initCustomTimePicker() {
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2009, 0, 1);
        Calendar endDate = Calendar.getInstance();
//        endDate.set(2299, 11, 31);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                if (time_state==1){
                    viewModel.start_time.set(getTime(date));
                }else {
                    viewModel.end_time.set(getTime(date));
                }


            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})//分别对应年月日时分秒，默认全部显示
                .setContentTextSize(28)//滚轮文字大小
                .setTitleSize(26)//标题文字大小
                .setLineSpacingMultiplier(2.0f)//设置间距倍数
                .setItemVisibleCount(7)//设置最大可见数目
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xFFfafafa)//滚轮背景颜色 Night mode
//                .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isDialog(true)//是否显示为对话框样式
                .build();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
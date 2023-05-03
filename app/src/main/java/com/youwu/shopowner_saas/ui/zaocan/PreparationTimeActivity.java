package com.youwu.shopowner_saas.ui.zaocan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bin.david.form.core.SmartTable;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityPeriodTimeBinding;
import com.youwu.shopowner_saas.ui.zaocan.bean.PeriodInfo;
import com.youwu.shopowner_saas.ui.zaocan.bean.PreparationInfo;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;
import com.youwu.shopowner_saas.utils_view.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 早餐时段备料明细页面
 * @author: Administrator
 * @date: 2023/04/27
 */
public class PreparationTimeActivity extends BaseActivity<ActivityPeriodTimeBinding, PeriodTimeViewModel> {


    String store_id;//门店id
    String channel_id;//1早餐/2食材
    String appointment_time;//配送时间
    private SmartTable<PreparationInfo> periodTable;

    List<PreparationInfo> list = new ArrayList<>();
    String TopName;

    private TimePickerView pvCustomTime;//时间选择器

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_period_time;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        TopName=getIntent().getStringExtra("TopName");
        channel_id=getIntent().getStringExtra("channel_id");
    }

    @Override
    public PeriodTimeViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(PeriodTimeViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://选择日期
                        pvCustomTime.show();
                        break;
                    case 2:

                        getTimeGoodsNum();
                        break;


                }
            }
        });
        viewModel.PreparationInfoEvent.observe(this, new Observer<ArrayList<PreparationInfo>>() {
            @Override
            public void onChanged(ArrayList<PreparationInfo> preparationInfos) {
                list.clear();
                for(int i = 0;i <preparationInfos.size(); i++) {

                    list.add(new PreparationInfo(preparationInfos.get(i).getTime(), preparationInfos.get(i).getGoodsName(),preparationInfos.get(i).getNumber()));

                }
                periodTable.setData(list);
            }
        });
    }
    //查询
    private void getTimeGoodsNum() {
        appointment_time= TimeUtil.getTimeStemp(viewModel.TimeField.get(),"yyyy-MM-dd")/1000+"";
        viewModel.getTimeGoodsNum(channel_id,store_id,appointment_time);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色
        store_id= AppApplication.spUtils.getString("StoreId");

        viewModel.TopNameField.set(TopName);

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        String time=getTime(selectedDate.getTime());
        viewModel.TimeField.set(time);
        appointment_time= TimeUtil.getTimeStemp(time,"yyyy-MM-dd")/1000+"";

        initCustomTimePicker();

        viewModel.getTimeGoodsNum(channel_id,store_id,appointment_time);


        periodTable = (SmartTable<PreparationInfo>) findViewById(R.id.periodTable);

        periodTable.getConfig().setShowTableTitle(false);
        periodTable.setZoom(true,2,0.2f);

    }



    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
        //最多选明天
        endDate.add(Calendar.DATE,1);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                birthday.setText(getTime(date));
                KLog.d("选择时间："+getTime(date));
                KLog.d("现在时间："+getTime(Calendar.getInstance().getTime()));
                viewModel.TimeField.set(getTime(date));
                getTimeGoodsNum();
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
                .setType(new boolean[]{true, true, true, false, false, false})//分别对应年月日时分秒，默认全部显示
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
                .isDialog(false)//是否显示为对话框样式
                .build();
    }

}


package com.youwu.shopowner_saas.ui.main;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.alibaba.fastjson.JSON;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.NotificationClickReceiver;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityMainBinding;
import com.youwu.shopowner_saas.mqtt.service.IGetMessageCallBack;
import com.youwu.shopowner_saas.mqtt.service.MQTTService;
import com.youwu.shopowner_saas.mqtt.service.MQTTServiceConnction;
import com.youwu.shopowner_saas.queue.LogTask;
import com.youwu.shopowner_saas.queue.TaskPriority;
import com.youwu.shopowner_saas.ui.fragment.DianPuFragment;
import com.youwu.shopowner_saas.ui.fragment.FourFragment;
import com.youwu.shopowner_saas.ui.fragment.MyFragment;
import com.youwu.shopowner_saas.ui.fragment.OneFragment;
import com.youwu.shopowner_saas.ui.fragment.ThreeFragment;
import com.youwu.shopowner_saas.ui.fragment.TwoFragment;
import com.youwu.shopowner_saas.ui.fragment.bean.MqttBean;
import com.youwu.shopowner_saas.ui.order_record.bean.OrderGoodsBean;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * 2021/12/12
 * 首页
 * 金库
 */

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements IGetMessageCallBack {


    private OneFragment mOneFragment;
    private TwoFragment mTowFragment;

    private ThreeFragment mThreeFragment;
    private FourFragment mFourFragment;
    private DianPuFragment mDianPuFragment;
    private MyFragment mMyFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;



    /**
     * MQTT
     */
    private MQTTServiceConnction serviceConnection;

    private NotificationManager NOManager;
    private Notification notification;

    PendingIntent pendingIntent;

    int type=1;

    OrderGoodsBean orderGoodsBean;
    @Override
    public MainViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initParam() {
        super.initParam();

        type=getIntent().getIntExtra("type",1);
        orderGoodsBean= (OrderGoodsBean) getIntent().getSerializableExtra("OrderGoodsBean");
    }


    @Override
    public void initData() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色
        // 可以调用该方法，设置不允许滑动退出
        setSwipeBackEnable(false);


        setSwPage(type);

        //初始化
        initAndroidMQTT();

        NOManager=(NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        Intent intent=new Intent(this, NotificationClickReceiver.class);
        intent.putExtra("type","2");
        pendingIntent =PendingIntent.getBroadcast(this, 0, intent, 0);

        //检查是否已经注册
        if(!EventBus.getDefault().isRegistered(this)){//是否注册eventbus的判断
            EventBus.getDefault().register(this);
        }
    }


    //MainActivity传递的数据
    @Subscribe
    public void onMQttBean(EventBusBean eventBusBean) {
        viewModel.IntegerEvent.setValue(eventBusBean.getType());

    };


    @Override
    public void initViewObservable() {

        //点击的监听
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:

                        setSwPage(1);
                        EventBus.getDefault().post("1");
                        break;
                    case 2:

                        setSwPage(2);
                        EventBus.getDefault().post("2");
                        break;
                    case 3:

                        setSwPage(3);
                        EventBus.getDefault().post("3");
                        break;
                    case 4:

                        setSwPage(4);
                        EventBus.getDefault().post("4");
                        break;

                    case 5:

                        setSwPage(5);
                        EventBus.getDefault().post("5");
                        break;
                    case 6:

                        setSwPage(6);
                        EventBus.getDefault().post("6");
                        break;

                }
            }
        });
    }
    private void initAndroidMQTT() {
        serviceConnection = new MQTTServiceConnction();
        serviceConnection.setIGetMessageCallBack(this);
        //用Intent方式创建并启用Service
        Intent intent = new Intent(this, MQTTService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void setMessage(String message) {
        KLog.a("收到的推送数据：" + message);



        if (message!=null){
            MqttBean mqttBean = JSON.parseObject(toPrettyFormat(message), MqttBean.class);

            EventBus.getDefault().post(mqttBean);

            KLog.d("pay_sn:"+mqttBean.getPay_sn());
            KLog.d("type:"+mqttBean.getType());
            KLog.d("shipping_type:"+mqttBean.getShipping_type());

            Notification.Builder builder1 = new Notification.Builder(MainActivity.this);
            builder1.setContentTitle(getApplicationName())
                    .setSmallIcon(R.mipmap.logo)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)//打开程序后图标消失
                    .setContentIntent(pendingIntent);

            builder1.setContentText("您有一个待处理订单，请及时处理！");
            new LogTask("DEFAULT","您有一个待处理订单，请及时处理！")
                    .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
                    .enqueue(); //入队

//            if (mqttBean.getOrder_status()==1){
//
//                switch (mqttBean.getShipping_type()){
//                    case 1:
//                        builder1.setContentText("您有一个外卖订单，请及时处理！");
//                        new LogTask("DEFAULT","您有一个外卖订单，请及时处理！")
//                                .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
//                                .enqueue(); //入队
//                        break;
//                    case 2:
//                        builder1.setContentText("您有一个门店自提订单，请及时处理！");
//                        new LogTask("DEFAULT","您有一个门店自提订单，请及时处理！")
//                                .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
//                                .enqueue(); //入队
//                        break;
//                    case 3:
//                        builder1.setContentText("您有一个取餐点自提订单，请及时处理！");
//                        new LogTask("DEFAULT","您有一个取餐点自提订单，请及时处理！")
//                                .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
//                                .enqueue(); //入队
//                        break;
//                    case 4:
//                        builder1.setContentText("您有一个堂食订单，请及时处理！");
//                        new LogTask("DEFAULT","您有一个堂食订单，请及时处理！")
//                                .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
//                                .enqueue(); //入队
//                        break;
//                    case 5:
//                        builder1.setContentText("您有一个即食订单，请及时处理！");
//                        new LogTask("DEFAULT","您有一个即食订单，请及时处理！")
//                                .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
//                                .enqueue(); //入队
//                        break;
//                    case 6:
//                        builder1.setContentText("您有一个预约订单，请及时处理！");
//                        new LogTask("DEFAULT","您有一个预约订单，请及时处理！")
//                                .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
//                                .enqueue(); //入队
//                        break;
//                }
//
//            }else if(mqttBean.getOrder_status()==2){
//                builder1.setContentText("您有一个申请退款订单，请及时处理！");
//                new LogTask("DEFAULT","您有一个申请退款订单，请及时处理！")
//                        .setPriority(TaskPriority.DEFAULT) //设置优先级，默认是DEFAULT
//                        .enqueue(); //入队
//            }
            notification = builder1.build();
            NOManager.notify(1,notification);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void setSwPage(int i) {

        //获取FragmentManager对象
        manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);

        switch (i) {
            case 1:
                binding.oneHome.setonSelected(true);
                if (mOneFragment == null) {
                    mOneFragment = new OneFragment();
                    transaction.add(R.id.frame, mOneFragment);
                } else {
                    //对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mOneFragment);
                }
                break;
            case 2:
                binding.twoHome.setonSelected(true);

                if (mTowFragment == null) {
                    mTowFragment = new TwoFragment(orderGoodsBean);
                    transaction.add(R.id.frame, mTowFragment);
                } else {
                    //对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mTowFragment);
                }

                break;
            case 3:
                binding.threeHome.setonSelected(true);
                if (mThreeFragment == null) {
                    mThreeFragment = new ThreeFragment();
                    transaction.add(R.id.frame, mThreeFragment);
                } else {
                    //对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mThreeFragment);
                }
                break;
            case 4:
                binding.fourHome.setonSelected(true);
                if (mFourFragment == null) {
                    mFourFragment = new FourFragment();
                    transaction.add(R.id.frame, mFourFragment);
                } else {
                    //对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFourFragment);
                }
                break;
            case 5:
                binding.dianPuHome.setonSelected(true);
                if (mDianPuFragment == null) {
                    mDianPuFragment = new DianPuFragment();
                    transaction.add(R.id.frame, mDianPuFragment);
                } else {
                    //对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mDianPuFragment);
                }
                break;
            case 6:
                binding.myHome.setonSelected(true);
                if (mMyFragment == null) {
                    mMyFragment = new MyFragment();
                    transaction.add(R.id.frame, mMyFragment);
                } else {
                    //对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mMyFragment);
                }
                break;
        }
        transaction.commit();
    }

    //将全部Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        binding.oneHome.setonSelected(false);
        binding.twoHome.setonSelected(false);
        binding.threeHome.setonSelected(false);
        binding.fourHome.setonSelected(false);
        binding.dianPuHome.setonSelected(false);
        binding.myHome.setonSelected(false);
        if (mOneFragment != null) {
            transaction.hide(mOneFragment);
        }
        if (mTowFragment != null) {
            transaction.hide(mTowFragment);
        }
        if (mThreeFragment != null) {
            transaction.hide(mThreeFragment);
        }
        if (mFourFragment != null) {
            transaction.hide(mFourFragment);
        }
        if (mDianPuFragment != null) {
            transaction.hide(mDianPuFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
    }
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示

                View toastRoot = getLayoutInflater().inflate(R.layout.my_toast, null);
                Toast toast = new Toast(this);
                toast.setView(toastRoot);
                TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
                tv.setText("再按一次退出程序");
                toast.setGravity(Gravity.BOTTOM, 0, 150);
                toast.show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        //反注册
        EventBus.getDefault().unregister(this);


    }
}

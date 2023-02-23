package com.youwu.shopowner_saas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.youwu.shopowner_saas.ui.order_goods.OrderReceivingActivity;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author: Administrator
 * @date: 2022/9/16
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        KLog.d("我被点击了");
        Intent newIntent = new Intent(context, OrderReceivingActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }
}



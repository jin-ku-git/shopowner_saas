package com.youwu.shopowner_saas.utils_view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: Administrator
 * @date: 2022/9/16
 */
public class NotificationsUtils {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 判断通知栏权限是否开启
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager mAppOps =
                    (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;
            Class appOpsClass = null;

            /* Context.APP_OPS_MANAGER */
            try {
                appOpsClass = Class.forName(AppOpsManager.class.getName());

                Method checkOpNoThrowMethod =
                        appOpsClass.getMethod(CHECK_OP_NO_THROW,
                                Integer.TYPE, Integer.TYPE, String.class);

                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                int value = (Integer) opPostNotificationValue.get(Integer.class);

                return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
                        AppOpsManager.MODE_ALLOWED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }else {
            return true;
        }
    }

    /**
     * 跳转设置通知栏
     * @param activity
     */
    public static void openNotification(Activity activity){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);

            localIntent.setClassName("com.android.settings",
                    "com.android.settings.InstalledAppDetails");

            localIntent.putExtra("com.android.settings.ApplicationPkgName",
                    activity.getPackageName());
        }
        activity.startActivity(localIntent);
    }
}


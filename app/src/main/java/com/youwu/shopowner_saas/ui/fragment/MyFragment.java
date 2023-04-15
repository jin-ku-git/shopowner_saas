package com.youwu.shopowner_saas.ui.fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CONNECTIVITY_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.xuexiang.xqrcode.ui.CaptureActivity;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.app.UserUtils;
import com.youwu.shopowner_saas.databinding.FragmentFourBinding;
import com.youwu.shopowner_saas.databinding.FragmentMyBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.goods_operate.InventoryActivity;
import com.youwu.shopowner_saas.ui.goods_operate.LossReportingActivity;
import com.youwu.shopowner_saas.ui.goods_operate.ReturnGoodsActivity;
import com.youwu.shopowner_saas.ui.goods_operate.SellOffActivity;
import com.youwu.shopowner_saas.ui.login.LoginActivity;
import com.youwu.shopowner_saas.ui.main.EventBusBean;
import com.youwu.shopowner_saas.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 2023/03/06
 */

public class MyFragment extends BaseFragment<FragmentMyBinding,MyViewModel> {

    private ConnectivityManager mConnectivityManager = null;
    private NetworkInfo mActiveNetInfo = null;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_my;
    }

    @Override
    public MyViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MyViewModel.class);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 0://全部
                        EventBus.getDefault().post(new EventBusBean(3,0));
                        break;
                    case 1:
                        AppApplication.mSpeechSynthesizer.speak("测试语音");
                        break;
                    case 88:
                        showDialog();
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
        super.initData();


        mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);//获取系统的连接服务
        mActiveNetInfo = mConnectivityManager.getActiveNetworkInfo();//获取网络连接的信息

        setUpInfo();
    }

    public String getIPAddress() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if ((info.getType() == ConnectivityManager.TYPE_MOBILE) || (info.getType() == ConnectivityManager.TYPE_WIFI) ){//当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                }
                catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }
        else { //当前无网络连接,请在设置中打开网络
            return null;
        }
        return null;
    }

    public void setUpInfo()  {
        if(mActiveNetInfo.getType()==ConnectivityManager.TYPE_WIFI)  {

            viewModel.IPNameEvent.set("网络类型：WIFI");
            viewModel.IPEvent.set("IP地址："+getIPAddress());
        }
        else if(mActiveNetInfo.getType()==ConnectivityManager.TYPE_MOBILE)  {

            viewModel.IPNameEvent.set("网络类型：3G/4G");
            viewModel.IPEvent.set("IP地址："+getIPAddress());
        }
        else  {

            viewModel.IPNameEvent.set("网络类型：未知");
            viewModel.IPEvent.set("IP地址：");
        }
    }

    /**
     * 退出登录提示弹窗
     */
    private void showDialog() {

        final Dialog dialog = new Dialog(getContext(), R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.tips_dialog, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);


        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths * 0.7);
//        layoutParams.height = (int) (height);


//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);//点击外部消失弹窗
        dialog.show();
        //初始化控件

        TextView determine_text = (TextView) dialogView.findViewById(R.id.determine_text);//确定
        TextView cancel_text = dialogView.findViewById(R.id.cancel_text);//取消

        //取消
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //确定
        determine_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TestXML", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear().commit();


                UserUtils.logout();


                Intent intent=new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
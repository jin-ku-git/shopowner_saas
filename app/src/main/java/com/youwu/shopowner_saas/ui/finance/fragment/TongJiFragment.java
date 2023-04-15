package com.youwu.shopowner_saas.ui.finance.fragment;

import static com.youwu.shopowner_saas.utils_view.TimeUtil.getTimeDaysList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.FragmentAccountBinding;
import com.youwu.shopowner_saas.databinding.FragmentTongJiBinding;
import com.youwu.shopowner_saas.ui.finance.adapter.AccountAdapter;
import com.youwu.shopowner_saas.ui.fragment.adapter.SpinnerAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.SpinnerBean;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 2023/03/07
 * 统计页面
 */

public class TongJiFragment extends BaseFragment<FragmentTongJiBinding, TongJiFragmentViewModel> {



    List<String> TimeList=new ArrayList<>();

    private String appointment_time;//预约配送日期

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_tong_ji;
    }
    @Override
    public TongJiFragmentViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(TongJiFragmentViewModel.class);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }



    @Override
    public void initViewObservable() {

    }
    @Override
    public void initData() {



        initSpinner();

    }


    //时间下拉框选择
    private void initSpinner() {
        //获取近半年的月份
        TimeList= getTimeDaysList(6);

        String submitJsonData = new Gson().toJson(TimeList);
        KLog.i("返回的时间："+submitJsonData);

        List<SpinnerBean> list=new ArrayList<>();

        for (int i=0;i<TimeList.size();i++){
            KLog.d("时间："+TimeList.get(i));
            SpinnerBean spinnerBean=new SpinnerBean();
            spinnerBean.setId(i+1);
            spinnerBean.setName(TimeList.get(i));
            if (i==0){
                spinnerBean.setSelect(true);
            }else {
                spinnerBean.setSelect(false);
            }

            list.add(spinnerBean);
        }



        SpinnerAdapter adapter=new SpinnerAdapter(getContext(),list);

        adapter.setType(1);
        binding.DropdownBox.setAdapter(adapter);
        binding.DropdownBox.setSelection(0);

        binding.DropdownBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (int i=0;i<list.size();i++){
                    list.get(i).setSelect(false);
                }
                list.get(pos).setSelect(true);

                appointment_time=TimeList.get(pos);
                KLog.i("选择日期："+appointment_time);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


}

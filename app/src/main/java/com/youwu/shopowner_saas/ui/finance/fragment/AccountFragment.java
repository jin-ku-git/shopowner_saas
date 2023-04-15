package com.youwu.shopowner_saas.ui.finance.fragment;

import static com.youwu.shopowner_saas.utils_view.TimeUtil.getMonthDiff;
import static com.youwu.shopowner_saas.utils_view.TimeUtil.getTimeDaysList;
import static com.youwu.shopowner_saas.utils_view.TimeUtil.getTimeList;
import static com.youwu.shopowner_saas.utils_view.TimeUtil.getTimeYearList;
import static com.youwu.shopowner_saas.utils_view.TimeUtil.toDate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.FragmentAccountBinding;
import com.youwu.shopowner_saas.databinding.FragmentFormBinding;
import com.youwu.shopowner_saas.entity.FormEntity;
import com.youwu.shopowner_saas.ui.finance.adapter.AccountAdapter;
import com.youwu.shopowner_saas.ui.form.FormViewModel;
import com.youwu.shopowner_saas.ui.fragment.MyViewModel;
import com.youwu.shopowner_saas.ui.fragment.adapter.OneOrderAdapter;
import com.youwu.shopowner_saas.ui.fragment.adapter.SpinnerAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.OrderBean;
import com.youwu.shopowner_saas.ui.fragment.bean.SpinnerBean;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;

/**
 * 2023/03/06
 * 对账页面
 */

public class AccountFragment extends BaseFragment<FragmentAccountBinding, AccountFragmentViewModel> {



    List<String> TimeList=new ArrayList<>();
    List<String> list=new ArrayList<>();
    private String appointment_time;//预约配送日期

    AccountAdapter mAccountAdapter;
    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_account;
    }
    @Override
    public AccountFragmentViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(AccountFragmentViewModel.class);
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


        viewModel.bth_one.set(1);
        viewModel.null_type.set(0);

        initSpinner();
        initDatas();
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
    //初始化数据
    private void initDatas() {

        for (int i=0;i<10;i++){
            list.add("数据"+i);
        }
        initRecyclerView();
    }


    /**
     * 订单列表
     */
    private void initRecyclerView() {

        if (list.size()>0){
            viewModel.null_type.set(1);
        }

        //创建adapter
        mAccountAdapter = new AccountAdapter(getContext(), list);
        //给RecyclerView设置adapter
        binding.accountRecyclerView.setAdapter(mAccountAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.accountRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.accountRecyclerView.getItemDecorationCount() == 0) {
            binding.accountRecyclerView.addItemDecoration(new DividerItemDecorations(getContext(), DividerItemDecorations.VERTICAL));
        }

    }

}

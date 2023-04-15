package com.youwu.shopowner_saas.ui.finance.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.FragmentGoodsBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.finance.adapter.GoodsAnalyseAdapter;
import com.youwu.shopowner_saas.ui.finance.adapter.GoodsRankingAdapter;
import com.youwu.shopowner_saas.ui.fragment.adapter.SpinnerAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.SpinnerBean;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 2023/03/21
 * 商品页面
 */

public class GoodsFragment extends BaseFragment<FragmentGoodsBinding, GoodsFragmentViewModel>{




    List<String> list=new ArrayList<>();


    GoodsRankingAdapter mGoodsRankingAdapter;


    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_goods;
    }
    @Override
    public GoodsFragmentViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(GoodsFragmentViewModel.class);
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

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String dateStr = formatter.format(date);
        viewModel.Time.set(dateStr);


        initDatas();
        initSpinner();
    }

    private void initSpinner() {
        List<SpinnerBean> list=new ArrayList<>();

        for (int i=0;i<5;i++){
            SpinnerBean Bean=new SpinnerBean();
            Bean.setId(i);
            if (i==0){
                Bean.setName("请选择");
                Bean.setSelect(false);
            }else if (i==1){
                Bean.setName("订单号");
                Bean.setSelect(true);
            }else if (i==2){
                Bean.setName("商品名称");
                Bean.setSelect(false);
            }else if (i==3){
                Bean.setName("手机尾号(4位)");
                Bean.setSelect(false);
            }else if (i==4){
                Bean.setName("顾客地址");
                Bean.setSelect(false);
            }
            list.add(Bean);
        }

        SpinnerAdapter adapter=new SpinnerAdapter(getContext(),list);


        binding.DropdownBox.setAdapter(adapter);
        binding.DropdownBox.setSelection(1);

        binding.DropdownBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (int i=0;i<list.size();i++){
                    list.get(i).setSelect(false);
                }
                list.get(pos).setSelect(true);


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
        mGoodsRankingAdapter = new GoodsRankingAdapter(getContext(), list);
        //给RecyclerView设置adapter
        binding.goodsRecyclerView.setAdapter(mGoodsRankingAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.goodsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.goodsRecyclerView.getItemDecorationCount() == 0) {
            binding.goodsRecyclerView.addItemDecoration(new DividerItemDecorations(getContext(), DividerItemDecorations.VERTICAL));
        }

    }

}

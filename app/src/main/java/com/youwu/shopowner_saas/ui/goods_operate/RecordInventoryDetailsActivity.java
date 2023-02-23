package com.youwu.shopowner_saas.ui.goods_operate;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.pickerview.view.TimePickerView;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityRecordInventoryDetailsBinding;
import com.youwu.shopowner_saas.ui.fragment.adapter.InventoryRecycleAdapter;
import com.youwu.shopowner_saas.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner_saas.utils_view.BigDecimalUtils;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 盘点记录详情
 * @author: Administrator
 * @date: 2022/11/14
 */
public class RecordInventoryDetailsActivity extends BaseActivity<ActivityRecordInventoryDetailsBinding, RecordInventoryDetailsViewModel> {


    String TotalPrice;
    String TotalType;
    String TotalQuantity;
    int goods_num;

    private TimePickerView pvCustomTime;//时间选择器

    //购物车recyclerveiw的适配器
    private InventoryRecycleAdapter mInventoryRecycleAdapter;
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<ScrollBean.SAASOrderBean> ShoppingEntityList = new ArrayList<ScrollBean.SAASOrderBean>();

    String store_id;//门店id
    @Override
    public void initParam() {
        super.initParam();

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_record_inventory_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public RecordInventoryDetailsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RecordInventoryDetailsViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){




                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色

        store_id= AppApplication.spUtils.getString("StoreId");
        viewModel.TotalPrice.set(TotalPrice);
        viewModel.TotalType.set(TotalType);
        viewModel.TotalQuantity.set(TotalQuantity);

//        initRecyclerView();


    }



    /**
     * 购物车列表
     */
    private void initRecyclerView() {


        //创建adapter
        mInventoryRecycleAdapter = new InventoryRecycleAdapter(this, ShoppingEntityList);
        //给RecyclerView设置adapter
        binding.goodsRecyclerview.setAdapter(mInventoryRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.goodsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.goodsRecyclerview.getItemDecorationCount() == 0) {
            binding.goodsRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }

        /**
         * 加减
         */
        mInventoryRecycleAdapter.setOnChangeListener(new InventoryRecycleAdapter.OnChangeListener() {
            @Override
            public void onChange(ScrollBean.SAASOrderBean data, int position) {

                ShoppingEntityList.get(position).setChange_stock(data.getChange_stock());

                cll();

            }
        });
        /**
         * 删除
         */
        mInventoryRecycleAdapter.setOnDeleteListener(new InventoryRecycleAdapter.OnDeleteListener() {
            @Override
            public void onDelete(ScrollBean.SAASOrderBean data, int position) {
                ShoppingEntityList.get(position).setChange_stock(0);
                ShoppingEntityList.remove(position);

                cll();
            }
        });
        mInventoryRecycleAdapter.setOnEditListener(new InventoryRecycleAdapter.OnEditListener() {
            @Override
            public void onEdit(ScrollBean.SAASOrderBean lists, int position) {
                ShoppingEntityList.get(position).setChange_stock(lists.getChange_stock());

                cll();
            }
        });
    }

    /**
     * 计算价格
     *
     *
     */
    private void cll() {

        double prick=0.0;
        int quantity=0;
        for (int i=0;i<ShoppingEntityList.size();i++){
            prick+= BigDecimalUtils.formatRoundUp((Double.parseDouble(ShoppingEntityList.get(i).getOrder_price())*ShoppingEntityList.get(i).getChange_stock()),2);
            quantity+=ShoppingEntityList.get(i).getChange_stock();
        }

        viewModel.TotalPrice.set(prick+"");
        viewModel.TotalType.set(ShoppingEntityList.size()+"");
        viewModel.TotalQuantity.set(quantity+"");


    }




    public ArrayList duplicateRemovalBySet(ArrayList<ScrollBean.SAASOrderBean> list){
        Set set = new HashSet();
        list.addAll(set);
        for(int i = 0;i < list.size();i++){
            set.add(list.get(i));
        }
        ArrayList newlist = new ArrayList();
        newlist.addAll(set);
        return newlist;
    }
}

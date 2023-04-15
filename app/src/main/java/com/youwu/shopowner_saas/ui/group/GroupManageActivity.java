package com.youwu.shopowner_saas.ui.group;



import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;

import com.youwu.shopowner_saas.app.AppViewModelFactory;

import com.youwu.shopowner_saas.databinding.ActivityGroupManageBinding;

import com.youwu.shopowner_saas.ui.goods_operate.NewCommodityActivity;
import com.youwu.shopowner_saas.ui.group.adapter.GroupManageAdapter;

import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 群组管理页面
 * 2023/03/06
 */
public class GroupManageActivity extends BaseActivity<ActivityGroupManageBinding, GroupManageViewModel> {

    private GroupManageAdapter mAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_group_manage;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public GroupManageViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(GroupManageViewModel.class);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        super.initData();

        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色



        initViews();
    }


    protected void initViews() {

        mAdapter=new GroupManageAdapter(this,getDemoData());


        //给RecyclerView设置adapter
        binding.recyclerView.setAdapter(mAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.recyclerView.getItemDecorationCount() == 0) {
            binding.recyclerView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }

        mAdapter.setOnClickListener(new GroupManageAdapter.OnClickListener() {
            @Override
            public void onClick(String lists, int position,int type) {
                switch (type){
                    case 1://编辑
                        Bundle bundle=new Bundle();
                        bundle.putInt("type",2);
                        bundle.putString("name",lists);
                        startActivity(NewGroupActivity.class,bundle);
                        break;
                    case 2://新建商品
                        startActivity(NewCommodityActivity.class);
                        break;
                }

            }
        });

    }

    private List<String> getDemoData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(""+i);
        }
        return list;
    }
}


package com.youwu.shopowner_saas.ui.group;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.xuexiang.xui.utils.WidgetUtils;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.touch.OnItemStateChangedListener;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityGroupManageBinding;
import com.youwu.shopowner_saas.databinding.ActivityGroupSortBinding;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.group.adapter.SwipeDragTouchListAdapter;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 群组排序
 * 2023/03/06
 */
public class GroupSortActivity extends BaseActivity<ActivityGroupSortBinding, GroupSortViewModel> {

    private SwipeDragTouchListAdapter mAdapter;
    List<String> list=new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_group_sort;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public GroupSortViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(GroupSortViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://完成

                        String JsonData = new Gson().toJson(list);

                        KLog.i("JsonData："+JsonData);

                        break;
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
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色

        list=getDemoData();

        initViews();
    }


    protected void initViews() {
        WidgetUtils.initRecyclerView( binding.recyclerView);

        mAdapter=new SwipeDragTouchListAdapter(this,list);

        // 监听拖拽和侧滑删除，更新UI和数据源。
        binding.recyclerView.setOnItemMoveListener(onItemMoveListener);
        // 监听Item的手指状态:拖拽、侧滑、松开。
        binding.recyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener);


        //给RecyclerView设置adapter
        binding.recyclerView.setAdapter(mAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.recyclerView.getItemDecorationCount() == 0) {
            binding.recyclerView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }

        // 长按拖拽，默认关闭。
        binding.recyclerView.setLongPressDragEnabled(true);
        // 滑动删除，默认关闭。
        binding.recyclerView.setItemViewSwipeEnabled(false);

    }


    /**
     * Item的拖拽/侧滑删除时，手指状态发生变化监听。
     */
    private OnItemStateChangedListener mOnItemStateChangedListener = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            KLog.i("actionState："+actionState);
            if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {
                // 网格布局 设置选中放大
                ViewCompat.animate(viewHolder.itemView).setDuration(200).scaleX(1.3F).scaleY(1.3F).start();
                viewModel.typeName.set("状态：拖拽");
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {
                viewModel.typeName.set("状态：滑动删除");
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {
                viewModel.typeName.set("状态：手指松开");
                mAdapter.notifyDataSetChanged();

            }
        }
    };

    /**
     * 监听拖拽和侧滑删除，更新UI和数据源。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            int position = mAdapter.onGetItem(srcHolder);
            RxToast.normal("现在的第"+position+"个");
            return mAdapter.onMoveItem(srcHolder, targetHolder);
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            int position = mAdapter.onRemoveItem(srcHolder);
            RxToast.normal("现在的第" + (position + 1) + "被删除。");
        }

    };

    private List<String> getDemoData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("第" + i + "条数据");
        }
        return list;
    }
}


package com.youwu.shopowner_saas.ui.goods_operate;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.touch.OnItemStateChangedListener;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppApplication;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityGoodsSetUpTowBinding;
import com.youwu.shopowner_saas.databinding.ActivityGoodsSortBinding;
import com.youwu.shopowner_saas.db.GoodsDao;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.fragment.bean.CommunityBean;
import com.youwu.shopowner_saas.ui.fragment.bean.GroupBean;
import com.youwu.shopowner_saas.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner_saas.ui.goods_operate.adapter.GodosSetUpRightAdapter;
import com.youwu.shopowner_saas.ui.goods_operate.adapter.GoodsDragAdapter;
import com.youwu.shopowner_saas.ui.goods_operate.adapter.GoodsSetUpAdapter;
import com.youwu.shopowner_saas.ui.group.adapter.SwipeDragTouchListAdapter;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 商品排序页面
 * @author: Administrator
 * @date: 2023/03/11
 */
public class GoodsSortActivity extends BaseActivity<ActivityGoodsSortBinding, GoodsSortViewModel> {


    //分类recyclerveiw的适配器
    private GoodsSetUpAdapter mCommunityRecycleAdapter;
    //定义以CommunityEntityList实体类为对象的数据集合
    private ArrayList<GroupBean> CommunityEntityList = new ArrayList<>();

    //商品recyclerveiw的适配器
    private GodosSetUpRightAdapter mCabinetListRecycleAdapter;
    //定义以CabinetEntityList实体类为对象的数据集合
    private ArrayList<CommunityBean> CabinetEntityList = new ArrayList<CommunityBean>();

    //商品recyclerveiw的适配器
    private GoodsDragAdapter mAdapter;
    List<String> list=new ArrayList<>();



    public GoodsDao goodsDao;

    String store_id;

    Context mContext;

    int page=1;
    private int limit = 100;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_sort;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public GoodsSortViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(GoodsSortViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        RxToast.showTipToast(GoodsSortActivity.this,"修改成功！");


                        break;

                }
            }
        });

        //商品群组返回数据
        viewModel.groupList.observe(this, new Observer<ArrayList<GroupBean>>() {
            @Override
            public void onChanged(ArrayList<GroupBean> groupBeans) {
                CommunityEntityList.clear();
                CommunityEntityList.addAll(groupBeans);

//                viewModel.getGoodsGroup(store_id,page+"",limit+"");

                initRecyclerView();
            }
        });
        //商品返回数据
        viewModel.goodList.observe(this, new Observer<ArrayList<CommunityBean>>() {
            @Override
            public void onChanged(ArrayList<CommunityBean> goodBeans) {


                initRecyclerViewTow();
                goodsDao.deleteAllData();
                goodsDao.initTable(goodBeans);

                if (goodBeans.size() == limit) {
                    page++;
                    viewModel.getGoodsGroup(store_id, page + "", limit + "");
                    return;
                }
                //关闭对话框
                dismissDialog();
                initGoodsList(CommunityEntityList.get(0).getId() + "");



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initGoodsCategory();
    }

    @Override
    public void initData() {
        super.initData();
        mContext = this;
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色


        goodsDao = new GoodsDao(this);
        boolean dataExist = goodsDao.isDataExist();
        if (dataExist) {
            goodsDao.deleteAllData();
        }


        store_id= AppApplication.spUtils.getString("StoreId");


        //模拟数据
        initGoodsList("1");




        //刷新
        binding.scSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                page=1;
                //获取订单列表
//                viewModel.getGoodsGroup(store_id,page+"",limit+"");
                refreshLayout.finishRefresh(true);
            }
        });
        //加载
        binding.scSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                //获取订单列表
//                viewModel.getGoodsGroup(store_id,page+"",limit+"");
                refreshLayout.finishLoadMore(true);//加载完成
            }
        });

    }

    /**
     * 分类
     */
    private void initRecyclerView() {
        //一级分类
        //创建adapter
        mCommunityRecycleAdapter = new GoodsSetUpAdapter(this, CommunityEntityList);
        //给RecyclerView设置adapter
        binding.recLeft.setAdapter(mCommunityRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.recLeft.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.recLeft.getItemDecorationCount() == 0) {
            binding.recLeft.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        mCommunityRecycleAdapter.setOnScanningListener(new GoodsSetUpAdapter.OnScanningListener() {
            @Override
            public void onScanning(GroupBean groupBean) {

//                viewModel.getGoodsGroup(store_id,page+"",limit+"");
                CabinetEntityList.clear();
                CabinetEntityList.addAll(goodsDao.getGoodListByGroupId(groupBean.getId()));
                initRecyclerViewTow();
            }
        });
    }

    //    刷新商品列表
    private void initGoodsList(String goods_id) {

//        if (page==1){
//            CabinetEntityList.clear();
//        }
//
//        CabinetEntityList.addAll(goodsDao.getGoodListByGroupId(goods_id));

        list=getDemoData();

        initRecyclerViewTow();
    }
    /**
     * 商品列表
     */
    private void initRecyclerViewTow() {

        WidgetUtils.initRecyclerView( binding.recyclerView);

        mAdapter=new GoodsDragAdapter(this,list);

        // 监听拖拽和侧滑删除，更新UI和数据源。
        binding.recyclerView.setOnItemMoveListener(onItemMoveListener);
        // 监听Item的手指状态:拖拽、侧滑、松开。
        binding.recyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener);


        //给RecyclerView设置adapter
        binding.recyclerView.setAdapter(mAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //添加自定义的分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        binding.recyclerView.addItemDecoration(divider);
        //设置item的分割线
//        if (binding.recyclerView.getItemDecorationCount() == 0) {
//            binding.recyclerView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
//        }

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
                viewHolder.itemView.setBackgroundResource(R.color.color_hui_d4);
                KLog.d("状态：拖拽");
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {
                KLog.d("状态：滑动删除");
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundResource(R.color.main_touming);
                KLog.d("状态：手指松开");
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


    /**
     * 获取群组
     */
    private void initGoodsCategory() {
        viewModel.getGoodsGroup(store_id);
    }


    private void TipsToast(String content,CommunityBean date, int type){
        new  XPopup.Builder(GoodsSortActivity.this)
                .maxWidth((int) (widths * 0.8))
                .maxHeight((int) (height*0.6))
                .asConfirm("提示", content, "取消", "确认", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {

                        viewModel.updateStoreGoods(date.getType()+"",date.getGoods_sku(),date.getPackage_id()+"",date.getGoods_name(),date.getGoods_price(),date.getMarket_price(),type,date.getStock(),date.getStore_goods_id(),date.getStore_goods_sku());

                    }
                }, null,false)
                .show();
    }


    /**
     * 获得资源 dimens (dp)
     *
     * @param context
     * @param id      资源id
     * @return
     */
    public float getDimens(Context context, int id) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float px = context.getResources().getDimension(id);
        return px / dm.density;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
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

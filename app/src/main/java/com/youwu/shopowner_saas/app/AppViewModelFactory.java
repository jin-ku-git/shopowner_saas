package com.youwu.shopowner_saas.app;

import android.annotation.SuppressLint;
import android.app.Application;

import com.youwu.shopowner_saas.data.DemoRepository;
import com.youwu.shopowner_saas.ui.finance.AccountViewModel;
import com.youwu.shopowner_saas.ui.finance.BankCardViewModel;
import com.youwu.shopowner_saas.ui.finance.ChongZhiViewModel;
import com.youwu.shopowner_saas.ui.finance.MingXiBusinessViewModel;
import com.youwu.shopowner_saas.ui.finance.OperatingDataViewModel;
import com.youwu.shopowner_saas.ui.finance.TiXianViewModel;
import com.youwu.shopowner_saas.ui.finance.fragment.AccountFragmentViewModel;
import com.youwu.shopowner_saas.ui.finance.fragment.BusinessFragmentViewModel;
import com.youwu.shopowner_saas.ui.finance.fragment.CustomerFragmentViewModel;
import com.youwu.shopowner_saas.ui.finance.fragment.GoodsFragmentViewModel;
import com.youwu.shopowner_saas.ui.finance.fragment.MoveAboutFragmentViewModel;
import com.youwu.shopowner_saas.ui.finance.fragment.OverviewFragmentViewModel;
import com.youwu.shopowner_saas.ui.finance.fragment.TongJiFragmentViewModel;
import com.youwu.shopowner_saas.ui.finance.fragment.TradeFragmentViewModel;
import com.youwu.shopowner_saas.ui.fragment.DianPuViewModel;
import com.youwu.shopowner_saas.ui.fragment.FourViewModel;
import com.youwu.shopowner_saas.ui.fragment.MyViewModel;
import com.youwu.shopowner_saas.ui.fragment.OneViewModel;
import com.youwu.shopowner_saas.ui.fragment.ThreeViewModel;
import com.youwu.shopowner_saas.ui.fragment.TwoViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.BatchOperationViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.GoodsSetUpViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.GoodsSortViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.InventoryDetailsViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.InventoryViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.LossReportingDetailsViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.LossReportingViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.NewCommodityViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.RecordInventoryDetailsViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.ReturnGoodsDetailsViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.ReturnGoodsViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.SellOffDetailsViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.SellOffViewModel;
import com.youwu.shopowner_saas.ui.goods_operate.SetUpGoodsViewModel;
import com.youwu.shopowner_saas.ui.group.GroupManageViewModel;
import com.youwu.shopowner_saas.ui.group.GroupSortViewModel;
import com.youwu.shopowner_saas.ui.group.NewGroupViewModel;
import com.youwu.shopowner_saas.ui.login.LoginViewModel;
import com.youwu.shopowner_saas.ui.main.DemoViewModel;
import com.youwu.shopowner_saas.ui.main.MainViewModel;
import com.youwu.shopowner_saas.ui.main.sousuo.OrderSouSuoViewModel;
import com.youwu.shopowner_saas.ui.main.sousuo.SubscribeOrderViewModel;
import com.youwu.shopowner_saas.ui.network.NetWorkViewModel;
import com.youwu.shopowner_saas.ui.order_goods.ApplyReturnGoodsViewModel;
import com.youwu.shopowner_saas.ui.order_record.BookDetailsViewModel;
import com.youwu.shopowner_saas.ui.order_record.OrderGoodsListViewModel;
import com.youwu.shopowner_saas.ui.order_goods.ConfirmOrderViewModel;
import com.youwu.shopowner_saas.ui.order_goods.OrderDetailsViewModel;
import com.youwu.shopowner_saas.ui.order_goods.OrderReceivingViewModel;
import com.youwu.shopowner_saas.ui.order_goods.RefundOrderDetailsViewModel;
import com.youwu.shopowner_saas.ui.order_record.RecordViewModel;
import com.youwu.shopowner_saas.ui.order_record.RefundDetailsViewModel;
import com.youwu.shopowner_saas.ui.ping_jia.PingJiaViewModel;
import com.youwu.shopowner_saas.ui.report_form.SalesOverviewViewModel;
import com.youwu.shopowner_saas.ui.set_up.ModifyPasswordViewModel;
import com.youwu.shopowner_saas.ui.set_up.OrderSetUpViewModel;
import com.youwu.shopowner_saas.ui.set_up.SettingsViewModel;
import com.youwu.shopowner_saas.ui.set_up.StoreCodeViewModel;
import com.youwu.shopowner_saas.ui.set_up.StoreSetUpViewModel;
import com.youwu.shopowner_saas.ui.set_up.StoreStatusViewModel;
import com.youwu.shopowner_saas.ui.zaocan.DeliveryDetailsViewModel;
import com.youwu.shopowner_saas.ui.zaocan.GoodsTotalViewModel;
import com.youwu.shopowner_saas.ui.zaocan.PeriodTimeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by goldze on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final DemoRepository mRepository;

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application, Injection.provideDemoRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, DemoRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NetWorkViewModel.class)) {
            return (T) new NetWorkViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(DemoViewModel.class)) {//模板
            return (T) new DemoViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {//登录
            return (T) new LoginViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MainViewModel.class)) {//首页
            return (T) new MainViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OneViewModel.class)) {
            return (T) new OneViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(FourViewModel.class)) {
            return (T) new FourViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(TwoViewModel.class)) {
            return (T) new TwoViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ThreeViewModel.class)) {
            return (T) new ThreeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(StoreSetUpViewModel.class)) {//门店设置页面
            return (T) new StoreSetUpViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SettingsViewModel.class)) {//设置页面
            return (T) new SettingsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ConfirmOrderViewModel.class)) {//确认订货页面
            return (T) new ConfirmOrderViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderReceivingViewModel.class)) {//推送订单页面
            return (T) new OrderReceivingViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderDetailsViewModel.class)) {//订单详情
            return (T) new OrderDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RefundOrderDetailsViewModel.class)) {//退款订单
            return (T) new RefundOrderDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderGoodsListViewModel.class)) {//订货记录页面
            return (T) new OrderGoodsListViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SalesOverviewViewModel.class)) {//报表页面
            return (T) new SalesOverviewViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ModifyPasswordViewModel.class)) {//修改密码页面
            return (T) new ModifyPasswordViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(LossReportingViewModel.class)) {//报损页面
            return (T) new LossReportingViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(LossReportingDetailsViewModel.class)) {//报损确认页面
            return (T) new LossReportingDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(InventoryViewModel.class)) {//盘点页面
            return (T) new InventoryViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(InventoryDetailsViewModel.class)) {//盘点确认页面
            return (T) new InventoryDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SellOffViewModel.class)) {//沽清页面
            return (T) new SellOffViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SellOffDetailsViewModel.class)) {//提交沽清页面
            return (T) new SellOffDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ReturnGoodsViewModel.class)) {//退货页面
            return (T) new ReturnGoodsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ReturnGoodsDetailsViewModel.class)) {//退货确认页面
            return (T) new ReturnGoodsDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RecordViewModel.class)) {//盘点 记录页面
            return (T) new RecordViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(BookDetailsViewModel.class)) {//订货详情页面
            return (T) new BookDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RefundDetailsViewModel.class)) {//退货详情页面
            return (T) new RefundDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GoodsSetUpViewModel.class)) {//商品管理
            return (T) new GoodsSetUpViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SetUpGoodsViewModel.class)) {//修改商品
            return (T) new SetUpGoodsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RecordInventoryDetailsViewModel.class)) {//盘点记录详情1
            return (T) new RecordInventoryDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderSouSuoViewModel.class)) {//搜索订单
            return (T) new OrderSouSuoViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ApplyReturnGoodsViewModel.class)) {//申请退货
            return (T) new ApplyReturnGoodsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SubscribeOrderViewModel.class)) {//预约订单
            return (T) new SubscribeOrderViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(DianPuViewModel.class)) {//店铺
            return (T) new DianPuViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MyViewModel.class)) {//我的
            return (T) new MyViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GroupManageViewModel.class)) {//群组管理
            return (T) new GroupManageViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(AccountViewModel.class)) {//财务对账
            return (T) new AccountViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(AccountFragmentViewModel.class)) {//对账
            return (T) new AccountFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(TongJiFragmentViewModel.class)) {//统计
            return (T) new TongJiFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(TiXianViewModel.class)) {//提现
            return (T) new TiXianViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ChongZhiViewModel.class)) {//充值
            return (T) new ChongZhiViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(BankCardViewModel.class)) {//我的账户
            return (T) new BankCardViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GroupSortViewModel.class)) {//群组排序
            return (T) new GroupSortViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(NewGroupViewModel.class)) {//新建/编辑群组
            return (T) new NewGroupViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(BatchOperationViewModel.class)) {//批量操作
            return (T) new BatchOperationViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(NewCommodityViewModel.class)) {//新建商品
            return (T) new NewCommodityViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GoodsSortViewModel.class)) {//商品排序
            return (T) new GoodsSortViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(PingJiaViewModel.class)) {//顾客评价
            return (T) new PingJiaViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OperatingDataViewModel.class)) {//经营数据
            return (T) new OperatingDataViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OverviewFragmentViewModel.class)) {//总览页面
            return (T) new OverviewFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(TradeFragmentViewModel.class)) {//行业页面
            return (T) new TradeFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(BusinessFragmentViewModel.class)) {//营业页面
            return (T) new BusinessFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MingXiBusinessViewModel.class)) {//营业明细页面
            return (T) new MingXiBusinessViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(CustomerFragmentViewModel.class)) {//顾客页面
            return (T) new CustomerFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GoodsFragmentViewModel.class)) {//商品页面
            return (T) new GoodsFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(StoreCodeViewModel.class)) {//门店收款码页面
            return (T) new StoreCodeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderSetUpViewModel.class)) {//订单设置页面
            return (T) new OrderSetUpViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(StoreStatusViewModel.class)) {//门店状态页面
            return (T) new StoreStatusViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MoveAboutFragmentViewModel.class)) {//活动页面
            return (T) new MoveAboutFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(DeliveryDetailsViewModel.class)) {//早餐/晚餐配送明细页面
            return (T) new DeliveryDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(PeriodTimeViewModel.class)) {//早餐/晚餐时段制作明细页面
            return (T) new PeriodTimeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GoodsTotalViewModel.class)) {//早餐/晚餐商品总单页面
            return (T) new GoodsTotalViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

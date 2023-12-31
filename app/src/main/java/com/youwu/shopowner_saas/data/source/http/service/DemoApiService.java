package com.youwu.shopowner_saas.data.source.http.service;

import com.youwu.shopowner_saas.bean.UpDateBean;
import com.youwu.shopowner_saas.entity.DemoEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by goldze on 2017/6/15.
 */

public interface DemoApiService {
    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<DemoEntity>> demoGet();

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResponse<DemoEntity>> demoPost(@Field("catalog") String catalog);

    /**
     * 检查更新
     * @return
     */
    @GET("dzd_app_version")
    Observable<BaseBean<UpDateBean>> GET_APP_VERSION();

    /**
     * 登录
     *
     * @param tel      账号
     * @param password 密码
     * @param type 1、收银员    2、加盟商
     * @return
     */
    @FormUrlEncoded
    @POST("auth/login")
    Observable<BaseBean<Object>> LOGIN(@Field("tel") String tel, @Field("password") String password, @Field("type") int type);

    /**
     * 获取个人信息
     *
     * @return
     */

    @POST("auth/me")
    Observable<BaseBean<Object>> GET_ME();

    /**
     * 待处理小程序订单数量
     *
     * @return
     */

    @POST("order/xcx_order_count")
    Observable<BaseBean<Object>> XCX_ORDER_COUNT();

    /**
     * 小程序订单
     *
     * @param type 1.待接单2.待出餐3.带退款
     * @return
     */
    @FormUrlEncoded
    @POST("order/xcx_order_list")
    Observable<BaseBean<Object>> XCX_ORDER_LIST(@Field("type") String type);

    /**
     * 接单、拒单、出餐
     *
     * @param order_sn 订单编号
     * @param status   2接单3.拒单4.出餐'
     * @return
     */
    @FormUrlEncoded
    @POST("order/edit_order_status")
    Observable<BaseBean<Object>> DEIT_ORDER_STATUS(@Field("order_sn") String order_sn, @Field("status") String status);

    /**
     * 小程序退款订单审核
     * @param type
     * @param order_sn
     * @param refund_reason
     * @param modify_stock
     * @return
     */
    @FormUrlEncoded
    @POST("order/audit_order_refund")
    Observable<BaseBean<Object>> AUDIT_ORDER_REFUND(@Field("type") int type,@Field("order_sn") String order_sn,@Field("refund_reason") String refund_reason,@Field("modify_stock") String modify_stock );

    /**
     * 获取订货分类
     *
     * @return
     */

    @POST("goods/goods_category")
    Observable<BaseBean<Object>> GOODS_CATEGORY();

    /**
     * 获取订货商品列表
     *
     * @param store_id 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("cargo/goods_list")
    Observable<BaseBean<Object>> GOODS_LIST(@Field("store_id") String store_id,@Field("category_id") String id);

    /**
     * 获取盘点商品列表
     *
     * @param store_id 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("goods/stock_goods_list")
    Observable<BaseBean<Object>> GET_STOCK_GOODS_LIST(@Field("store_id") String store_id,@Field("category_id") String category_id,@Field("page") String page
            ,@Field("limit") String limit,@Field("type") String type);

    /**
     * 获取退货商品列表
     *
     * @param store_id 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("return_cargo/cargo_refund_goods_list")
    Observable<BaseBean<Object>> CARGO_REFUND_GOODS_LIST(@Field("store_id") String store_id,@Field("category_id") String id);



    /**
     * 获取报损商品列表
     *
     * @param store_id 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("loss_report/goods_list")
    Observable<BaseBean<Object>> LOSS_GOODS_LIST(@Field("store_id") String store_id,@Field("category_id") String id);

    /**
     * 更新门店自动接单
     *
     * @param is_order       是都自动接单 1自动接单 2 手动接单
     * @param start         开始时间
     * @param end           结束时间
     * @return
     */
    @FormUrlEncoded
    @POST("update_store")
    Observable<BaseBean<Object>> UPDATE_STORE(@Field("is_order") String is_order, @Field("start") String start, @Field("end") String end);

    /**
     * 申请订货
     *
     * @param store_id 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("cargo/add_order")
    Observable<BaseBean<Object>> ADD_ORDER(@Field("store_id") String store_id,@Field("arrival_time") String arrival_time,@Field("goods_list") String goods_list);

    /**
     * 申请退货
     *
     * @param store_id 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("return_cargo/cargo_refund")
    Observable<BaseBean<Object>> CARGO_REFUND(@Field("store_id") String store_id,@Field("mark") String mark,@Field("goods_list") String goods_list);

    /**
     * 在售商品数量
     *
     * @param store_id 店铺id
     * @return
     */
    @FormUrlEncoded
    @POST("goods/goods_count")
    Observable<BaseBean<Object>> GOODS_COUNT(@Field("store_id") String store_id);

    /**
     * 获取订货列表
     * @param store_id 店铺id
     * @return
     */
    @FormUrlEncoded
    @POST("cargo/order_list")
    Observable<BaseBean<Object>> ORDER_LIST(@Field("store_id") String store_id,@Field("start") String start);

    /**
     * 获取退货列表
     * @param store_id 店铺id
     * @return
     */
    @FormUrlEncoded
    @POST("return_cargo/cargo_refund_list")
    Observable<BaseBean<Object>> REFUND_LIST(@Field("store_id") String store_id,@Field("page") String page,@Field("limit") String limit
            ,@Field("start") String start);

    /**
     * 订单列表
     *
     * @param start           开始时间
     * @param end             结束时间
     * @param page            页数
     * @param delivery_method 配送方式
     * @param tel             手机号
     * @param store_id             店铺id
     * @return
     */
    @FormUrlEncoded
    @POST("order/store_terminal_order_list")
    Observable<BaseBean<Object>> ORDER_List(@Field("start") String start, @Field("end") String end, @Field("page") int page, @Field("limit") String limit, @Field("delivery_method") String delivery_method, @Field("tel") String tel, @Field("store_id") String store_id, @Field("order_taking_status") String order_taking_status, @Field("order_sn") String order_sn);


    /**
     * 获取销售概况
     *
     * @param type 1.今日 2.本周 3.本月 4.本季度
     * @return
     */
    @FormUrlEncoded
    @POST("sales_situation")
    Observable<BaseBean<Object>> SALES_SITUATION(@Field("type") String type,@Field("store_id") String store_id);

    /**
     * 获取商品销量排行
     *
     * @param type 1.今日 2.本周 3.本月 4.本季度
     * @return
     */
    @FormUrlEncoded
    @POST("goods_sale")
    Observable<BaseBean<Object>> GOODS_SALE(@Field("type") String type,@Field("store_id") String store_id);

    /**
     * 获取套餐销量排行
     *
     * @param type 1.今日 2.本周 3.本月 4.本季度
     * @return
     */
    @FormUrlEncoded
    @POST("package_sale")
    Observable<BaseBean<Object>> PACKAGE_SALE(@Field("type") String type,@Field("store_id") String store_id);

    /**
     * 核销
     *
     * @param order_sn 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("order/close_order")
    Observable<BaseBean<Object>> CLOSE_ORDER(@Field("order_sn") String order_sn);

    /**
     * 订单详情
     *
     * @param order_sn 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("order/order_details")
    Observable<BaseBean<Object>> ORDER_DETAILS(@Field("order_sn") String order_sn);

    /**
     * 退款订单详情
     *
     * @param order_sn 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("order/refund_details")
    Observable<BaseBean<Object>> REFUND_DETAILS(@Field("order_sn") String order_sn);

    /**
     * 退货详情
     *
     * @param order_sn 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("return_cargo/cargo_refund_details")
    Observable<BaseBean<Object>> CARGO_REFUND_DETAILS(@Field("order_sn") String order_sn);

    /**
     * 报损原因
     *
     * @return
     */

    @POST("loss_report/reason")
    Observable<BaseBean<Object>> REASON();

    /**
     * 报损
     *
     * @return
     */
    @FormUrlEncoded
    @POST("loss_report/add")
    Observable<BaseBean<Object>> ADD_LOSS_REPORT(@Field("store_id") String store_id,@Field("mark") String mark,@Field("goods_list") String goods_list);

    /**
     * 盘点
     *
     * @return
     */
    @FormUrlEncoded
    @POST("stock/sorting_inventory")
    Observable<BaseBean<Object>> SORTING_INVENTORY(@Field("goods_list") String goods_list,@Field("type") String type,@Field("mark") String mark);

    /**
     * 报损列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("loss_report/list")
    Observable<BaseBean<Object>> LOSS_REPORT_LIST(@Field("page") String page,@Field("limit") String limit,@Field("store_id") String store_id);

    /**
     * 盘点列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("stock/sorting_inventory_list")
    Observable<BaseBean<Object>> INVENTORY_REPORT_LIST(@Field("page") String page,@Field("limit") String limit,@Field("store_id") String store_id);

    /**
     * 沽清列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("stock/out_stock_list")
    Observable<BaseBean<Object>> SELL_OFF_REPORT_LIST(@Field("page") String page,@Field("limit") String limit,@Field("store_id") String store_id);

    /**
     * 门店设置
     *
     * @return
     */
    @FormUrlEncoded
    @POST("update_store_terminal")
    Observable<BaseBean<Object>> UPDATE_STORE_TERMINAL(@Field("is_order") String is_order,@Field("start") String start,@Field("end") String end
            ,@Field("delivery_method") String delivery_method,@Field("is_link") String is_link,@Field("key") String key,@Field("sn") String sn);

    /**
     * 打印
     *
     * @return
     */
    @FormUrlEncoded
    @POST("print")
    Observable<BaseBean<Object>> PRINT(@Field("order_sn") String order_sn,@Field("store_id") String store_id);

    /**
     * 沽清
     *
     * @param goods_list 商品列表
     * @param mark       备注
     * @return
     */
    @FormUrlEncoded
    @POST("stock/out_stock")
    Observable<BaseBean<Object>> OUT_STOCK(@Field("goods_list") String goods_list,@Field("mark") String mark);

    /**
     * 沽清
     *
     * @param order_list 商品列表
     * @return
     */
    @FormUrlEncoded
    @POST("cargo/receive")
    Observable<BaseBean<Object>> RECEIVING(@Field("order_list") String order_list);
    /**
     * 打印测试
     *
     * @return
     */

    @POST("print_test")
    Observable<BaseBean<Object>> PRINT_TEST();
    /**
     * 门店列表设置
     *
     * @return
     */

    @POST("setting_list")
    Observable<BaseBean<Object>> SETTING_LIST();

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("update_password")
    Observable<BaseBean<Object>> UPDATE_PASSWORD(@Field("old_password") String old_password,@Field("new_password") String new_password,@Field("confirm_password") String confirm_password);
    /**
     * 获取群组
     *
     * @param store_id 门店id
     * @return
     */
    @FormUrlEncoded
    @POST("goods/goods_group")
    Observable<BaseBean<Object>> GOODS_GROUP(@Field("store_id") String store_id);

    /**
     * 获取商品
     *
     * @param store_id 门店id
     * @return
     */
    @FormUrlEncoded
    @POST("goods_manage/goods_list")
    Observable<BaseBean<Object>> GOODS_List(@Field("store_id") String store_id, @Field("page") String page, @Field("limit") String limit);

    /**
     * 更新商品信息
     * @param type              商品类型 1商品 2套餐
     * @param goods_sku         商品名称
     * @param package_id        商品sku
     * @param goods_name        套餐id
     * @param goods_price       商品售价
     * @param market_price      商品市场价
     * @param status            商品状态 1上架 2下架
     * @param stock             商品库存
     * @return
     */
    @FormUrlEncoded
    @POST("goods_manage/update_store_goods")
    Observable<BaseBean<Object>> UPDATE_STORE_GOODS(@Field("type") String type, @Field("goods_sku") String goods_sku, @Field("package_id") String package_id,@Field("goods_name") String goods_name,@Field("goods_price") String goods_price
            ,@Field("market_price") String market_price,@Field("status") String status,@Field("stock") String stock,@Field("store_goods_id") String store_goods_id,@Field("store_goods_sku") String store_goods_sku);

    /**
     *  订单列表
     *
     * @param appointment_time                  预约配送日期
     * @param type                              订单类型 1全部 2已取消
     * @param order_taking_status               门店接单状态 1待接单 2待出餐
     * @param order_sn                          订单编号
     * @param goods_name                        商品名称
     * @param member_phone                      用户手机号
     * @param member_address                    用户地址
     * @return
     */
    @FormUrlEncoded
    @POST("order/new_order_list")
    Observable<BaseBean<Object>> NEW_ORDER_LIST(@Field("appointment_time") String appointment_time, @Field("type") String type, @Field("order_taking_status") String order_taking_status, @Field("order_sn") String order_sn,
                                                @Field("goods_name") String goods_name,@Field("member_phone") String member_phone,@Field("member_address") String member_address,@Field("page") String page,@Field("limit") String limit);
    /**
     *  退款订单列表
     * @param refund_type                       1待退款 2已退款
     * @param order_sn                          订单编号
     * @param goods_name                        商品名称
     * @param member_phone                      用户手机号
     * @param member_address                    用户地址
     * @return
     */
    @FormUrlEncoded
    @POST("order/new_refund_order_list")
    Observable<BaseBean<Object>> NEW_REFUND_ORDER_LIST(@Field("refund_type") String refund_type,@Field("order_sn") String order_sn,
                                                @Field("goods_name") String goods_name,@Field("member_phone") String member_phone,@Field("member_address") String member_address,
                                                       @Field("page") String page,@Field("limit") String limit);

    /**
     * 修改订单状态
     *
     * @param status    2.接单 3.拒单 4.出餐
     * @param order_sn    订单号
     * @return
     */
    @FormUrlEncoded
    @POST("order/new_update_order")
    Observable<BaseBean<Object>> NEW_UPDATE_ORDER(@Field("status") String status, @Field("order_sn") String order_sn);
    /**
     * 审核退款订单
     *
     * @param audit_status  //审核退款 1同意 2拒绝
     * @param refund_sn    订单号
     * @param remark        备注
     * @return
     */
    @FormUrlEncoded
    @POST("order/new_audit_refund")
    Observable<BaseBean<Object>> NEW_AUDIT_REFUND(@Field("audit_status") String audit_status, @Field("refund_sn") String refund_sn, @Field("remark") String remark);
    /**
     * 更新门店信息
     *
     * @param is_order   是否自动接单 1是 2否
     * @return
     */
    @FormUrlEncoded
    @POST("setting/new_update_store")
    Observable<BaseBean<Object>> NEW_UPDATE_STORE(@Field("is_order") String is_order);
    /**
     * 获取门店信息
     *
     * @return
     */

    @POST("setting/new_store_info")
    Observable<BaseBean<Object>> NEW_STORE_INFO();

    /**
     * 早餐/食材时段配送明细
     *
     * @param channel_id                1早餐/2食材
     * @param store_id                  门店id
     * @param appointment_time          时间戳（配送时间）
     * @param community_id              社区id
     * @return
     */
    @FormUrlEncoded
    @POST("report/get_time_order_info")
    Observable<BaseBean<Object>> GET_TIME_ORDER_INFO(@Field("channel_id") String channel_id,@Field("store_id") String store_id,@Field("appointment_time") String appointment_time,@Field("community_id") String community_id);

    /**
     * 早餐/食材时段备料明细
     *
     * @param channel_id                1早餐/2食材
     * @param store_id                  门店id
     * @param appointment_time          时间戳（配送时间）
     * @return
     */
    @FormUrlEncoded
    @POST("report/get_time_goods_num")
    Observable<BaseBean<Object>> GET_TIME_GOODS_NUM(@Field("channel_id") String channel_id,@Field("store_id") String store_id,@Field("appointment_time") String appointment_time);
    /**
     * 早餐/食材时段制作明细
     *
     * @param channel_id                1早餐/2食材
     * @param store_id                  门店id
     * @param appointment_time          时间戳（配送时间）
     * @return
     */
    @FormUrlEncoded
    @POST("report/get_time_goods_info")
    Observable<BaseBean<Object>> GET_TIME_GOODS_INFO(@Field("channel_id") String channel_id,@Field("store_id") String store_id,@Field("appointment_time") String appointment_time);
    /**
     * 早餐/食材商品总单
     *
     * @param channel_id                1早餐/2食材
     * @param store_id                  门店id
     * @param appointment_time          时间戳（配送时间）
     * @return
     */
    @FormUrlEncoded
    @POST("report/get_goods_num")
    Observable<BaseBean<Object>> GET_GOODS_NUM(@Field("channel_id") String channel_id,@Field("store_id") String store_id,@Field("appointment_time") String appointment_time);
    /**
     * 获取社区
     *
     * @param store_id          门店id
     * @param name              社区名称（可为空）
     * @return
     */
    @FormUrlEncoded
    @POST("setting/get_community")
    Observable<BaseBean<Object>> GET_COMMUNITY(@Field("store_id") String store_id,@Field("name") String name);
    /**
     * 获取已取消订单数量
     *
     * @param appointment_time          时间
     * @return
     */
    @FormUrlEncoded
    @POST("order/new_get_order_num")
    Observable<BaseBean<Object>> GET_ORDER_NUM(@Field("appointment_time") String appointment_time);


}

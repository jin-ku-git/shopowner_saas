package com.youwu.shopowner_saas.ui.fragment.bean;

import java.io.Serializable;
import java.util.List;

public class OrderBean implements Serializable {


    public int type;//展开还是收起 0、关闭 1、打开


    /**
     * id : 2862.0
     * status_name : 已出餐
     * dining_code : B021
     * appointment_time : 2023-02-28
     * appointment_hour : 09:27-09:57
     * order_status : 1.0
     * order_taking_status : 4.0
     * pickup_status : 0.0
     * member_name : 餘生
     * member_phone : 13468214148
     * juli : 0.01
     * pickup_address : 先每集团-南门 201
     * mark : 厕所
     * goods_kind_num : 3.0
     * goods_num : 3.0
     * pay_channel : 余额
     * pay_amount : 28.5
     * item_amount : 26.5
     * packing_fee : 0.0
     * shipping_fee : 2.0
     * reduced_amount : 0.0
     * created_at : 2023-02-28 09:27:53
     * order_sn : 100101010199981677547673
     * order_details : [{"goods_name":"鸡蛋汉堡","goods_price":13.5,"goods_quantity":1},{"goods_name":"鲜肉小笼包","goods_price":7,"goods_quantity":1},{"goods_name":"鸡排鸡蛋手抓饼","goods_price":6,"goods_quantity":1}]
     */

    private double id;
    private String channel_name;
    private String status_name;
    private String dining_code;
    private String appointment_time;
    private String appointment_hour;
    private int order_status;          //支付状态   0、待支付 1、已支付 2、已取消 3、已退款
    private int order_taking_status;  //门店接单状态:1.待接单2,接单-待出餐3.拒单4.出餐
    private int pickup_status;       //取餐状态 0、待配送 1、已配送 2、已签收 3、已评价
    private int delivery_method_id;       //配送方式 3、自提 4、堂食 5、外卖配送 6、配送到柜

    private String member_name;
    private String member_phone;
    private String juli;
    private String pickup_address;
    private String mark;
    private double goods_kind_num;
    private double goods_num;
    private String pay_channel;
    private double pay_amount;
    private double item_amount;
    private double packing_fee;
    private double shipping_fee;
    private double reduced_amount;
    private String created_at;
    private String order_sn;
    private int tableware_number;//餐具数量
    private List<OrderDetailsBean> order_details;
    private List<OrderRefundBean> order_refund;

    public int getTableware_number() {
        return tableware_number;
    }

    public void setTableware_number(int tableware_number) {
        this.tableware_number = tableware_number;
    }

    public List<OrderRefundBean> getOrder_refund() {
        return order_refund;
    }

    public void setOrder_refund(List<OrderRefundBean> order_refund) {
        this.order_refund = order_refund;
    }

    public static class OrderDetailsBean implements Serializable {
        /**
         * goods_name : 鸡蛋汉堡
         * goods_price : 13.5
         * goods_quantity : 1.0
         */

        private String goods_name;
        private double goods_price;//商品加个
        private double goods_pay_price;//实付
        private int goods_quantity;

        public double getGoods_pay_price() {
            return goods_pay_price;
        }

        public void setGoods_pay_price(double goods_pay_price) {
            this.goods_pay_price = goods_pay_price;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public int getGoods_quantity() {
            return goods_quantity;
        }

        public void setGoods_quantity(int goods_quantity) {
            this.goods_quantity = goods_quantity;
        }
    }


    public static class OrderRefundBean implements Serializable {

        /**
         * order_sn : 100101010199921677053983
         * refund_sn : 16771420206900
         * status : 1
         * type : 2
         * type_name : 全部退款
         * total_price : 11.5
         * mark :
         * image : []
         * refund_goods : [{"goods_name":"鸡蛋汉堡","goods_number":1,"goods_price":9.5}]
         */

        private String order_sn;
        private String refund_sn;
        private int status;
        private int type;
        private String type_name;
        private double total_price;
        private String mark;//退款理由-用户填写
        private String refund_reason;//退款理由-选择
        private String remark;//拒绝理由
        private String created_at;
        private List<String> image;
        private List<RefundGoodsBean> refund_goods;

        public String getRefund_reason() {
            return refund_reason;
        }

        public void setRefund_reason(String refund_reason) {
            this.refund_reason = refund_reason;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getRefund_sn() {
            return refund_sn;
        }

        public void setRefund_sn(String refund_sn) {
            this.refund_sn = refund_sn;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }

        public List<RefundGoodsBean> getRefund_goods() {
            return refund_goods;
        }

        public void setRefund_goods(List<RefundGoodsBean> refund_goods) {
            this.refund_goods = refund_goods;
        }

        public static class RefundGoodsBean implements Serializable {
            /**
             * goods_name : 鸡蛋汉堡
             * goods_number : 1
             * goods_price : 9.5
             */

            private String goods_name;
            private int goods_number;
            private double goods_price;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(int goods_number) {
                this.goods_number = goods_number;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }
        }
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getDining_code() {
        return dining_code;
    }

    public void setDining_code(String dining_code) {
        this.dining_code = dining_code;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getAppointment_hour() {
        return appointment_hour;
    }

    public void setAppointment_hour(String appointment_hour) {
        this.appointment_hour = appointment_hour;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getOrder_taking_status() {
        return order_taking_status;
    }

    public void setOrder_taking_status(int order_taking_status) {
        this.order_taking_status = order_taking_status;
    }

    public int getPickup_status() {
        return pickup_status;
    }

    public void setPickup_status(int pickup_status) {
        this.pickup_status = pickup_status;
    }

    public int getDelivery_method_id() {
        return delivery_method_id;
    }

    public void setDelivery_method_id(int delivery_method_id) {
        this.delivery_method_id = delivery_method_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public double getGoods_kind_num() {
        return goods_kind_num;
    }

    public void setGoods_kind_num(double goods_kind_num) {
        this.goods_kind_num = goods_kind_num;
    }

    public double getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(double goods_num) {
        this.goods_num = goods_num;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(double pay_amount) {
        this.pay_amount = pay_amount;
    }

    public double getItem_amount() {
        return item_amount;
    }

    public void setItem_amount(double item_amount) {
        this.item_amount = item_amount;
    }

    public double getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(double packing_fee) {
        this.packing_fee = packing_fee;
    }

    public double getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(double shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public double getReduced_amount() {
        return reduced_amount;
    }

    public void setReduced_amount(double reduced_amount) {
        this.reduced_amount = reduced_amount;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public List<OrderDetailsBean> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(List<OrderDetailsBean> order_details) {
        this.order_details = order_details;
    }
}

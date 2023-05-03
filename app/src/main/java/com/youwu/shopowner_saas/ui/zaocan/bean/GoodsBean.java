package com.youwu.shopowner_saas.ui.zaocan.bean;

import java.io.Serializable;
import java.util.List;


public class GoodsBean implements Serializable {

    /**
     * time : 06:00-07:00
     * orderCode : A061
     * com : 沂河郡府
     * address : 沂河郡府31号楼3单元401
     * table : C门
     * goods_list : [{"goods_name":"商品名","goods_num":"1"},{"goods_name":"商品名","goods_num":"1"}]
     */

    private String time;
    private String orderCode;
    private String com;
    private String address;
    private String table;
    private List<GoodsListBean> goods_list;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean implements Serializable {
        /**
         * goods_name : 商品名
         * goods_num : 1
         */

        private String goods_name;
        private String goods_num;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }
    }
}

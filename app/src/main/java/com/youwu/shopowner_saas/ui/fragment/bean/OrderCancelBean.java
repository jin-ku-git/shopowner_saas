package com.youwu.shopowner_saas.ui.fragment.bean;

import java.io.Serializable;

public class OrderCancelBean implements Serializable {


    private int all_order;//全部数量
    private int pay_order;//支付数量
    private int cancel_order;//取消数量

    public int getAll_order() {
        return all_order;
    }

    public void setAll_order(int all_order) {
        this.all_order = all_order;
    }

    public int getPay_order() {
        return pay_order;
    }

    public void setPay_order(int pay_order) {
        this.pay_order = pay_order;
    }

    public int getCancel_order() {
        return cancel_order;
    }

    public void setCancel_order(int cancel_order) {
        this.cancel_order = cancel_order;
    }
}

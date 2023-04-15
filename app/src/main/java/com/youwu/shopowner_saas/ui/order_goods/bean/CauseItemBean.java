package com.youwu.shopowner_saas.ui.order_goods.bean;


import java.io.Serializable;

public class CauseItemBean implements Serializable {



    private String cause_id;

    private String cause_name;
    private boolean cause_status;

    public String getCause_id() {
        return cause_id;
    }

    public void setCause_id(String cause_id) {
        this.cause_id = cause_id;
    }

    public String getCause_name() {
        return cause_name;
    }

    public void setCause_name(String cause_name) {
        this.cause_name = cause_name;
    }

    public boolean isCause_status() {
        return cause_status;
    }

    public void setCause_status(boolean cause_status) {
        this.cause_status = cause_status;
    }
}

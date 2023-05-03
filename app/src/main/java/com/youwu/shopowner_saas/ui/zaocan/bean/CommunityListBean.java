package com.youwu.shopowner_saas.ui.zaocan.bean;

import java.io.Serializable;

public class CommunityListBean implements Serializable {

    /**
     * id : 3
     * name : 先每集团
     * address : 临沂市凤凰大街
     * longitude : 118.420079
     * latitude : 35.070781
     * created_at : 2022-03-07 20:35:39
     * updated_at : 2022-03-07 20:35:39
     * sort : 1
     * company_id : 1
     * type : 1
     * status : 1
     */

    private int id;
    private String name;
    private String address;
    private String longitude;
    private String latitude;
    private String created_at;
    private String updated_at;
    private int sort;
    private int company_id;
    private int type;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

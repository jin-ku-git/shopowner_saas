package com.youwu.shopowner_saas.ui.zaocan.bean;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import java.util.List;

/**
 * Created by huang on 2017/11/1.
 */
@SmartTable(name="合并信息列表")
public class MergeInfo {
    @SmartColumn(id =1,name = "预约取餐时间",autoMerge = true,width = 100)
    private String time;
    @SmartColumn(id=2,name="收货地址",autoMerge = true)
    private String com;
    @SmartColumn(id =3,name="地址",autoMerge = true)
    private String address;

    @SmartColumn(id =4,name="格子",autoMerge = true)
    private String table;
    @SmartColumn(id =5,name="取餐号")
    private String orderCode;
    @SmartColumn(type= ColumnType.Child)
    private ChildData childData;
    private String url;
    private List<GoodsInfo>  goodsList;

    private String community_id;
    private String community_name;
    private String cabinet_id;
    private String cabinet_name;
    private String lattice;
    private String number;

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getCabinet_id() {
        return cabinet_id;
    }

    public void setCabinet_id(String cabinet_id) {
        this.cabinet_id = cabinet_id;
    }

    public String getCabinet_name() {
        return cabinet_name;
    }

    public void setCabinet_name(String cabinet_name) {
        this.cabinet_name = cabinet_name;
    }

    public String getLattice() {
        return lattice;
    }

    public void setLattice(String lattice) {
        this.lattice = lattice;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ChildData getChildData() {
        return childData;
    }

    public void setChildData(ChildData childData) {
        this.childData = childData;
    }

    public List<GoodsInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsInfo> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsInfo {
        String name;
        int number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    public MergeInfo(String time, String com, String address, String table, String orderCode, ChildData childData) {
        this.time = time;
        this.com = com;
        this.address = address;
        this.table = table;
        this.orderCode = orderCode;
        this.childData = childData;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

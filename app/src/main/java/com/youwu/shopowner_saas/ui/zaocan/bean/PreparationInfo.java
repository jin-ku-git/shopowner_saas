package com.youwu.shopowner_saas.ui.zaocan.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * Created by huang on 2017/11/1.
 */
@SmartTable(name="合并信息列表",count = true)
public class PreparationInfo {
    @SmartColumn(id =1,name = "备料时段",autoMerge = true,width = 100)
    private String time;
    @SmartColumn(id =2,name = "商品名称")
    private String GoodsName;
    @SmartColumn(id =3,name = "商品数量",autoCount = true)
    private int number;

    public PreparationInfo(String time, String GoodsName, int number) {
        this.time = time;
        this.GoodsName = GoodsName;
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

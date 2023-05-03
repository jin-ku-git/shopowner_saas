package com.youwu.shopowner_saas.ui.zaocan.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * Created by huang on 2017/11/1.
 */
@SmartTable(name="合并信息列表",count = true)
public class GoodsTotalInfo {

    @SmartColumn(id =1,name = "商品名称")
    private String GoodsName;
    @SmartColumn(id =2,name = "商品数量",autoCount = true)
    private int number;

    public GoodsTotalInfo( String GoodsName, int number) {

        this.GoodsName = GoodsName;
        this.number = number;
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

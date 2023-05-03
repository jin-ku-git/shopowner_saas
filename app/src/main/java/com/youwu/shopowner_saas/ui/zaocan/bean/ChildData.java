package com.youwu.shopowner_saas.ui.zaocan.bean;


import android.graphics.Paint;

import com.bin.david.form.annotation.SmartColumn;

/**
 * Created by huang on 2017/11/1.
 */

public class ChildData {

    @SmartColumn(id =6,name = "商品名称/数量",align=Paint.Align.LEFT)
    private String name;
//    @SmartColumn(id =7,name = "求和项：商品数量")
//    private String number;

    public ChildData(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

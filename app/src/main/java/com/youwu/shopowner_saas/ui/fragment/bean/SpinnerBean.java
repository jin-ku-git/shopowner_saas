package com.youwu.shopowner_saas.ui.fragment.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class SpinnerBean  {


        private int id;
        private String name;
        private boolean Select;

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

    public boolean isSelect() {
        return Select;
    }

    public void setSelect(boolean select) {
        Select = select;
    }
}

package com.youwu.shopowner_saas.ui.zaocan.bean;

import java.io.Serializable;
import java.util.List;


public class FormBean implements Serializable {


    /**
     * time : 06:00-07:00
     * VillageList : [{"name":"沂河观邸","unitList":[{"name":"沂河观邸16号楼西侧","lattice_list":[{"lattice":"A门","numberList":[{"number":"A06","goodsList":[{"name":"八宝粥","goodsNumber":"2"}]}]}]}]}]
     */

    private String time;
    private List<VillageListBean> VillageList;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<VillageListBean> getVillageList() {
        return VillageList;
    }

    public void setVillageList(List<VillageListBean> villageList) {
        VillageList = villageList;
    }

    public static class VillageListBean implements Serializable {
        /**
         * name : 沂河观邸
         * unitList : [{"name":"沂河观邸16号楼西侧","lattice_list":[{"lattice":"A门","numberList":[{"number":"A06","goodsList":[{"name":"八宝粥","goodsNumber":"2"}]}]}]}]
         */

        private String name;
        private List<UnitListBean> unitList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<UnitListBean> getUnitList() {
            return unitList;
        }

        public void setUnitList(List<UnitListBean> unitList) {
            this.unitList = unitList;
        }

        public static class UnitListBean implements Serializable {
            /**
             * name : 沂河观邸16号楼西侧
             * lattice_list : [{"lattice":"A门","numberList":[{"number":"A06","goodsList":[{"name":"八宝粥","goodsNumber":"2"}]}]}]
             */

            private String name;
            private List<LatticeListBean> lattice_list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<LatticeListBean> getLattice_list() {
                return lattice_list;
            }

            public void setLattice_list(List<LatticeListBean> lattice_list) {
                this.lattice_list = lattice_list;
            }

            public static class LatticeListBean implements Serializable {
                /**
                 * lattice : A门
                 * numberList : [{"number":"A06","goodsList":[{"name":"八宝粥","goodsNumber":"2"}]}]
                 */

                private String lattice;
                private List<NumberListBean> numberList;

                public String getLattice() {
                    return lattice;
                }

                public void setLattice(String lattice) {
                    this.lattice = lattice;
                }

                public List<NumberListBean> getNumberList() {
                    return numberList;
                }

                public void setNumberList(List<NumberListBean> numberList) {
                    this.numberList = numberList;
                }

                public static class NumberListBean implements Serializable {
                    /**
                     * number : A06
                     * goodsList : [{"name":"八宝粥","goodsNumber":"2"}]
                     */

                    private String number;
                    private List<GoodsListBean> goodsList;

                    public String getNumber() {
                        return number;
                    }

                    public void setNumber(String number) {
                        this.number = number;
                    }

                    public List<GoodsListBean> getGoodsList() {
                        return goodsList;
                    }

                    public void setGoodsList(List<GoodsListBean> goodsList) {
                        this.goodsList = goodsList;
                    }

                    public static class GoodsListBean implements Serializable {
                        /**
                         * name : 八宝粥
                         * goodsNumber : 2
                         */

                        private String name;
                        private String goodsNumber;

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getGoodsNumber() {
                            return goodsNumber;
                        }

                        public void setGoodsNumber(String goodsNumber) {
                            this.goodsNumber = goodsNumber;
                        }
                    }
                }
            }
        }
    }
}

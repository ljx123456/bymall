package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/7/10.
 */

public class Cart {

    /**
     * agent_id : 0
     * list : [{"product_id":"1","num":"1"},{"product_id":"93","num":9}]
     */

    private String agent_id;
    private List<ListBean> list;

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * product_id : 1
         * num : 1
         */

        private String product_id;
        private String num;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}

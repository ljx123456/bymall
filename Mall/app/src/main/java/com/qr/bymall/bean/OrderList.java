package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/7/20.
 */

public class OrderList {

    /**
     * code : 1
     * message : success
     * data : [{"shop_name":"自营","product_num":4,"product":[{"name":"果粒奶优(蜜桃)","title":"果粒奶优(香蕉）","price":2.5,"img":"https://s.qingrongby.com/20180518/5afe8f1082314.png","pid":"4","num":"1"},{"name":"金磨坊鱼豆腐（香辣味）","title":"金磨坊鱼豆腐（香辣味）","price":"1","img":"https://s.qingrongby.com/20180518/5afe759c8b416.png","pid":"29","num":"1"},{"name":"好心肠(原香味）","title":"好心肠(烧烤味）","price":"1","img":"https://s.qingrongby.com/20180510/5af3c33d656b9.png","pid":"86","num":"1"},{"name":"海苔（原味大）","title":"海苔（原味小）","price":"6.8","img":"https://s.qingrongby.com/20180518/5afe6c86b7442.png","pid":"74","num":"2"}],"total_price":20.1,"express_fee":"2","order_id":"1530","agent_phone":""},{"shop_name":"自营","product_num":5,"product":[{"name":"果粒奶优(蜜桃)","title":"果粒奶优(香蕉）","price":2.5,"img":"https://s.qingrongby.com/20180518/5afe8f1082314.png","pid":"4","num":"1"},{"name":"金磨坊鱼豆腐（香辣味）","title":"金磨坊鱼豆腐（香辣味）","price":"1","img":"https://s.qingrongby.com/20180518/5afe759c8b416.png","pid":"29","num":"1"},{"name":"好心肠(原香味）","title":"好心肠(烧烤味）","price":"1","img":"https://s.qingrongby.com/20180510/5af3c33d656b9.png","pid":"86","num":"1"},{"name":"海苔（原味大）","title":"海苔（原味小）","price":"6.8","img":"https://s.qingrongby.com/20180518/5afe6c86b7442.png","pid":"74","num":"2"},{"name":"素毛肚（香辣味）","title":"素毛肚（香辣味）","price":"1","img":"https://s.qingrongby.com/20180518/5afe74c9bcb1c.png","pid":"89","num":"1"}],"total_price":21.1,"express_fee":"2","order_id":"1527","agent_phone":""},{"shop_name":"自营","product_num":1,"product":[{"name":"果粒奶优(蜜桃)","title":"果粒奶优(香蕉）","price":2.5,"img":"https://s.qingrongby.com/20180518/5afe8f1082314.png","pid":"4","num":"1"}],"total_price":4.5,"express_fee":"2","order_id":"1526","agent_phone":""},{"shop_name":"自营","product_num":2,"product":[{"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）test","price":"2","img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","pid":"1","num":1},{"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","price":"2","img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","pid":"93","num":1}],"total_price":6,"express_fee":"2","order_id":"1523","agent_phone":""},{"shop_name":"自营","product_num":2,"product":[{"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）test","price":"2","img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","pid":"1","num":1},{"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","price":"2","img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","pid":"93","num":1}],"total_price":6,"express_fee":"2","order_id":"1522","agent_phone":""},{"shop_name":"自营","product_num":2,"product":[{"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）test","price":"2","img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","pid":"1","num":1},{"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","price":"2","img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","pid":"93","num":1}],"total_price":6,"express_fee":"2","order_id":"1521","agent_phone":""},{"shop_name":"自营","product_num":2,"product":[{"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）test","price":"2","img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","pid":"1","num":1},{"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","price":"2","img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","pid":"93","num":1}],"total_price":6,"express_fee":"2","order_id":"1520","agent_phone":""},{"shop_name":"自营","product_num":2,"product":[{"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）test","price":"2","img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","pid":"1","num":1},{"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","price":"2","img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","pid":"93","num":1}],"total_price":6,"express_fee":"2","order_id":"1519","agent_phone":""}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shop_name : 自营
         * product_num : 4
         * product : [{"name":"果粒奶优(蜜桃)","title":"果粒奶优(香蕉）","price":2.5,"img":"https://s.qingrongby.com/20180518/5afe8f1082314.png","pid":"4","num":"1"},{"name":"金磨坊鱼豆腐（香辣味）","title":"金磨坊鱼豆腐（香辣味）","price":"1","img":"https://s.qingrongby.com/20180518/5afe759c8b416.png","pid":"29","num":"1"},{"name":"好心肠(原香味）","title":"好心肠(烧烤味）","price":"1","img":"https://s.qingrongby.com/20180510/5af3c33d656b9.png","pid":"86","num":"1"},{"name":"海苔（原味大）","title":"海苔（原味小）","price":"6.8","img":"https://s.qingrongby.com/20180518/5afe6c86b7442.png","pid":"74","num":"2"}]
         * total_price : 20.1
         * express_fee : 2
         * order_id : 1530
         * agent_phone :
         */

        private String shop_name;
        private int product_num;
        private double total_price;
        private String express_fee;
        private String order_id;
        private String agent_phone;
        private List<ProductBean> product;

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getProduct_num() {
            return product_num;
        }

        public void setProduct_num(int product_num) {
            this.product_num = product_num;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public String getExpress_fee() {
            return express_fee;
        }

        public void setExpress_fee(String express_fee) {
            this.express_fee = express_fee;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getAgent_phone() {
            return agent_phone;
        }

        public void setAgent_phone(String agent_phone) {
            this.agent_phone = agent_phone;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * name : 果粒奶优(蜜桃)
             * title : 果粒奶优(香蕉）
             * price : 2.5
             * img : https://s.qingrongby.com/20180518/5afe8f1082314.png
             * pid : 4
             * num : 1
             */

            private String name;
            private String title;
            private double price;
            private String img;
            private String pid;
            private String num;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}

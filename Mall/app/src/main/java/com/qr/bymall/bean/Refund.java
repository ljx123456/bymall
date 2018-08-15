package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/7/26.
 */

public class Refund {

    /**
     * code : 1
     * message : success
     * data : [{"shop_name":"自营","product_num":1,"product":[{"name":"田园薯片（香辣味）","title":"田园薯片（宫保鸡丁）","price":"4","img":"https://s.qingrongby.com/20180518/5afe7e3e18e3f.png","pid":"18","num":"1"}],"total_price":6,"express_fee":"2","order_id":"1517","memo":"","status":"8"},{"shop_name":"自营","product_num":1,"product":[{"name":"田园薯片（烤肉味）","title":"田园薯片（宫保鸡丁）","price":"6","img":"https://s.qingrongby.com/20180518/5afe7e3e18e3f.png","pid":"17","num":"1"}],"total_price":8,"express_fee":"2","order_id":"1516","memo":"","status":"7"}]
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
         * product_num : 1
         * product : [{"name":"田园薯片（香辣味）","title":"田园薯片（宫保鸡丁）","price":"4","img":"https://s.qingrongby.com/20180518/5afe7e3e18e3f.png","pid":"18","num":"1"}]
         * total_price : 6
         * express_fee : 2
         * order_id : 1517
         * memo :
         * status : 8
         */

        private String shop_name;
        private int product_num;
        private int total_price;
        private String express_fee;
        private String order_id;
        private String memo;
        private String status;
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

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
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

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * name : 田园薯片（香辣味）
             * title : 田园薯片（宫保鸡丁）
             * price : 4
             * img : https://s.qingrongby.com/20180518/5afe7e3e18e3f.png
             * pid : 18
             * num : 1
             */

            private String name;
            private String title;
            private String price;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
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

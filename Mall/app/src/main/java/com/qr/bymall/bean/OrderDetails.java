package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/7/20.
 */

public class OrderDetails {

    /**
     * code : 1
     * message : success
     * data : {"products":[{"pid":1,"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）","num":1,"img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","price":"2"},{"pid":93,"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","num":1,"img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","price":"2"}],"express_fee":"2","address":{"address_id":"2","rec_name":"杨云才","rec_phone":"13880494109","rec_detail":"test","school":"成都师范学院","room":"七号院A区"},"coupon_fee":"0","product_price":"4","pay_money":"6","order_status":"0","agent_phone":"","shop_name":"自营"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * products : [{"pid":1,"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）","num":1,"img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","price":"2"},{"pid":93,"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","num":1,"img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","price":"2"}]
         * express_fee : 2
         * address : {"address_id":"2","rec_name":"杨云才","rec_phone":"13880494109","rec_detail":"test","school":"成都师范学院","room":"七号院A区"}
         * coupon_fee : 0
         * product_price : 4
         * pay_money : 6
         * order_status : 0
         * agent_phone :
         * shop_name : 自营
         */

        private String express_fee;
        private AddressBean address;
        private String coupon_fee;
        private String product_price;
        private String pay_money;
        private String order_status;
        private String agent_phone;
        private String shop_name;
        private List<ProductsBean> products;

        public String getExpress_fee() {
            return express_fee;
        }

        public void setExpress_fee(String express_fee) {
            this.express_fee = express_fee;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public String getCoupon_fee() {
            return coupon_fee;
        }

        public void setCoupon_fee(String coupon_fee) {
            this.coupon_fee = coupon_fee;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getAgent_phone() {
            return agent_phone;
        }

        public void setAgent_phone(String agent_phone) {
            this.agent_phone = agent_phone;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class AddressBean {
            /**
             * address_id : 2
             * rec_name : 杨云才
             * rec_phone : 13880494109
             * rec_detail : test
             * school : 成都师范学院
             * room : 七号院A区
             */

            private String address_id;
            private String rec_name;
            private String rec_phone;
            private String rec_detail;
            private String school;
            private String room;

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getRec_name() {
                return rec_name;
            }

            public void setRec_name(String rec_name) {
                this.rec_name = rec_name;
            }

            public String getRec_phone() {
                return rec_phone;
            }

            public void setRec_phone(String rec_phone) {
                this.rec_phone = rec_phone;
            }

            public String getRec_detail() {
                return rec_detail;
            }

            public void setRec_detail(String rec_detail) {
                this.rec_detail = rec_detail;
            }

            public String getSchool() {
                return school;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public String getRoom() {
                return room;
            }

            public void setRoom(String room) {
                this.room = room;
            }
        }

        public static class ProductsBean {
            /**
             * pid : 1
             * name : 恒大冰泉（经典）
             * title : 恒大冰泉（经典）
             * num : 1
             * img : https://s.qingrongby.com/20180518/5afe7e99c437c.png
             * price : 2
             */

            private int pid;
            private String name;
            private String title;
            private int num;
            private String img;
            private String price;

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

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

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}

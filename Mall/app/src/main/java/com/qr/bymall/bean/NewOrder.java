package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/7/16.
 */

public class NewOrder {

    /**
     * code : 1
     * message : success
     * data : {"products":[{"product_id":1,"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）","num":3,"main_img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","price":"2"},{"product_id":93,"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","num":1,"main_img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","price":"2"}],"productsPrice":8,"express_fee":2,"address":{"address_id":"1","rec_name":"杨红","rec_phone":"18684000453","rec_detail":"知我者谓我心忧","school":"成都师范学院","room":"一号院A区"},"wallet":"0","shop_name":"自营","agent_id":"0","coupons":[{"coupon_use_id":"1615","start_date":"2018.07.10","end_date":"2018.07.17","name":"配送券","desc":"全场通用，领取后7天内有效","money":"2"}]}
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
         * products : [{"product_id":1,"name":"恒大冰泉（经典）","title":"恒大冰泉（经典）","num":3,"main_img":"https://s.qingrongby.com/20180518/5afe7e99c437c.png","price":"2"},{"product_id":93,"name":"口口丫鸭脖（香辣味）","title":"口口丫鸭脖（香辣味）","num":1,"main_img":"https://s.qingrongby.com/20180518/5afe75bfc3af2.png","price":"2"}]
         * productsPrice : 8
         * express_fee : 2
         * address : {"address_id":"1","rec_name":"杨红","rec_phone":"18684000453","rec_detail":"知我者谓我心忧","school":"成都师范学院","room":"一号院A区"}
         * wallet : 0
         * shop_name : 自营
         * agent_id : 0
         * coupons : [{"coupon_use_id":"1615","start_date":"2018.07.10","end_date":"2018.07.17","name":"配送券","desc":"全场通用，领取后7天内有效","money":"2"}]
         */

        private String productsPrice;
        private String express_fee;
        private AddressBean address;
        private String wallet;
        private String shop_name;
        private String agent_id;
        private List<ProductsBean> products;
        private List<CouponsBean> coupons;

        public String getProductsPrice() {
            return productsPrice;
        }

        public void setProductsPrice(String productsPrice) {
            this.productsPrice = productsPrice;
        }

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

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public List<CouponsBean> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<CouponsBean> coupons) {
            this.coupons = coupons;
        }

        public static class AddressBean {
            /**
             * address_id : 1
             * rec_name : 杨红
             * rec_phone : 18684000453
             * rec_detail : 知我者谓我心忧
             * school : 成都师范学院
             * room : 一号院A区
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
             * product_id : 1
             * name : 恒大冰泉（经典）
             * title : 恒大冰泉（经典）
             * num : 3
             * main_img : https://s.qingrongby.com/20180518/5afe7e99c437c.png
             * price : 2
             */

            private int product_id;
            private String name;
            private String title;
            private int num;
            private String main_img;
            private String price;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
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

            public String getMain_img() {
                return main_img;
            }

            public void setMain_img(String main_img) {
                this.main_img = main_img;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }

        public static class CouponsBean {
            /**
             * coupon_use_id : 1615
             * start_date : 2018.07.10
             * end_date : 2018.07.17
             * name : 配送券
             * desc : 全场通用，领取后7天内有效
             * money : 2
             */

            private String coupon_use_id;
            private String start_date;
            private String end_date;
            private String name;
            private String desc;
            private String money;

            public String getCoupon_use_id() {
                return coupon_use_id;
            }

            public void setCoupon_use_id(String coupon_use_id) {
                this.coupon_use_id = coupon_use_id;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}

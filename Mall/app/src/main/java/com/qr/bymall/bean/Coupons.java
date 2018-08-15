package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/7/25.
 */

public class Coupons {

    /**
     * code : 1
     * message : success
     * data : {"get":[{"coupon_id":"4","end_date":"2018.07.28","start_date":"2018.05.24","use_status":"0","name":"通用券","desc":"满50元使用，领取后7天内有效","money":"5"}],"unget":[{"name":"通用券","desc":"满10元使用，领取后14天内有效","money":"1","coupon_id":"7","start_date":"2018.07.20","end_date":"2018.08.03"}]}
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
        private List<GetBean> get;
        private List<UngetBean> unget;

        public List<GetBean> getGet() {
            return get;
        }

        public void setGet(List<GetBean> get) {
            this.get = get;
        }

        public List<UngetBean> getUnget() {
            return unget;
        }

        public void setUnget(List<UngetBean> unget) {
            this.unget = unget;
        }

        public static class GetBean {
            /**
             * coupon_id : 4
             * end_date : 2018.07.28
             * start_date : 2018.05.24
             * use_status : 0
             * name : 通用券
             * desc : 满50元使用，领取后7天内有效
             * money : 5
             */

            private String coupon_id;
            private String end_date;
            private String start_date;
            private String use_status;
            private String name;
            private String desc;
            private String money;

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getUse_status() {
                return use_status;
            }

            public void setUse_status(String use_status) {
                this.use_status = use_status;
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

        public static class UngetBean {
            /**
             * name : 通用券
             * desc : 满10元使用，领取后14天内有效
             * money : 1
             * coupon_id : 7
             * start_date : 2018.07.20
             * end_date : 2018.08.03
             */

            private String name;
            private String desc;
            private String money;
            private String coupon_id;
            private String start_date;
            private String end_date;

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

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
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
        }
    }
}

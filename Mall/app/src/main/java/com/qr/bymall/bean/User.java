package com.qr.bymall.bean;

/**
 * Created by alu on 2018/7/3.
 */

public class User {

    /**
     * code : 1
     * message : success
     * data : {"token":"fecfb0a3-99c0-4def-98c5-2b8e655ffa02","customer_id":1}
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
         * token : fecfb0a3-99c0-4def-98c5-2b8e655ffa02
         * customer_id : 1
         */

        private String token;
        private int customer_id;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }
    }
}

package com.qr.bymall.bean;

/**
 * Created by alu on 2018/7/25.
 */

public class PayInfo {

    /**
     * code : 1
     * message : success
     * data : {"money":"6","wallet":"963"}
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
         * money : 6
         * wallet : 963
         */

        private String money;
        private String wallet;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }
    }
}

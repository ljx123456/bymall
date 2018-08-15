package com.qr.bymall.bean;

/**
 * Created by alu on 2018/7/20.
 */

public class PersonalInfo  {

    /**
     * code : 1
     * message : success
     * data : {"name":"13880494109","money":"994","score":"0"}
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
         * name : 13880494109
         * money : 994
         * score : 0
         */

        private String name;
        private String money;
        private String score;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}

package com.qr.bymall.bean;

/**
 * Created by alu on 2018/6/29.
 */

public class School {

    /**
     * code : 1
     * message : success
     * data : {"school_id":"1","school_name":"成都师范学院","search_text":"休闲美食","flag":0}
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
         * school_id : 1
         * school_name : 成都师范学院
         * search_text : 休闲美食
         * flag : 0
         */

        private String school_id;
        private String school_name;
        private String search_text;
        private int flag;

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSearch_text() {
            return search_text;
        }

        public void setSearch_text(String search_text) {
            this.search_text = search_text;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }
}

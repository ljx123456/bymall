package com.qr.bymall.bean;

/**
 * Created by alu on 2018/7/13.
 */

public class Address {

    /**
     * code : 1
     * message : success
     * data : {"address_id":"12","rec_name":"柚子2","rec_phone":"13880494109","rec_detail":"5楼12号","school":"成都师范学院","room":"一号院A区","rec_school":"1","rec_room":"1","sex":"0","default_flag":"0"}
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
         * address_id : 12
         * rec_name : 柚子2
         * rec_phone : 13880494109
         * rec_detail : 5楼12号
         * school : 成都师范学院
         * room : 一号院A区
         * rec_school : 1
         * rec_room : 1
         * sex : 0
         * default_flag : 0
         */

        private String address_id;
        private String rec_name;
        private String rec_phone;
        private String rec_detail;
        private String school;
        private String room;
        private String rec_school;
        private String rec_room;
        private String sex;
        private String default_flag;

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

        public String getRec_school() {
            return rec_school;
        }

        public void setRec_school(String rec_school) {
            this.rec_school = rec_school;
        }

        public String getRec_room() {
            return rec_room;
        }

        public void setRec_room(String rec_room) {
            this.rec_room = rec_room;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDefault_flag() {
            return default_flag;
        }

        public void setDefault_flag(String default_flag) {
            this.default_flag = default_flag;
        }
    }
}

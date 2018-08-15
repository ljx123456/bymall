package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/7/13.
 */

public class AddressList {

    /**
     * code : 1
     * message : success
     * data : [{"address_id":"12","rec_name":"柚子2","rec_phone":"13880494109","rec_detail":"5楼12号","school":"成都师范学院","default_flag":"0","room":"一号院A区"}]
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
         * address_id : 12
         * rec_name : 柚子2
         * rec_phone : 13880494109
         * rec_detail : 5楼12号
         * school : 成都师范学院
         * default_flag : 0
         * room : 一号院A区
         */

        private String address_id;
        private String rec_name;
        private String rec_phone;
        private String rec_detail;
        private String school;
        private String default_flag;
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

        public String getDefault_flag() {
            return default_flag;
        }

        public void setDefault_flag(String default_flag) {
            this.default_flag = default_flag;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }
    }
}

package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/6/29.
 */

public class NearBySchool {

    /**
     * code : 1
     * message : success
     * data : {"current":{"school_id":"1","school_name":"成都师范学院"},"near":[{"school_id":"3","school_name":"四川农业大学（成都校区）"}]}
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
         * current : {"school_id":"1","school_name":"成都师范学院"}
         * near : [{"school_id":"3","school_name":"四川农业大学（成都校区）"}]
         */

        private CurrentBean current;
        private List<NearBean> near;

        public CurrentBean getCurrent() {
            return current;
        }

        public void setCurrent(CurrentBean current) {
            this.current = current;
        }

        public List<NearBean> getNear() {
            return near;
        }

        public void setNear(List<NearBean> near) {
            this.near = near;
        }

        public static class CurrentBean {
            /**
             * school_id : 1
             * school_name : 成都师范学院
             */

            private String school_id;
            private String school_name;

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
        }

        public static class NearBean {
            /**
             * school_id : 3
             * school_name : 四川农业大学（成都校区）
             */

            private String school_id;
            private String school_name;

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
        }
    }
}

package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/6/29.
 */

public class NearByRoom {

    /**
     * code : 1
     * message : success
     * data : {"current":{"room_id":"","room_name":""},"other":[{"room_id":"1","room_name":"一号院A区"},{"room_id":"2","room_name":"一号院B区"},{"room_id":"3","room_name":"一号院C区"},{"room_id":"4","room_name":"二号院A区"},{"room_id":"5","room_name":"二号院B区"},{"room_id":"6","room_name":"二号院C区"},{"room_id":"7","room_name":"三号院A区"},{"room_id":"8","room_name":"三号院B区"},{"room_id":"9","room_name":"四号院A区"},{"room_id":"10","room_name":"四号院B区"},{"room_id":"11","room_name":"四号院C区"},{"room_id":"12","room_name":"五号院A区"},{"room_id":"13","room_name":"五号院B区"},{"room_id":"14","room_name":"六号院A区"},{"room_id":"15","room_name":"六号院B区"},{"room_id":"16","room_name":"六号院C区"},{"room_id":"17","room_name":"七号院A区"},{"room_id":"18","room_name":"七号院B区"},{"room_id":"19","room_name":"七号院C区"}]}
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
         * current : {"room_id":"","room_name":""}
         * other : [{"room_id":"1","room_name":"一号院A区"},{"room_id":"2","room_name":"一号院B区"},{"room_id":"3","room_name":"一号院C区"},{"room_id":"4","room_name":"二号院A区"},{"room_id":"5","room_name":"二号院B区"},{"room_id":"6","room_name":"二号院C区"},{"room_id":"7","room_name":"三号院A区"},{"room_id":"8","room_name":"三号院B区"},{"room_id":"9","room_name":"四号院A区"},{"room_id":"10","room_name":"四号院B区"},{"room_id":"11","room_name":"四号院C区"},{"room_id":"12","room_name":"五号院A区"},{"room_id":"13","room_name":"五号院B区"},{"room_id":"14","room_name":"六号院A区"},{"room_id":"15","room_name":"六号院B区"},{"room_id":"16","room_name":"六号院C区"},{"room_id":"17","room_name":"七号院A区"},{"room_id":"18","room_name":"七号院B区"},{"room_id":"19","room_name":"七号院C区"}]
         */

        private CurrentBean current;
        private List<OtherBean> other;

        public CurrentBean getCurrent() {
            return current;
        }

        public void setCurrent(CurrentBean current) {
            this.current = current;
        }

        public List<OtherBean> getOther() {
            return other;
        }

        public void setOther(List<OtherBean> other) {
            this.other = other;
        }

        public static class CurrentBean {
            /**
             * room_id :
             * room_name :
             */

            private String room_id;
            private String room_name;

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }

            public String getRoom_name() {
                return room_name;
            }

            public void setRoom_name(String room_name) {
                this.room_name = room_name;
            }
        }

        public static class OtherBean {
            /**
             * room_id : 1
             * room_name : 一号院A区
             */

            private String room_id;
            private String room_name;

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }

            public String getRoom_name() {
                return room_name;
            }

            public void setRoom_name(String room_name) {
                this.room_name = room_name;
            }
        }
    }
}

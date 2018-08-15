package com.qr.bymall.bean;

import java.util.List;

/**
 * Created by alu on 2018/5/5.
 */

public class Version {

    /**
     * code : 1
     * message : success
     * data : {"version":"1.0.7","link":"https://api.qingrongby.com/apk/app_release_1.0.7.apk","size":"6.88M","content":["test1","test2"]}
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
         * version : 1.0.7
         * link : https://api.qingrongby.com/apk/app_release_1.0.7.apk
         * size : 6.88M
         * content : ["test1","test2"]
         */

        private String version;
        private String link;
        private String size;
        private List<String> content;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public List<String> getContent() {
            return content;
        }

        public void setContent(List<String> content) {
            this.content = content;
        }
    }
}

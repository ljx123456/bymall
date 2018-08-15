package com.qr.bymall.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alu on 2018/7/25.
 */

public class WechatPay {

    /**
     * code : 1
     * message : success
     * data : {"params":{"appid":"wx6aea495997e32cc1","noncestr":"SCuXxmRokmrj8V1l","partnerid":"1509251851","prepayid":"wx25182511694169c102844c2a1117901696","package":"Sign=WXPay","timestamp":1532514311,"paySign":"A162730574FAA6F6CE12A1C9A5C584B5"},"terminal":"wechat","out_trade_no":"5b585007734e9_1533","order_id":"1533"}
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
         * params : {"appid":"wx6aea495997e32cc1","noncestr":"SCuXxmRokmrj8V1l","partnerid":"1509251851","prepayid":"wx25182511694169c102844c2a1117901696","package":"Sign=WXPay","timestamp":1532514311,"paySign":"A162730574FAA6F6CE12A1C9A5C584B5"}
         * terminal : wechat
         * out_trade_no : 5b585007734e9_1533
         * order_id : 1533
         */

        private ParamsBean params;
        private String terminal;
        private String out_trade_no;
        private String order_id;

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public String getTerminal() {
            return terminal;
        }

        public void setTerminal(String terminal) {
            this.terminal = terminal;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public static class ParamsBean {
            /**
             * appid : wx6aea495997e32cc1
             * noncestr : SCuXxmRokmrj8V1l
             * partnerid : 1509251851
             * prepayid : wx25182511694169c102844c2a1117901696
             * package : Sign=WXPay
             * timestamp : 1532514311
             * paySign : A162730574FAA6F6CE12A1C9A5C584B5
             */

            private String appid;
            private String noncestr;
            private String partnerid;
            private String prepayid;
            @SerializedName("package")
            private String packageX;
            private int timestamp;
            private String paySign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getPaySign() {
                return paySign;
            }

            public void setPaySign(String paySign) {
                this.paySign = paySign;
            }
        }
    }
}

package com.qr.bymall.bean;

/**
 * Created by alu on 2018/7/19.
 */

public class WalletPay {

    /**
     * code : 1
     * message : success
     * data : {"order_id":"314","terminal":"wap","out_trade_no":"5aec2a93ac92f_314","pay_status":1}
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
         * order_id : 314
         * terminal : wap
         * out_trade_no : 5aec2a93ac92f_314
         * pay_status : 1
         */

        private String order_id;
        private String terminal;
        private String out_trade_no;
        private int pay_status;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }
    }
}

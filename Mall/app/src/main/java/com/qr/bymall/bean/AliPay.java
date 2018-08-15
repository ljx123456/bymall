package com.qr.bymall.bean;

/**
 * Created by alu on 2018/8/14.
 */

public class AliPay {

    /**
     * code : 1
     * message : success
     * data : {"order_id":"1559","terminal":"aliapp","out_trade_no":"5b724d253416f_1559","script":"alipay_sdk=alipay-sdk-php-20161101&amp;app_id=2018062660437203&amp;biz_content=%7B%22subject%22%3A%22%5Cu5546%5Cu57ce%5Cu8ba2%5Cu5355%22%2C%22out_trade_no%22%3A%225b724d253416f_1559%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%228%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;notify_url=https%3A%2F%2Fapi.qingrongby.com%2Fopen%2Fnotify%2Fali&amp;sign_type=RSA2&amp;timestamp=2018-08-14+11%3A31%3A49&amp;version=1.0&amp;sign=oxnFKDoE0AIJvdZS0tUYzKuugaIvExRjmEsuLs8V%2Fqa0%2B8TDA5lLDLj3T2EMSqla1Yxt7rAvkuuJluI7voGgVTQBO3nQy%2F4fY62AT3aFt%2B%2BMaizGWFb1u%2F%2B3lCVFCjKk8tSqHVPt0mlEqcZRFXZNhe4ENayPyITuoVvSYtBkUu%2FQOhCdl3qeUwaClv%2FriEg3d6FHxNNMksRrxdXrZGMT4gr4JTAvT967gyHH%2BXrdyor6k%2FM0csyvSpChcHJAIZvEupoLiSFM0H3m5vHauPbOPMgHGq%2FMz7%2F%2FQ3UL9yw6VKc74T7SwkQI7iuZIpM5%2BIND6uMO5BJ2WziLMLIK%2FSmTaw%3D%3D"}
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
         * order_id : 1559
         * terminal : aliapp
         * out_trade_no : 5b724d253416f_1559
         * script : alipay_sdk=alipay-sdk-php-20161101&amp;app_id=2018062660437203&amp;biz_content=%7B%22subject%22%3A%22%5Cu5546%5Cu57ce%5Cu8ba2%5Cu5355%22%2C%22out_trade_no%22%3A%225b724d253416f_1559%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%228%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;notify_url=https%3A%2F%2Fapi.qingrongby.com%2Fopen%2Fnotify%2Fali&amp;sign_type=RSA2&amp;timestamp=2018-08-14+11%3A31%3A49&amp;version=1.0&amp;sign=oxnFKDoE0AIJvdZS0tUYzKuugaIvExRjmEsuLs8V%2Fqa0%2B8TDA5lLDLj3T2EMSqla1Yxt7rAvkuuJluI7voGgVTQBO3nQy%2F4fY62AT3aFt%2B%2BMaizGWFb1u%2F%2B3lCVFCjKk8tSqHVPt0mlEqcZRFXZNhe4ENayPyITuoVvSYtBkUu%2FQOhCdl3qeUwaClv%2FriEg3d6FHxNNMksRrxdXrZGMT4gr4JTAvT967gyHH%2BXrdyor6k%2FM0csyvSpChcHJAIZvEupoLiSFM0H3m5vHauPbOPMgHGq%2FMz7%2F%2FQ3UL9yw6VKc74T7SwkQI7iuZIpM5%2BIND6uMO5BJ2WziLMLIK%2FSmTaw%3D%3D
         */

        private String order_id;
        private String terminal;
        private String out_trade_no;
        private String script;

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

        public String getScript() {
            return script;
        }

        public void setScript(String script) {
            this.script = script;
        }
    }
}

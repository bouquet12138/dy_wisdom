package com.example.common_lib.bean;

import java.io.Serializable;

/**
 * 短信验证码
 */
public class SMSBean {

    /**
     * {
     * "reason": "操作成功",
     * "result": {
     * "sid": "54482003827A3678",
     * "fee": 1,
     * "count": 1
     * },
     * "error_code": 0
     * }
     */

    private String reason;
    private Result result;
    private int error_code;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code() {
        return error_code;
    }

    public class Result implements Serializable {

        private String sid;
        private int fee;
        private int count;

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSid() {
            return sid;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getFee() {
            return fee;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

    }

}

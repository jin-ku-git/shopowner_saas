package com.youwu.shopowner_saas.service;


/**
 * 快递查询的bjavaean
 */
public class Bean {

    public String message;//状态 OK
    public String msg;
    public String des;
    public int code;
    public int rc;
    public Object result;
    public Object data;
    public String success;
    public String timestamp;

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "message='" + message + '\'' +
                ", msg='" + msg + '\'' +
                ", des='" + des + '\'' +
                ", code=" + code +
                ", rc=" + rc +
                ", result=" + result +
                ", data=" + data +
                ", success='" + success + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

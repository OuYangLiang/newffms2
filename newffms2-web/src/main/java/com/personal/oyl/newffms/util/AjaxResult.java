package com.personal.oyl.newffms.util;

public class AjaxResult<T> {
    private boolean success;
    private String errCode;
    private String errMsg;
    private T data;
    
    public AjaxResult(boolean success) {
        super();
        this.success = success;
    }

    public AjaxResult(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    public AjaxResult(boolean success, String errCode, String errMsg) {
        super();
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

package com.personal.oyl.newffms.common;

@SuppressWarnings("serial")
public class NewffmsDomainException extends Exception {

    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }
    
    public NewffmsDomainException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public NewffmsDomainException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public NewffmsDomainException(String errorCode, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public NewffmsDomainException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public NewffmsDomainException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
    
    public static class NewffmsSystemException extends NewffmsDomainException {
        public NewffmsSystemException() {
            super("EFFMS000", "系统错误。");
        }
    }
    
    public static class NoOperatorException extends NewffmsDomainException {
        public NoOperatorException() {
            super("EFFMS001", "操作人为空。");
        }
    }

}

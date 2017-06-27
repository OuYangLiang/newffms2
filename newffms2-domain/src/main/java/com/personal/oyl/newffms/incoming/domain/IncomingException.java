package com.personal.oyl.newffms.incoming.domain;

import com.personal.oyl.newffms.common.NewffmsDomainException;

@SuppressWarnings("serial")
public class IncomingException {
    
    public static class IncomingAlreadyConfirmedException extends NewffmsDomainException {
        public IncomingAlreadyConfirmedException() {
            super("EFFMS401", "收入已经是确认状态，不能操作");
        }
    }
    
    public static class IncomingNotConfirmedException extends NewffmsDomainException {
        public IncomingNotConfirmedException() {
            super("EFFMS402", "收入还未确认。");
        }
    }
    
    public static class IncomingKeyEmptyException extends NewffmsDomainException {
        public IncomingKeyEmptyException() {
            super("EFFMS403", "收入id不能为空。");
        }
    }
    
    public static class IncomingNotExistException extends NewffmsDomainException {
        public IncomingNotExistException() {
            super("EFFMS404", "收入不存在。");
        }
    }
    
    public static class IncomingDescEmptyException extends NewffmsDomainException {
        public IncomingDescEmptyException() {
            super("EFFMS405", "收入描述不能为空。");
        }
    }
    
    public static class IncomingDescInvalidException extends NewffmsDomainException {
        public IncomingDescInvalidException() {
            super("EFFMS406", "收入描述不合法。");
        }
    }
    
    public static class IncomingAmountEmptyException extends NewffmsDomainException {
        public IncomingAmountEmptyException() {
            super("EFFMS407", "收入金额不能为空。");
        }
    }
    
    public static class IncomingAmountInvalidException extends NewffmsDomainException {
        public IncomingAmountInvalidException() {
            super("EFFMS408", "收入金额不合法。");
        }
    }
    
    public static class IncomingTypeEmptyException extends NewffmsDomainException {
        public IncomingTypeEmptyException() {
            super("EFFMS409", "收入类型不能为空。");
        }
    }
    
    public static class IncomingOwnerEmptyException extends NewffmsDomainException {
        public IncomingOwnerEmptyException() {
            super("EFFMS410", "收入所有人不能为空。");
        }
    }
    
    public static class IncomingDateEmptyException extends NewffmsDomainException {
        public IncomingDateEmptyException() {
            super("EFFMS411", "收入时期不能为空。");
        }
    }
    
    public static class IncomingAccountEmptyException extends NewffmsDomainException {
        public IncomingAccountEmptyException() {
            super("EFFMS412", "收入账户不能为空。");
        }
    }
    
    public static class IncomingBatchNumEmptyException extends NewffmsDomainException {
        public IncomingBatchNumEmptyException() {
            super("EFFMS413", "收入批次号不能为空。");
        }
    }
    
}

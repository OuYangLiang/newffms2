package com.personal.oyl.newffms.consumption.domain;

import com.personal.oyl.newffms.common.NewffmsDomainException;

@SuppressWarnings("serial")
public class ConsumptionException {
    
    public static class ConsumptionTypeEmptyException extends NewffmsDomainException {
        public ConsumptionTypeEmptyException() {
            super("EFFMS301", "消费方式不能为空。");
        }
    }
    
    public static class ConsumptionBatchNumEmptyException extends NewffmsDomainException {
        public ConsumptionBatchNumEmptyException() {
            super("EFFMS302", "消费批次号不能为空。");
        }
    }
    
    public static class ConsumptionTimeEmptyException extends NewffmsDomainException {
        public ConsumptionTimeEmptyException() {
            super("EFFMS303", "消费时间号不能为空。");
        }
    }
    
    public static class ConsumptionAlreadyConfirmedException extends NewffmsDomainException {
        public ConsumptionAlreadyConfirmedException() {
            super("EFFMS304", "消费已经是确认状态，不能操作。");
        }
    }
    
    public static class ConsumptionItemsEmptyException extends NewffmsDomainException {
        public ConsumptionItemsEmptyException() {
            super("EFFMS305", "消费项不能为空。");
        }
    }
    
    public static class ConsumptionItemDescEmptyException extends NewffmsDomainException {
        public ConsumptionItemDescEmptyException() {
            super("EFFMS306", "消费项描述不能为空。");
        }
    }
    
    public static class ConsumptionItemAmountEmptyException extends NewffmsDomainException {
        public ConsumptionItemAmountEmptyException() {
            super("EFFMS307", "消费项金额不能为空。");
        }
    }
    
    public static class ConsumptionItemAmountInvalidException extends NewffmsDomainException {
        public ConsumptionItemAmountInvalidException() {
            super("EFFMS308", "消费项金额不合法。");
        }
    }
    
    public static class ConsumptionItemOwnerEmptyException extends NewffmsDomainException {
        public ConsumptionItemOwnerEmptyException() {
            super("EFFMS309", "消费项所有人不能为空。");
        }
    }
    
    public static class ConsumptionItemCategoryEmptyException extends NewffmsDomainException {
        public ConsumptionItemCategoryEmptyException() {
            super("EFFMS310", "消费项所有人不能为空。");
        }
    }
    
    public static class ConsumptionPaymentsEmptyException extends NewffmsDomainException {
        public ConsumptionPaymentsEmptyException() {
            super("EFFMS311", "付款项不能为空。");
        }
    }
    
    public static class ConsumptionPaymentAmountEmptyException extends NewffmsDomainException {
        public ConsumptionPaymentAmountEmptyException() {
            super("EFFMS312", "付款项金额不能为空。");
        }
    }
    
    public static class ConsumptionPaymentAmountInvalidException extends NewffmsDomainException {
        public ConsumptionPaymentAmountInvalidException() {
            super("EFFMS313", "付款项金额不合法。");
        }
    }
    
    public static class ConsumptionPaymentAccountEmptyException extends NewffmsDomainException {
        public ConsumptionPaymentAccountEmptyException() {
            super("EFFMS314", "付款项账户不能为空。");
        }
    }
    
    public static class ConsumptionAmountNotMatchException extends NewffmsDomainException {
        public ConsumptionAmountNotMatchException() {
            super("EFFMS315", "消费总金额与付款总金额不匹配。");
        }
    }
    
    public static class ConsumptionKeyEmptyException extends NewffmsDomainException {
        public ConsumptionKeyEmptyException() {
            super("EFFMS316", "消费id不能为空。");
        }
    }
    
    public static class ConsumptionNotExistException extends NewffmsDomainException {
        public ConsumptionNotExistException() {
            super("EFFMS317", "消费不存在。");
        }
    }
    
    public static class ConsumptionNotConfirmedException extends NewffmsDomainException {
        public ConsumptionNotConfirmedException() {
            super("EFFMS318", "消费还未确认。");
        }
    }
}

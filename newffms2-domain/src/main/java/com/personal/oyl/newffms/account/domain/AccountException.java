package com.personal.oyl.newffms.account.domain;

import com.personal.oyl.newffms.common.NewffmsDomainException;

@SuppressWarnings("serial")
public class AccountException {
    
    public static class AccountDescEmptyException extends NewffmsDomainException {
        public AccountDescEmptyException() {
            super("EFFMS101", "账户描述不能为空。");
        }
    }
    
    public static class AccountDescInvalidException extends NewffmsDomainException {
        public AccountDescInvalidException() {
            super("EFFMS102", "账户描述不合法。");
        }
    }
    
    public static class AccountAmountInvalidException extends NewffmsDomainException {
        public AccountAmountInvalidException() {
            super("EFFMS103", "金额参数不合法。");
        }
    }
    
    public static class AccountBalanceInsufficiencyException extends NewffmsDomainException {
        public AccountBalanceInsufficiencyException() {
            super("EFFMS104", "账户余额不足。");
        }
    }
    
    public static class AccountOperationDescException extends NewffmsDomainException {
        public AccountOperationDescException() {
            super("EFFMS105", "操作说明不能为空。");
        }
    }
    
    public static class AccountKeyEmptyException extends NewffmsDomainException {
        public AccountKeyEmptyException() {
            super("EFFMS106", "账户id不能为空。");
        }
    }
    
    public static class AccountTypeEmptyException extends NewffmsDomainException {
        public AccountTypeEmptyException() {
            super("EFFMS107", "账户类型不能为空。");
        }
    }
    
    public static class AccountBalanceEmptyException extends NewffmsDomainException {
        public AccountBalanceEmptyException() {
            super("EFFMS108", "余额不能为空。");
        }
    }
    
    public static class AccountBalanceInvalidException extends NewffmsDomainException {
        public AccountBalanceInvalidException() {
            super("EFFMS109", "余额不合法。");
        }
    }
    
    public static class AccountOwnerEmptyException extends NewffmsDomainException {
        public AccountOwnerEmptyException() {
            super("EFFMS110", "余额所有人不能为空。");
        }
    }
    
    public static class AccountQuotaEmptyException extends NewffmsDomainException {
        public AccountQuotaEmptyException() {
            super("EFFMS111", "限额不能为空。");
        }
    }
    
    public static class AccountQuotaInvalidException extends NewffmsDomainException {
        public AccountQuotaInvalidException() {
            super("EFFMS112", "限额不合法。");
        }
    }
    
    public static class AccountDebtEmptyException extends NewffmsDomainException {
        public AccountDebtEmptyException() {
            super("EFFMS113", "欠款额不能为空。");
        }
    }
    
    public static class AccountDebtInvalidException extends NewffmsDomainException {
        public AccountDebtInvalidException() {
            super("EFFMS114", "欠款额不合法。");
        }
    }
    
    public static class AccountDebtPlusBalanceNeqQuotaException extends NewffmsDomainException {
        public AccountDebtPlusBalanceNeqQuotaException() {
            super("EFFMS115", "欠款额加余额必须等于限额。");
        }
    }
    
    public static class AccountDescTooLongException extends NewffmsDomainException {
        public AccountDescTooLongException() {
            super("EFFMS116", "账户描述最大不能超过30个字。");
        }
    }
    
    public static class AccountBatchNumEmptyException extends NewffmsDomainException {
        public AccountBatchNumEmptyException() {
            super("EFFMS117", "账户批次号不能为空。");
        }
    }
    
    public static class AccountBatchNumInvalidException extends NewffmsDomainException {
        public AccountBatchNumInvalidException() {
            super("EFFMS118", "账户批次号不合法。");
        }
    }
    
    public static class AccountNotExistException extends NewffmsDomainException {
        public AccountNotExistException() {
            super("EFFMS119", "账户不存在。");
        }
    }
    
    public static class AccountTransferToSelfException extends NewffmsDomainException {
        public AccountTransferToSelfException() {
            super("EFFMS120", "转账目标账户不合法。");
        }
    }
    
}

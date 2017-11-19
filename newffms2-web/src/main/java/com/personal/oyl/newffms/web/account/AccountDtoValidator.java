package com.personal.oyl.newffms.web.account;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.account.domain.AccountType;

public class AccountDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDto acnt = (AccountDto) target;

        if (null == acnt.getOwner() || null == acnt.getOwner().getUserOid()) {
            errors.reject(null, "账户所有人是谁啊，亲。");
        }

        if (null == acnt.getAcntType()) {
            //errors.reject(null, "账户类型是什么，亲。");
        }

        if (null != acnt.getTarget() && acnt.getAcntOid().equals(acnt.getTarget().getAcntOid())) {
            errors.reject(null, "目标账户与源账户相同，亲。");
        }
        
        if (null != acnt.getPayment() && acnt.getPayment().compareTo(BigDecimal.ZERO) <= 0) {
            errors.reject(null, "转账金额不合法啊，亲。");
        }

        if (null != acnt.getPayment() && acnt.getBalance().compareTo(acnt.getPayment()) < 0) {
            errors.reject(null, "余额不足啊，亲。");
        }

        if (AccountType.Creditcard.equals(acnt.getAcntType())) {
            if (null == acnt.getQuota()) {
                errors.reject(null, "信用卡限定额度是多少，亲。");
            } else if (acnt.getQuota().compareTo(BigDecimal.ZERO) <= 0) {
                errors.reject(null, "信用卡限定额度没问题？你确定吗，亲。");
            }

            if (null == acnt.getDebt()) {
                errors.reject(null, "信用卡欠款额度是多少，亲。");
            } else if (acnt.getDebt().compareTo(BigDecimal.ZERO) < 0) {
                errors.reject(null, "信用卡欠款额度是负数，你确定吗，亲。");
            }

            if (null != acnt.getQuota() && null != acnt.getDebt() && (acnt.getBalance().add(acnt.getDebt())
                    .subtract(acnt.getQuota()).compareTo(BigDecimal.ZERO) != 0)) {
                errors.reject(null, "信用卡限定额度不等于欠款额度与可用额度之和，亲。");
            }
        }

    }

}

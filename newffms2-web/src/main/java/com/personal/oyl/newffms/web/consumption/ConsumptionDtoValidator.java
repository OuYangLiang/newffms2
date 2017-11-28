package com.personal.oyl.newffms.web.consumption;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.web.account.AccountDto;

public class ConsumptionDtoValidator implements Validator {

    private ConsumptionItemDtoValidator itemValidator = new ConsumptionItemDtoValidator();

    @Override
    public boolean supports(Class<?> clazz) {
        return ConsumptionDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConsumptionDto form = (ConsumptionDto) target;

        if (null == form.getCpnType()) {
            errors.reject(null, "消费类型不可以为空哦，亲。");
        }

        if (null == form.getItems() || form.getItems().isEmpty()) {
            errors.reject(null, "没有消费项目啊，亲。");
            return;
        }

        if (null == form.getPayments() || form.getPayments().isEmpty()) {
            errors.reject(null, "你想不付钱啊，亲。");
            return;
        }

        BigDecimal totalItemAmount = BigDecimal.ZERO;
        BigDecimal totalPayment = BigDecimal.ZERO;

        int idx = 0;
        for (ConsumptionItemDto item : form.getItems()) {

            totalItemAmount = totalItemAmount.add(item.getAmount());

            try {
                errors.pushNestedPath("items[" + idx + "]");
                ValidationUtils.invokeValidator(itemValidator, item, errors);
            } finally {
                errors.popNestedPath();
            }
            idx++;
        }

        idx = 0;
        for (AccountDto acnt : form.getPayments()) {
            totalPayment = totalPayment.add(acnt.getPayment());

            /*
             * try { Account dbAcnt = accountService.selectByKey(new
             * AccountKey(acnt.getAcntOid()));
             * 
             * if (acnt.getPayment() != null) { if
             * (dbAcnt.getBalance().compareTo(acnt.getPayment()) < 0) {
             * errors.reject(null, "账户[ " + dbAcnt.getAcntHumanDesc() +
             * " ]余额不足啊，亲。"); } } } catch (SQLException e) {
             * log.error(e.getMessage(), e); }
             */

        }

        if (totalPayment.compareTo(totalItemAmount) != 0) {
            errors.reject(null, "消费总金额与支付总金额不匹配，亲。");
        }
    }

}

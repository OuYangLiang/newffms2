package com.personal.oyl.newffms.web.consumption;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ConsumptionItemDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ConsumptionItemDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "ownerOid", null, "是谁在消费啊，亲。");
    }

}

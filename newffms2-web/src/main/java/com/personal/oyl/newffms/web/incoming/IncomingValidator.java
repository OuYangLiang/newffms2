package com.personal.oyl.newffms.web.incoming;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IncomingValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return IncomingDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IncomingDto obj = (IncomingDto) target;

        if (null == obj.getOwner() || null == obj.getOwner().getUserOid()) {
            errors.reject(null, "谁的收入啊，亲。");
        }

        if (null == obj.getIncomingType()) {
            errors.reject(null, "收入类型呢，亲。");
        }

    }

}

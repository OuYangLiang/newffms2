package com.personal.oyl.newffms.web.category;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CategoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryDto category = (CategoryDto) target;

        if (null == category.getCategoryDesc()) {
            errors.reject(null, "描述是什么，亲。");
            return;
        }

        if (null == category.getMonthlyBudget()) {
            errors.reject(null, "月度预算是多少，亲。");
            return;
        }
    }
}

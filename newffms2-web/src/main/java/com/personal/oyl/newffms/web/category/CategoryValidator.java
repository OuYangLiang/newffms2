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

        /*
         * try { if (null == category.getCategoryOid()) { if
         * (categoryService.isCategoryExist(category.getParentOid(),
         * category.getCategoryDesc())) { errors.reject(null,
         * "描述在当前层次已经存在了，亲。"); return; } } else { Category oldObj =
         * categoryService.selectByParentAndDesc(category.getParentOid(),
         * category.getCategoryDesc()); if (null != oldObj &&
         * !oldObj.getCategoryOid().equals(category.getCategoryOid())) {
         * errors.reject(null, "描述在当前层次已经存在了，亲。"); return; } }
         * 
         * if (null == category.getCategoryOid() && null !=
         * category.getParentOid() &&
         * categoryService.isCategoryUsed(category.getParentOid())) {
         * errors.reject(null, "父类别已经被使用过了，不能再作为父类了，亲。"); return; }
         * 
         * } catch (SQLException e) { log.error(e.getMessage(), e); }
         */
    }

}

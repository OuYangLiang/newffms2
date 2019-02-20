package com.personal.oyl.newffms.category.domain;

import java.math.BigDecimal;

import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetInvalidException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescTooLongException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotLeafException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;

public interface CategoryOperation {
    
    /**
     * 变更当前类别名称
     *
     * @param newDesc 新的名称
     * @param operator 操作人
     * @throws CategoryDescEmptyException
     * @throws CategoryDescTooLongException
     * @throws NoOperatorException
     */
    void changeCategoryDesc(String newDesc, String operator)
            throws CategoryDescEmptyException, CategoryDescTooLongException, NoOperatorException;
    
    /**
     * 创建当前类别的子类别
     *
     * @param desc 子类别名称
     * @param budget 月度预算
     * @param operator 操作人
     * @return 子类别
     * @throws CategoryDescEmptyException
     * @throws CategoryDescTooLongException
     * @throws NoOperatorException
     * @throws CategoryBudgetEmptyException
     * @throws CategoryBudgetInvalidException
     * @throws NewffmsSystemException
     */
    Category addChild(String desc, BigDecimal budget, String operator)
            throws CategoryDescEmptyException, CategoryDescTooLongException, NoOperatorException,
            CategoryBudgetEmptyException, CategoryBudgetInvalidException, NewffmsSystemException;
    
    /**
     * 变更当前类别的月度预算（当前类别必须为叶子类别）
     *
     * @param newBudget 新的预算
     * @param operator 操作人
     * @throws CategoryNotLeafException
     * @throws NoOperatorException
     */
    void changeBudget(BigDecimal newBudget, String operator)
            throws CategoryNotLeafException, NoOperatorException, NewffmsSystemException;
}

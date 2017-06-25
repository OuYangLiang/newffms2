package com.personal.oyl.newffms.category.domain;

import java.util.List;

import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetInvalidException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescTooLongException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryKeyEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotExistException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotLeafException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotRootException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;

public interface CategoryRepos {
	/**
	 * 根据类别标识查询类别
	 * 
	 * @param key 类别标识
	 * @return 类别实体
	 * @throws CategoryKeyEmptyException
	 */
	Category categoryOfId(CategoryKey key) throws CategoryKeyEmptyException;
	
	/**
	 * 创建新的类别（仅限根类别）
	 * 
	 * @param bean 待创建类别实体
	 * @param operator 操作人
     * @throws CategoryDescEmptyException
     * @throws CategoryDescTooLongException
     * @throws NoOperatorException
     * @throws CategoryNotRootException
     * @throws CategoryBudgetEmptyException
     * @throws CategoryBudgetInvalidException
     */
    void add(Category bean, String operator) throws CategoryDescEmptyException, CategoryDescTooLongException,
            NoOperatorException, CategoryNotRootException, CategoryBudgetEmptyException, CategoryBudgetInvalidException;

	/**
	 * 删除类别（必须为叶子类别）
	 * 
	 * @param key 待删除类别标识
	 * @param operator 操作人
     * @throws CategoryKeyEmptyException
     * @throws NoOperatorException
     * @throws CategoryNotLeafException
     * @throws CategoryNotExistException
     */
    void remove(CategoryKey key, String operator)
            throws CategoryKeyEmptyException, NoOperatorException, CategoryNotLeafException, CategoryNotExistException;
	
	/**
	 * 根据父类别标识查询子类别
	 * 
	 * @param parentKey 父类别标识
	 * @return 子类别集合（无排序）
	 * @throws CategoryKeyEmptyException
	 */
	List<Category> categoriesOfParent(CategoryKey parentKey) throws CategoryKeyEmptyException;
	
	/**
	 * 查询所有根类别
	 * 
	 * @return 根类别集合（无排序）
	 */
	List<Category> rootCategories();
}

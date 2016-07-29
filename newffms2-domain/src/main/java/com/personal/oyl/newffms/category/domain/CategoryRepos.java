package com.personal.oyl.newffms.category.domain;

import java.util.List;

public interface CategoryRepos {
	/**
	 * 根据类别标识查询类别
	 * 
	 * @param key 类别标识
	 * @return 类别实体
	 */
	Category categoryOfId(CategoryKey key);
	
	/**
	 * 创建新的类别（仅限根类别）
	 * 
	 * @param bean 待创建类别实体
	 * @param operator 操作人
	 */
	void add(Category bean, String operator);
	
	/**
	 * 删除类别（必须为叶子类别）
	 * 
	 * @param key 待删除类别标识
	 * @param operator 操作人
	 */
	void remove(CategoryKey key, String operator);
	
	/**
	 * 根据父类别标识查询子类别
	 * 
	 * @param parentKey 父类别标识
	 * @return 子类别集合（无排序）
	 */
	List<Category> categoriesOfParent(CategoryKey parentKey);
}

package com.personal.oyl.newffms.category.domain;

import java.util.List;

public interface CategoryRepos {
	Category categoryOfId(CategoryKey key);
	
	void add(Category bean, String operator);
	
	void remove(CategoryKey key, String operator);
	
	List<Category> categoriesOfParent(CategoryKey parentKey);
}

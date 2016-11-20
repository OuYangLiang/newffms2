package com.personal.oyl.newffms.category.store.mapper;

import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.category.domain.Category;
import com.personal.oyl.newffms.category.domain.CategoryKey;

public interface CategoryMapper {
	List<Category> select(Category param);
	
	int insert(Category param);
	
	int delete(CategoryKey key);
	
	int changeCategoryDesc(Map<String, Object> param);
	
	int changeBudget(Map<String, Object> param);
}

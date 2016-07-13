package com.personal.oyl.newffms.category.store.mapper;

import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.category.domain.Category;
import com.personal.oyl.newffms.category.domain.CategoryKey;

public interface CategoryMapper {
	public List<Category> select(Category param);
	
	public int insert(Category param);
	
	public int delete(CategoryKey key);
	
	public int changeCategoryDesc(Map<String, Object> param);
	
	public int changeBudget(Map<String, Object> param);
}

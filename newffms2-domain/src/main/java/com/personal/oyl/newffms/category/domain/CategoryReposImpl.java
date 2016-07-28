package com.personal.oyl.newffms.category.domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.category.store.mapper.CategoryMapper;

public class CategoryReposImpl implements CategoryRepos {
	
	@Autowired
	private CategoryMapper mapper;

	@Override
	public Category categoryOfId(CategoryKey key) {
		if (null == key || null == key.getCategoryOid()) {
			throw new IllegalArgumentException();
		}
		
		Category param = new Category();
		param.setKey(key);
		
		List<Category> list = mapper.select(param);
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		return list.get(0);
	}

	@Override
	public void add(Category bean, String operator) {
		bean.setCreateBy(operator);
		bean.setCreateTime(new Date());
		
		mapper.insert(bean);
		bean.setSeqNo(1);
	}

	@Override
	public void remove(CategoryKey key, String operator) {
		if (null == key || null == key.getCategoryOid()) {
			throw new IllegalArgumentException();
		}
		
		Category obj = this.categoryOfId(key);
		
		if (null == obj || !obj.getIsLeaf()) {
			throw new IllegalStateException();
		}
		
		// check if current category has been used already.
		
		mapper.delete(key);
		
		CategoryKey parentKey = obj.getParentKey();
		
		while (null != parentKey) {
			Category parent = this.categoryOfId(parentKey);
			
			if (null == parent) {
				throw new IllegalStateException();
			}
			
			List<Category> children = this.categoriesOfParent(parent.getKey());
			
			if (null == children || children.isEmpty()) {
				parent.changeBudget(parent.getMonthlyBudget().subtract(obj.getMonthlyBudget()), true, operator);
			} else {
				parent.changeBudget(parent.getMonthlyBudget().subtract(obj.getMonthlyBudget()), null, operator);
			}
			
			parentKey = parent.getParentKey();
		}
		
	}

	@Override
	public List<Category> categoriesOfParent(CategoryKey parentKey) {
		if (null == parentKey || null == parentKey.getCategoryOid()) {
			throw new IllegalArgumentException();
		}
		
		Category param = new Category();
		param.setParentKey(parentKey);
		
		List<Category> list = mapper.select(param);
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		return list;
	}

}

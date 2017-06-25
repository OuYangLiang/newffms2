package com.personal.oyl.newffms.category.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetInvalidException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescTooLongException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryKeyEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotExistException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotLeafException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotRootException;
import com.personal.oyl.newffms.category.store.mapper.CategoryMapper;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;

public class CategoryReposImpl implements CategoryRepos {
	
	@Autowired
	private CategoryMapper mapper;

	@Override
	public Category categoryOfId(CategoryKey key) throws CategoryKeyEmptyException {
		if (null == key || null == key.getCategoryOid()) {
			throw new CategoryKeyEmptyException();
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
    public void add(Category bean, String operator)
            throws CategoryDescEmptyException, CategoryDescTooLongException, NoOperatorException,
            CategoryNotRootException, CategoryBudgetEmptyException, CategoryBudgetInvalidException {
 
	    if (null == bean || null == bean.getCategoryDesc() || bean.getCategoryDesc().trim().isEmpty()) {
            throw new CategoryDescEmptyException();
        }

        if (bean.getCategoryDesc().trim().length() > 10) {
            throw new CategoryDescTooLongException();
        }
        
        if (!bean.isLeaf() || (null != bean.getCategoryLevel() && bean.getCategoryLevel() != 1) ) {
            throw new CategoryNotRootException();
        }
        
        if (null == bean.getMonthlyBudget()) {
            throw new CategoryBudgetEmptyException();
        }
        
        if (bean.getMonthlyBudget().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CategoryBudgetInvalidException();
        }
	    
		if (null == operator || operator.trim().isEmpty()) {
			throw new NoOperatorException();
		}
		
		bean.setCategoryLevel(Integer.valueOf(1));
		bean.setCreateBy(operator);
		bean.setCreateTime(new Date());
		
		mapper.insert(bean);
		bean.setSeqNo(1);
	}

    @Override
    public void remove(CategoryKey key, String operator)
            throws CategoryKeyEmptyException, NoOperatorException, CategoryNotLeafException, CategoryNotExistException {
    	if (null == key || null == key.getCategoryOid()) {
			throw new CategoryKeyEmptyException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new NoOperatorException();
		}
		
		Category obj = this.categoryOfId(key);
		
		if (null == obj) {
			throw new CategoryException.CategoryNotExistException();
		}
		
		if (!obj.isLeaf()) {
			throw new CategoryNotLeafException();
		}
		
		//TODO check if current category has been used already.
		
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
	public List<Category> categoriesOfParent(CategoryKey parentKey) throws CategoryKeyEmptyException {
		if (null == parentKey || null == parentKey.getCategoryOid()) {
			throw new CategoryKeyEmptyException();
		}
		
		Category param = new Category();
		param.setParentKey(parentKey);
		
		List<Category> list = mapper.select(param);
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		return list;
	}

	@Override
	public List<Category> rootCategories() {
		Category param = new Category();
		param.setCategoryLevel(Integer.valueOf(1));
		
		List<Category> list = mapper.select(param);
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		return list;
	}

}

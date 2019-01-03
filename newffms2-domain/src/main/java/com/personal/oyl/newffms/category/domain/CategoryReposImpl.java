package com.personal.oyl.newffms.category.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetInvalidException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescTooLongException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryKeyEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotExistException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotLeafException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotRootException;
import com.personal.oyl.newffms.category.store.mapper.CategoryMapper;
import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void add(Category bean, String operator) throws CategoryDescEmptyException, CategoryDescTooLongException,
            NoOperatorException, CategoryNotRootException, CategoryBudgetEmptyException, CategoryBudgetInvalidException,
            NewffmsSystemException {

        if (null == bean || null == bean.getCategoryDesc() || bean.getCategoryDesc().trim().isEmpty()) {
            throw new CategoryDescEmptyException();
        }

        if (bean.getCategoryDesc().trim().length() > 10) {
            throw new CategoryDescTooLongException();
        }

        if (null == bean.getLeaf()) {
            bean.setLeaf(true);
        }

        if (null == bean.getCategoryLevel()) {
            bean.setCategoryLevel(Category.CATEGORY_ROOT_LEVEL);
        }

        if (!bean.getLeaf() || bean.getCategoryLevel() != 0) {
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

        bean.setCreateBy(operator);
        bean.setCreateTime(new Date());

        int n = mapper.insert(bean);

        if (1 != n) {
            throw new NewffmsSystemException();
        }

        bean.setSeqNo(1);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void remove(CategoryKey key, String operator) throws CategoryKeyEmptyException, NoOperatorException,
            CategoryNotLeafException, CategoryNotExistException, NewffmsSystemException {
        if (null == key || null == key.getCategoryOid()) {
            throw new CategoryKeyEmptyException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        Category obj = this.categoryOfId(key);

        if (null == obj) {
            throw new CategoryNotExistException();
        }

        if (!obj.getLeaf()) {
            throw new CategoryNotLeafException();
        }

        // TODO check if current category has been used already.

        int n = mapper.delete(key);

        if (1 != n) {
            throw new NewffmsSystemException();
        }

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
        param.setCategoryLevel(Category.CATEGORY_ROOT_LEVEL);

        List<Category> list = mapper.select(param);

        if (null == list || list.isEmpty()) {
            return null;
        }

        return list;
    }

    @Override
    public List<Category> allCategories() {
        List<Category> list = mapper.select(null);
        if (null == list || list.isEmpty()) {
            return null;
        }

        return list;
    }

    @Override
    public List<Category> rootCategoriesCascaded() {
        List<Category> list = this.allCategories();
        Map<BigDecimal, List<Category>> catMap = new HashMap<>();

        BigDecimal key = null;
        for (Category cat : list) {
            key = null == cat.getParentKey() ? BigDecimal.valueOf(-1) : cat.getParentKey().getCategoryOid();

            if (catMap.containsKey(key)) {
                List<Category> cList = catMap.get(key);
                cList.add(cat);
            } else {
                List<Category> cList = new ArrayList<>();
                cList.add(cat);
                catMap.put(key, cList);
            }
        }

        for (Category cat : list) {
            if (!cat.getLeaf()) {
                cat.setSubCategories(catMap.get(cat.getKey().getCategoryOid()));
            }
        }

        List<Category> rlt = new ArrayList<>();
        for (Category root : catMap.get(BigDecimal.valueOf(-1))) {
            rlt.add(root);
        }
        return rlt;
    }

    @Override
    public Map<BigDecimal, Category> allCategoriesById() {
        List<Category> list = this.allCategories();
        Map<BigDecimal, Category> rlt = new HashMap<>();
        for (Category item : list) {
            rlt.put(item.getKey().getCategoryOid(), item);
        }
        return rlt;
    }
    
    @Override
    public List<Category> allCategories(Set<BigDecimal> excludedRootCategories) {
        List<Category> excludedCategories = this.rootCategoriesCascaded();
        if (null != excludedRootCategories) {
            Iterator<Category> it = excludedCategories.iterator();
            while (it.hasNext()) {
                Category item = it.next();
                if (excludedRootCategories.contains(item.getKey().getCategoryOid())) {
                    it.remove();
                }
            }
        }

        List<Category> categoryList = new LinkedList<>();
        for (Category item : excludedCategories) {
            categoryList.addAll(item.toFlatList());
        }
        
        return categoryList;
    }

}

package com.personal.oyl.newffms.category.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryBudgetInvalidException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryDescTooLongException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryKeyEmptyException;
import com.personal.oyl.newffms.category.domain.CategoryException.CategoryNotLeafException;
import com.personal.oyl.newffms.category.store.mapper.CategoryMapper;
import com.personal.oyl.newffms.common.AppContext;
import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;

public class Category implements CategoryOperation, Serializable {
	private static final long serialVersionUID = 1L;
	
	private CategoryKey key;
	private String categoryDesc;
	private BigDecimal monthlyBudget;
	private Integer categoryLevel;
	private Boolean leaf;
	private CategoryKey parentKey;
	
	private Date createTime;
	private Date updateTime;
	private String createBy;
	private String updateBy;
	private Integer seqNo;
	
	public Category() {
		AppContext.getContext().getAutowireCapableBeanFactory().autowireBean(this);
	}
	
	@Autowired
	private CategoryRepos repos;
	@Autowired
	private CategoryMapper mapper;

	public CategoryKey getKey() {
		return key;
	}

	public void setKey(CategoryKey key) {
		this.key = key;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public BigDecimal getMonthlyBudget() {
		return monthlyBudget;
	}

	public void setMonthlyBudget(BigDecimal monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}

	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public CategoryKey getParentKey() {
		return parentKey;
	}

	public void setParentKey(CategoryKey parentKey) {
		this.parentKey = parentKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

    @Override
    public void changeCategoryDesc(String newDesc, String operator)
            throws CategoryDescEmptyException, CategoryDescTooLongException, NoOperatorException {
        if (null == newDesc || newDesc.trim().isEmpty()) {
            throw new CategoryDescEmptyException();
        }

        if (newDesc.trim().length() > 10) {
            throw new CategoryDescTooLongException();
        }
        
        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("seqNo", this.getSeqNo());
		param.put("categoryOid", this.getKey().getCategoryOid());
		param.put("updateBy", operator);
		param.put("categoryDesc", newDesc);
		
		int n = mapper.changeCategoryDesc(param);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setCategoryDesc(newDesc);
		this.setUpdateBy(operator);
	}
	
    @Override
    public Category addChild(String desc, BigDecimal budget, String operator)
            throws CategoryDescEmptyException, CategoryDescTooLongException, NoOperatorException,
            CategoryBudgetEmptyException, CategoryBudgetInvalidException, NewffmsSystemException {

	    if (null == desc || desc.trim().isEmpty()) {
            throw new CategoryDescEmptyException();
        }

        if (desc.trim().length() > 10) {
            throw new CategoryDescTooLongException();
        }
        
        if (null == budget) {
            throw new CategoryBudgetEmptyException();
        }
        
        if (budget.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CategoryBudgetInvalidException();
        }
        
        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }
        
        if (null == this.getLeaf()) {
            throw new NewffmsSystemException();
        }
	    
		if (this.getLeaf()) {
		    // TODO check if current category has been used already.
		}
		
		Category target = new Category();
		target.setCategoryDesc(desc);
		target.setMonthlyBudget(budget);
		target.setCategoryLevel(this.getCategoryLevel() + 1);
		target.setLeaf(true);
		target.setParentKey(new CategoryKey(this.getKey().getCategoryOid()));
		target.setCreateBy(operator);
		target.setCreateTime(new Date());
		
		mapper.insert(target);
		target.setSeqNo(1);
		
		try {
		    if (this.getLeaf()) {
	            this.changeBudget(budget, false, operator);
	        } else {
	            this.changeBudget(this.getMonthlyBudget().add(budget), false, operator);
	        }
        } catch (CategoryKeyEmptyException e) {
            throw new NewffmsSystemException();
        }
		
		return target;
	}
	
	@Override
    public void changeBudget(BigDecimal newBudget, String operator)
            throws CategoryNotLeafException, NoOperatorException, NewffmsSystemException {
	    if (null == this.getLeaf()) {
            throw new NewffmsSystemException();
        }
	    
		if (!this.getLeaf()) {
			throw new CategoryNotLeafException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }
		
		try {
            this.changeBudget(newBudget, null, operator);
        } catch (CategoryKeyEmptyException e) {
            throw new NewffmsSystemException();
        }
	}
	
    protected void changeBudget(BigDecimal newBudget, Boolean leaf, String operator) throws CategoryKeyEmptyException, NewffmsSystemException {
		if (null == newBudget || newBudget.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException();
		}
		
		BigDecimal oldBudget = this.getMonthlyBudget();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("seqNo", this.getSeqNo());
		param.put("categoryOid", this.getKey().getCategoryOid());
		param.put("updateBy", operator);
		param.put("monthlyBudget", newBudget);
		if (null != leaf) {
			param.put("leaf", leaf);
		}
		
		int n = mapper.changeBudget(param);
		
		if (1 != n) {
			throw new NewffmsSystemException();
		}
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setMonthlyBudget(newBudget);
		this.setUpdateBy(operator);
		if (null != leaf) {
			this.setLeaf(leaf);
		}
		
		if (null != this.getParentKey()) {
			Category parent = repos.categoryOfId(this.getParentKey());
			if (null == parent) {
				throw new IllegalStateException();
			}
			
			BigDecimal changement = newBudget.subtract(oldBudget);
			parent.changeBudget(changement.add(parent.getMonthlyBudget()), null, operator);
		}
	}

}

package com.personal.oyl.newffms.category.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.category.store.mapper.CategoryMapper;
import com.personal.oyl.newffms.common.AppContext;

public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private CategoryKey key;
	private String categoryDesc;
	private BigDecimal monthlyBudget;
	private Integer categoryLevel;
	private Boolean isLeaf;
	private BigDecimal parentOid;
	
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

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public BigDecimal getParentOid() {
		return parentOid;
	}

	public void setParentOid(BigDecimal parentOid) {
		this.parentOid = parentOid;
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

	public void changeCategoryDesc(String newDesc, String operator) {
		if (null == newDesc || newDesc.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		this.setCategoryDesc(newDesc);
		
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
	}
	
	public Category addChild(String desc, BigDecimal budget, String operator) {
		if (this.getIsLeaf()) {
			// check if current category has been used already.
		}
		
		Category target = new Category();
		target.setCategoryDesc(desc);
		target.setMonthlyBudget(budget);
		target.setCategoryLevel(this.getCategoryLevel() + 1);
		target.setIsLeaf(true);
		target.setParentOid(this.getKey().getCategoryOid());
		target.setCreateBy(operator);
		target.setCreateTime(new Date());
		
		mapper.insert(target);
		target.setSeqNo(1);
		
		if (this.getIsLeaf()) {
			this.changeBudget(budget, false, operator);
		} else {
			this.changeBudget(this.getMonthlyBudget().add(budget), false, operator);
		}
		
		return target;
	}
	
	public void changeBudget(BigDecimal newBudget, String operator) {
		if (!this.getIsLeaf()) {
			throw new IllegalStateException();
		}
		
		this.changeBudget(newBudget, null, operator);
	}
	
	protected void changeBudget(BigDecimal newBudget, Boolean isLeaf, String operator) {
		if (null == newBudget || newBudget.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException();
		}
		
		BigDecimal oldBudget = this.getMonthlyBudget();
		
		this.setMonthlyBudget(newBudget);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("seqNo", this.getSeqNo());
		param.put("categoryOid", this.getKey().getCategoryOid());
		param.put("updateBy", operator);
		param.put("monthlyBudget", newBudget);
		if (null != isLeaf) {
			param.put("isLeaf", isLeaf);
		}
		
		int n = mapper.changeBudget(param);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		this.setSeqNo(this.getSeqNo() + 1);
		
		if (null != this.getParentOid()) {
			Category parent = repos.categoryOfId(new CategoryKey(this.getParentOid()));
			if (null == parent) {
				throw new IllegalStateException();
			}
			
			BigDecimal changement = newBudget.subtract(oldBudget);
			parent.changeBudget(changement.add(parent.getMonthlyBudget()), null, operator);
		}
	}

}

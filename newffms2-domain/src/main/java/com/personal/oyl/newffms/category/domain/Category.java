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
	private boolean leaf;
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

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
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

	/**
	 * 变更当前类别名称
	 * 
	 * @param newDesc 新的名称
	 * @param operator 操作人
	 */
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
	
	/**
	 * 创建当前类别的子类别
	 * 
	 * @param desc 子类别名称
	 * @param budget 月度预算	
	 * @param operator 操作人
	 * @return
	 */
	public Category addChild(String desc, BigDecimal budget, String operator) {
		if (this.isLeaf()) {
			// check if current category has been used already.
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
		
		if (this.isLeaf()) {
			this.changeBudget(budget, false, operator);
		} else {
			this.changeBudget(this.getMonthlyBudget().add(budget), false, operator);
		}
		
		return target;
	}
	
	/**
	 * 变更当前类别的月度预算（当前类别必须为叶子类别）
	 * 
	 * @param newBudget 新的预算
	 * @param operator 操作人
	 */
	public void changeBudget(BigDecimal newBudget, String operator) {
		if (!this.isLeaf()) {
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

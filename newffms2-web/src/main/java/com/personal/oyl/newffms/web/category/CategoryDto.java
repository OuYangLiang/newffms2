package com.personal.oyl.newffms.web.category;

import java.math.BigDecimal;
import java.util.List;

import com.personal.oyl.newffms.category.domain.Category;

public class CategoryDto {
    private BigDecimal categoryOid;
    private String categoryDesc;
    private BigDecimal monthlyBudget;
    private Integer categoryLevel;
    private Boolean leaf;
    private BigDecimal parentOid;
    private CategoryDto parent;
    private List<CategoryDto> subCategories;
    
    public CategoryDto(Category category) {
        this.setCategoryOid(category.getKey().getCategoryOid());
        this.setCategoryDesc(category.getCategoryDesc());
        this.setMonthlyBudget(category.getMonthlyBudget());
        this.setCategoryLevel(category.getCategoryLevel());
        this.setLeaf(category.getLeaf());
        this.setParentOid(null == category.getParentKey() ? null : category.getParentKey().getCategoryOid());
    }

    public BigDecimal getCategoryOid() {
        return categoryOid;
    }

    public void setCategoryOid(BigDecimal categoryOid) {
        this.categoryOid = categoryOid;
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

    public BigDecimal getParentOid() {
        return parentOid;
    }

    public void setParentOid(BigDecimal parentOid) {
        this.parentOid = parentOid;
    }

    public CategoryDto getParent() {
        return parent;
    }

    public void setParent(CategoryDto parent) {
        this.parent = parent;
    }

    public List<CategoryDto> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryDto> subCategories) {
        this.subCategories = subCategories;
    }
}

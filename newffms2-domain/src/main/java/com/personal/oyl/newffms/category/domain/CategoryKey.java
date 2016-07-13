package com.personal.oyl.newffms.category.domain;

import java.math.BigDecimal;

public class CategoryKey {
	private BigDecimal categoryOid;
	
	public CategoryKey() {
		
	}
	
	public CategoryKey(BigDecimal categoryOid) {
		this.setCategoryOid(categoryOid);
	}

	public BigDecimal getCategoryOid() {
		return categoryOid;
	}

	public void setCategoryOid(BigDecimal categoryOid) {
		if (null != this.categoryOid) {
			throw new IllegalStateException();
		}
		
		this.categoryOid = categoryOid;
	}
}

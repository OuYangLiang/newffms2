package com.personal.oyl.newffms.account.domain;

import java.math.BigDecimal;

public class AccountKey {
	private BigDecimal acntOid;

	public AccountKey() {
		
	}

	public AccountKey(BigDecimal acntOid) {
		super();
		this.setAcntOid(acntOid);
	}
	
	public BigDecimal getAcntOid() {
		return acntOid;
	}
	
	public void setAcntOid(BigDecimal acntOid) {
		if (null != this.acntOid) {
			throw new IllegalStateException();
		}
		
		this.acntOid = acntOid;
	}
}

package com.personal.oyl.newffms.account.domain;

import java.math.BigDecimal;

public class AccountAuditKey {
	private BigDecimal adtOid;
	
	public AccountAuditKey() {
		
	}
	
	public AccountAuditKey(BigDecimal adtOid) {
		super();
		this.setAdtOid(adtOid);
	}

	public BigDecimal getAdtOid() {
		return adtOid;
	}

	public void setAdtOid(BigDecimal adtOid) {
		if (null != this.adtOid) {
			throw new IllegalStateException();
		}
		
		this.adtOid = adtOid;
	}

}

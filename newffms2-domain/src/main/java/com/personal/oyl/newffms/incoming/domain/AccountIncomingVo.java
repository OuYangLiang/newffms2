package com.personal.oyl.newffms.incoming.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountIncomingVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal acntOid;
	private BigDecimal incomingOid;

	public BigDecimal getAcntOid() {
		return acntOid;
	}

	public void setAcntOid(BigDecimal acntOid) {
		this.acntOid = acntOid;
	}

	public BigDecimal getIncomingOid() {
		return incomingOid;
	}

	public void setIncomingOid(BigDecimal incomingOid) {
		this.incomingOid = incomingOid;
	}

}

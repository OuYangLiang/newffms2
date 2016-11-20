package com.personal.oyl.newffms.incoming.domain;

import java.math.BigDecimal;

public class IncomingKey {
	private BigDecimal incomingOid;
	
	public IncomingKey(BigDecimal incomingOid) {
		super();
		this.setIncomingOid(incomingOid);
	}

	public BigDecimal getIncomingOid() {
		return incomingOid;
	}

	public void setIncomingOid(BigDecimal incomingOid) {
		if (null != this.incomingOid) {
			throw new IllegalStateException();
		}
		
		this.incomingOid = incomingOid;
	}
	
}

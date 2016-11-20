package com.personal.oyl.newffms.consumption.domain;

import java.math.BigDecimal;

public class ConsumptionKey {
	private BigDecimal cpnOid;
	
	public ConsumptionKey() {
		
	}

	public ConsumptionKey(BigDecimal cpnOid) {
		super();
		this.setCpnOid(cpnOid);
	}

	public BigDecimal getCpnOid() {
		return cpnOid;
	}

	public void setCpnOid(BigDecimal cpnOid) {
		if (null != this.cpnOid) {
			throw new IllegalStateException();
		}
		
		this.cpnOid = cpnOid;
	}
}

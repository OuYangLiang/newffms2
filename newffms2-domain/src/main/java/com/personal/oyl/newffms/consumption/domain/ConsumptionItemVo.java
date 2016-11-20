package com.personal.oyl.newffms.consumption.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConsumptionItemVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal itemOid;
	private String itemDesc;
	private BigDecimal amount;
	private BigDecimal ownerOid;
	private BigDecimal categoryOid;
	private BigDecimal cpnOid;

	public BigDecimal getItemOid() {
		return itemOid;
	}

	public void setItemOid(BigDecimal itemOid) {
		this.itemOid = itemOid;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getOwnerOid() {
		return ownerOid;
	}

	public void setOwnerOid(BigDecimal ownerOid) {
		this.ownerOid = ownerOid;
	}

	public BigDecimal getCategoryOid() {
		return categoryOid;
	}

	public void setCategoryOid(BigDecimal categoryOid) {
		this.categoryOid = categoryOid;
	}

	public BigDecimal getCpnOid() {
		return cpnOid;
	}

	public void setCpnOid(BigDecimal cpnOid) {
		this.cpnOid = cpnOid;
	}

}

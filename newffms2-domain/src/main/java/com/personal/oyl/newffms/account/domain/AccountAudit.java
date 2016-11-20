package com.personal.oyl.newffms.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountAudit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private BigDecimal key;
	private String adtDesc;
	private AccountAuditType adtType;
	private Date adtTime;
	private BigDecimal amount;
	private Boolean confirmed;
	private BigDecimal acntOid;
	private BigDecimal refAcntOid;
	private BigDecimal incomingOid;
	private BigDecimal cpnOid;

	private Date createTime;
	private String createBy;

	public BigDecimal getKey() {
		return key;
	}

	public void setKey(BigDecimal key) {
		this.key = key;
	}

	public String getAdtDesc() {
		return adtDesc;
	}

	public void setAdtDesc(String adtDesc) {
		this.adtDesc = adtDesc;
	}

	public AccountAuditType getAdtType() {
		return adtType;
	}

	public void setAdtType(AccountAuditType adtType) {
		this.adtType = adtType;
	}

	public Date getAdtTime() {
		return adtTime;
	}

	public void setAdtTime(Date adtTime) {
		this.adtTime = adtTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public BigDecimal getAcntOid() {
		return acntOid;
	}

	public void setAcntOid(BigDecimal acntOid) {
		this.acntOid = acntOid;
	}

	public BigDecimal getRefAcntOid() {
		return refAcntOid;
	}

	public void setRefAcntOid(BigDecimal refAcntOid) {
		this.refAcntOid = refAcntOid;
	}

	public BigDecimal getIncomingOid() {
		return incomingOid;
	}

	public void setIncomingOid(BigDecimal incomingOid) {
		this.incomingOid = incomingOid;
	}

	public BigDecimal getCpnOid() {
		return cpnOid;
	}

	public void setCpnOid(BigDecimal cpnOid) {
		this.cpnOid = cpnOid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}

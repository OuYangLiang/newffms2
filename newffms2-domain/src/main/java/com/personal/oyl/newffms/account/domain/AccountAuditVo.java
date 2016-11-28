package com.personal.oyl.newffms.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountAuditVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal adtOid;
	private String adtDesc;
	private AccountAuditType adtType;
	private Date adtTime;
	private BigDecimal balanceAfter;
	private BigDecimal chgAmt;
	private BigDecimal acntOid;
	private String batchNum;
	private Date createTime;
	private String createBy;

	public BigDecimal getAdtOid() {
		return adtOid;
	}

	public void setAdtOid(BigDecimal adtOid) {
		this.adtOid = adtOid;
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

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public BigDecimal getChgAmt() {
		return chgAmt;
	}

	public void setChgAmt(BigDecimal chgAmt) {
		this.chgAmt = chgAmt;
	}

	public BigDecimal getAcntOid() {
		return acntOid;
	}

	public void setAcntOid(BigDecimal acntOid) {
		this.acntOid = acntOid;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
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

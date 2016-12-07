package com.personal.oyl.newffms.incoming.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.personal.oyl.newffms.common.AppContext;

public class Incoming implements Serializable {
	private static final long serialVersionUID = 1L;

	private IncomingKey key;
	private String incomingDesc;
	private BigDecimal amount;
	private IncomingType incomingType;
	private Boolean confirmed;
	private BigDecimal ownerOid;
	private Date incomingDate;
	private String batchNum;

	private Date createTime;
	private Date updateTime;
	private String createBy;
	private String updateBy;
	private Integer seqNo;
	
	private AccountIncomingVo acntRel;
	
	public Incoming() {
		AppContext.getContext().getAutowireCapableBeanFactory().autowireBean(this);
	}

	public IncomingKey getKey() {
		return key;
	}

	public void setKey(IncomingKey key) {
		this.key = key;
	}

	public String getIncomingDesc() {
		return incomingDesc;
	}

	public void setIncomingDesc(String incomingDesc) {
		this.incomingDesc = incomingDesc;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public IncomingType getIncomingType() {
		return incomingType;
	}

	public void setIncomingType(IncomingType incomingType) {
		this.incomingType = incomingType;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public BigDecimal getOwnerOid() {
		return ownerOid;
	}

	public void setOwnerOid(BigDecimal ownerOid) {
		this.ownerOid = ownerOid;
	}

	public Date getIncomingDate() {
		return incomingDate;
	}

	public void setIncomingDate(Date incomingDate) {
		this.incomingDate = incomingDate;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public AccountIncomingVo getAcntRel() {
		return acntRel;
	}

	public void setAcntRel(AccountIncomingVo acntRel) {
		this.acntRel = acntRel;
	}

}

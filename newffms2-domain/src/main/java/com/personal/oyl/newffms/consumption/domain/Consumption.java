package com.personal.oyl.newffms.consumption.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.personal.oyl.newffms.common.AppContext;

public class Consumption implements Serializable {
	private static final long serialVersionUID = 1L;

	private ConsumptionKey key;
	private ConsumptionType cpnType;
	private BigDecimal amount;
	private Date cpnTime;
	private Boolean confirmed;

	private Date createTime;
	private Date updateTime;
	private String createBy;
	private String updateBy;
	private Integer seqNo;

	public Consumption() {
		AppContext.getContext().getAutowireCapableBeanFactory().autowireBean(this);
	}

	public ConsumptionKey getKey() {
		return key;
	}

	public void setKey(ConsumptionKey key) {
		this.key = key;
	}

	public ConsumptionType getCpnType() {
		return cpnType;
	}

	public void setCpnType(ConsumptionType cpnType) {
		this.cpnType = cpnType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCpnTime() {
		return cpnTime;
	}

	public void setCpnTime(Date cpnTime) {
		this.cpnTime = cpnTime;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
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

}

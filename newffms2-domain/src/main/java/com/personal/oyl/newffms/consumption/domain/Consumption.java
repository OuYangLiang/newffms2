package com.personal.oyl.newffms.consumption.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountKey;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.common.AppContext;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionMapper;

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
	
	private List<ConsumptionItemVo> items;
	private List<AccountConsumptionVo> payments;
	
	@Autowired
	private ConsumptionMapper mapper;
	@Autowired
	private AccountRepos acntRepos;

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

	public List<ConsumptionItemVo> getItems() {
		return items;
	}

	public void setItems(List<ConsumptionItemVo> items) {
		this.items = items;
	}

	public List<AccountConsumptionVo> getPayments() {
		return payments;
	}

	public void setPayments(List<AccountConsumptionVo> payments) {
		this.payments = payments;
	}

	public void confirm(String operator) {
		Date now = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("seqNo", this.getSeqNo());
		param.put("cpnOid", this.getKey().getCpnOid());
		param.put("updateBy", operator);
		param.put("updateTime", now);
		param.put("confirmed", true);
		
		int n = mapper.confirm(param);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		for (AccountConsumptionVo payment: this.getPayments()) {
			Account acnt = acntRepos.accountOfId(new AccountKey(payment.getAcntOid()));
			acnt.subtract(payment.getAmount(), null, this.getCpnTime(), this.getKey().getCpnOid(), operator);
		}
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setUpdateBy(operator);
		this.setUpdateTime(now);
		this.setConfirmed(true);
	}
}

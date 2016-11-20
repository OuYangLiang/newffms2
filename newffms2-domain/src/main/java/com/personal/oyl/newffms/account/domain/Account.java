package com.personal.oyl.newffms.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.account.store.mapper.AccountMapper;
import com.personal.oyl.newffms.common.AppContext;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	private AccountKey key;
	private String acntDesc;
	private AccountType acntType;
	private BigDecimal balance;
	private BigDecimal quota;
	private BigDecimal debt;
	private BigDecimal ownerOid;

	private Date createTime;
	private Date updateTime;
	private String createBy;
	private String updateBy;
	private Integer seqNo;
	
	@Autowired
	private AccountMapper mapper;
	
	public Account() {
		AppContext.getContext().getAutowireCapableBeanFactory().autowireBean(this);
	}

	public AccountKey getKey() {
		return key;
	}

	public void setKey(AccountKey key) {
		this.key = key;
	}

	public String getAcntDesc() {
		return acntDesc;
	}

	public void setAcntDesc(String acntDesc) {
		this.acntDesc = acntDesc;
	}

	public AccountType getAcntType() {
		return acntType;
	}

	public void setAcntType(AccountType acntType) {
		this.acntType = acntType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getQuota() {
		return quota;
	}

	public void setQuota(BigDecimal quota) {
		this.quota = quota;
	}

	public BigDecimal getDebt() {
		return debt;
	}

	public void setDebt(BigDecimal debt) {
		this.debt = debt;
	}

	public BigDecimal getOwnerOid() {
		return ownerOid;
	}

	public void setOwnerOid(BigDecimal ownerOid) {
		this.ownerOid = ownerOid;
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
	
	/**
	 * @param newDesc
	 * @param operator
	 */
	public void changeDesc(String newDesc, String operator) {
		if (null == newDesc || newDesc.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("seqNo", this.getSeqNo());
		param.put("acntOid", this.getKey().getAcntOid());
		param.put("updateBy", operator);
		param.put("acntDesc", newDesc);
		
		int n = mapper.changeAccountDesc(param);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setAcntDesc(newDesc);
		this.setUpdateBy(operator);
	}
}

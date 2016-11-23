package com.personal.oyl.newffms.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;
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
	@Autowired
	private AccountAuditMapper auditMapper;
	
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
	 * 变更账户描述
	 * 
	 * @param newDesc
	 * @param operator
	 */
	public void changeDesc(String newDesc, String operator) {
		if (null == newDesc || newDesc.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
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
	
	/**
	 * 消费扣减
	 * 
	 * @param amount 扣减金额
	 * @param desc 描述
	 * @param eventTime 事件发生时间
	 * @param cpnOid 消费id
	 * @param operator 操作人
	 */
	public void subtract(BigDecimal amount, String desc, Date eventTime, BigDecimal cpnOid, String operator) {
		if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0) {
			throw new IllegalArgumentException();
		}
		
		if (null == desc || desc.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Date now = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("seqNo", this.getSeqNo());
		param.put("acntOid", this.getKey().getAcntOid());
		param.put("updateBy", operator);
		param.put("updateTime", now);
		param.put("balance", this.getBalance().subtract(amount));
		if (AccountType.Creditcard.equals(this.getAcntType())) {
			param.put("debt", this.getDebt().add(amount));
		}
		
		int n = mapper.updateBalance(param);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		AccountAuditVo audit = new AccountAuditVo();
		audit.setAdtDesc(desc);
		audit.setAdtTime(eventTime);
		audit.setAdtType(AccountAuditType.Subtract);
		audit.setAmount(amount);
		audit.setConfirmed(true);
		audit.setAcntOid(this.getKey().getAcntOid());
		audit.setRefAcntOid(null);
		audit.setIncomingOid(null);
		audit.setCpnOid(cpnOid);
		audit.setCreateBy(operator);
		audit.setCreateTime(now);
		
		n = auditMapper.insert(audit);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setUpdateBy(operator);
		this.setUpdateTime(now);
		this.setBalance(this.getBalance().subtract(amount));
		if (AccountType.Creditcard.equals(this.getAcntType())) {
			this.setDebt(this.debt.add(amount));
		}
	}
	
	/**
	 * 账户增加
	 * 
	 * @param amount 增加金额
	 * @param desc 描述
	 * @param eventTime 事件发生时间
	 * @param incomingOid 收入id
	 * @param operator 操作人
	 */
	public void increase(BigDecimal amount, String desc, Date eventTime, BigDecimal incomingOid, String operator) {
		if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0) {
			throw new IllegalArgumentException();
		}
		
		if (null == desc || desc.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Date now = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("seqNo", this.getSeqNo());
		param.put("acntOid", this.getKey().getAcntOid());
		param.put("updateBy", operator);
		param.put("updateTime", now);
		param.put("balance", this.getBalance().add(amount));
		if (AccountType.Creditcard.equals(this.getAcntType())) {
			param.put("debt", this.getDebt().subtract(amount));
		}
		
		int n = mapper.updateBalance(param);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		AccountAuditVo audit = new AccountAuditVo();
		audit.setAdtDesc(desc);
		audit.setAdtTime(eventTime);
		audit.setAdtType(AccountAuditType.Add);
		audit.setAmount(amount);
		audit.setConfirmed(true);
		audit.setAcntOid(this.getKey().getAcntOid());
		audit.setRefAcntOid(null);
		audit.setIncomingOid(incomingOid);
		audit.setCpnOid(null);
		audit.setCreateBy(operator);
		audit.setCreateTime(now);
		
		n = auditMapper.insert(audit);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setUpdateBy(operator);
		this.setUpdateTime(now);
		this.setBalance(this.getBalance().add(amount));
		if (AccountType.Creditcard.equals(this.getAcntType())) {
			this.setDebt(this.debt.subtract(amount));
		}
	}
		
	/**
	 * 账户转账
	 * 
	 * @param target 目标账户
	 * @param amount 增加金额
	 * @param operator 操作人
	 */
	public void transfer(Account target, BigDecimal amount, String operator) {
		if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0) {
			throw new IllegalArgumentException();
		}

		if (this.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException();
		}

		if (null == operator || operator.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		Date now = new Date();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("seqNo", this.getSeqNo());
		param.put("acntOid", this.getKey().getAcntOid());
		param.put("updateBy", operator);
		param.put("updateTime", now);
		param.put("balance", this.getBalance().subtract(amount));
		if (AccountType.Creditcard.equals(this.getAcntType())) {
			param.put("debt", this.getDebt().add(amount));
		}

		int n = mapper.updateBalance(param);

		if (1 != n) {
			throw new IllegalStateException();
		}

		AccountAuditVo audit = new AccountAuditVo();
		audit.setAdtDesc("转账至：" + target.getAcntDesc());// TODO
		audit.setAdtTime(now);
		audit.setAdtType(AccountAuditType.Subtract);
		audit.setAmount(amount);
		audit.setConfirmed(true);
		audit.setAcntOid(this.getKey().getAcntOid());
		audit.setRefAcntOid(target.getKey().getAcntOid());
		audit.setIncomingOid(null);
		audit.setCpnOid(null);
		audit.setCreateBy(operator);
		audit.setCreateTime(now);

		n = auditMapper.insert(audit);

		if (1 != n) {
			throw new IllegalStateException();
		}
		
		target.transFrom(this, amount, operator, now);

		this.setSeqNo(this.getSeqNo() + 1);
		this.setUpdateBy(operator);
		this.setUpdateTime(now);
		this.setBalance(this.getBalance().subtract(amount));
		if (AccountType.Creditcard.equals(this.getAcntType())) {
			this.setDebt(this.debt.add(amount));
		}
	}
	
	private void transFrom(Account srcObj, BigDecimal amount, String operator, Date eventTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("seqNo", this.getSeqNo());
		param.put("acntOid", this.getKey().getAcntOid());
		param.put("updateBy", operator);
		param.put("updateTime", eventTime);
		param.put("balance", this.getBalance().add(amount));
		if (AccountType.Creditcard.equals(this.getAcntType())) {
			param.put("debt", this.getDebt().subtract(amount));
		}
		
		int n = mapper.updateBalance(param);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		AccountAuditVo audit = new AccountAuditVo();
		audit.setAdtDesc("进账自：" + srcObj.getAcntDesc());// TODO
		audit.setAdtTime(eventTime);
		audit.setAdtType(AccountAuditType.Add);
		audit.setAmount(amount);
		audit.setConfirmed(true);
		audit.setAcntOid(this.getKey().getAcntOid());
		audit.setRefAcntOid(srcObj.getKey().getAcntOid());
		audit.setIncomingOid(null);
		audit.setCpnOid(null);
		audit.setCreateBy(operator);
		audit.setCreateTime(eventTime);
		
		n = auditMapper.insert(audit);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setUpdateBy(operator);
		this.setUpdateTime(eventTime);
		this.setBalance(this.getBalance().add(amount));
		if (AccountType.Creditcard.equals(this.getAcntType())) {
			this.setDebt(this.debt.subtract(amount));
		}
	}
}

package com.personal.oyl.newffms.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.personal.oyl.newffms.account.domain.AccountException.AccountDescEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDescTooLongException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountOperationDescException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountTransferToSelfException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountTypeInvalidException;
import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;
import com.personal.oyl.newffms.account.store.mapper.AccountMapper;
import com.personal.oyl.newffms.common.AppContext;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.common.Util;
import com.personal.oyl.newffms.user.domain.UserKey;
import com.personal.oyl.newffms.account.domain.AccountException.AccountAmountInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceInsufficiencyException;

public class Account implements AccountOperation, Serializable {

    private static final long serialVersionUID = 1100828800834047867L;
    private AccountKey key;
    private String acntDesc;
    private AccountType acntType;
    private BigDecimal balance;
    private BigDecimal quota;
    private BigDecimal debt;
    private UserKey owner;
    private Boolean disabled;

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

    public AccountOperation getProxy() {
        return (AccountOperation) AppContext.getContext().getAutowireCapableBeanFactory()
                .applyBeanPostProcessorsAfterInitialization(this, "Account");
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

    public UserKey getOwner() {
        return owner;
    }

    public void setOwner(UserKey owner) {
        this.owner = owner;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void changeDesc(String newDesc, String operator)
            throws AccountDescEmptyException, NoOperatorException, AccountDescTooLongException {
        if (null == newDesc || newDesc.trim().isEmpty()) {
            throw new AccountDescEmptyException();
        }

        if (newDesc.trim().length() > 30) {
            throw new AccountDescTooLongException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void subtract(BigDecimal amount, String desc, String batchNum, Date eventTime, String operator)
            throws AccountAmountInvalidException, AccountBalanceInsufficiencyException, AccountOperationDescException,
            NoOperatorException {
        if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new AccountAmountInvalidException();
        }

        if (this.getBalance().compareTo(amount) < 0) {
            throw new AccountBalanceInsufficiencyException();
        }

        if (null == desc || desc.trim().isEmpty()) {
            throw new AccountException.AccountOperationDescException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        this.subtract(amount, desc, batchNum, eventTime, new Date(), operator, AccountAuditType.subtract);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void increase(BigDecimal amount, String desc, String batchNum, Date eventTime, String operator)
            throws AccountAmountInvalidException, AccountOperationDescException, NoOperatorException {
        if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new AccountAmountInvalidException();
        }

        if (null == desc || desc.trim().isEmpty()) {
            throw new AccountOperationDescException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        this.increase(amount, desc, batchNum, eventTime, new Date(), operator, AccountAuditType.add);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void transfer(Account target, BigDecimal amount, String operator) throws AccountAmountInvalidException,
            AccountBalanceInsufficiencyException, NoOperatorException, AccountTransferToSelfException {
        if (null == amount || BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new AccountAmountInvalidException();
        }

        if (this.getBalance().compareTo(amount) < 0) {
            throw new AccountBalanceInsufficiencyException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        if (this.getKey().getAcntOid().compareTo(target.getKey().getAcntOid()) == 0) {
            throw new AccountException.AccountTransferToSelfException();
        }

        Date now = new Date();
        String batchNum = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        this.subtract(amount, "转账至：" + target.getAcntDesc(), batchNum, now, now, operator,
                AccountAuditType.trans_subtract);
        target.increase(amount, "进账自：" + this.getAcntDesc(), batchNum, now, now, operator, AccountAuditType.trans_add);
    }

    private void subtract(BigDecimal amount, String desc, String batchNum, Date eventTime, Date now, String operator,
            AccountAuditType auditType) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("seqNo", this.getSeqNo());
        param.put("acntOid", this.getKey().getAcntOid());
        param.put("updateBy", operator);
        param.put("updateTime", now);
        param.put("balance", this.getBalance().subtract(amount));
        if (AccountType.creditcard.equals(this.getAcntType())) {
            param.put("debt", this.getDebt().add(amount));
        }

        int n = mapper.updateBalance(param);

        if (1 != n) {
            throw new IllegalStateException();
        }

        AccountAuditVo audit = new AccountAuditVo();
        audit.setAdtDesc(desc);
        audit.setAdtTime(eventTime);
        audit.setAdtType(auditType);
        audit.setBalanceAfter(this.getBalance().subtract(amount));
        audit.setChgAmt(amount.negate());
        audit.setAcntOid(this.getKey().getAcntOid());
        audit.setBatchNum(batchNum);
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
        if (AccountType.creditcard.equals(this.getAcntType())) {
            this.setDebt(this.debt.add(amount));
        }
    }

    private void increase(BigDecimal amount, String desc, String batchNum, Date eventTime, Date now, String operator,
            AccountAuditType auditType) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("seqNo", this.getSeqNo());
        param.put("acntOid", this.getKey().getAcntOid());
        param.put("updateBy", operator);
        param.put("updateTime", now);
        param.put("balance", this.getBalance().add(amount));
        if (AccountType.creditcard.equals(this.getAcntType())) {
            param.put("debt", this.getDebt().subtract(amount));
        }

        int n = mapper.updateBalance(param);

        if (1 != n) {
            throw new IllegalStateException();
        }

        AccountAuditVo audit = new AccountAuditVo();
        audit.setAdtDesc(desc);
        audit.setAdtTime(eventTime);
        audit.setAdtType(auditType);
        audit.setBalanceAfter(this.getBalance().add(amount));
        audit.setChgAmt(amount);
        audit.setAcntOid(this.getKey().getAcntOid());
        audit.setBatchNum(batchNum);
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
        if (AccountType.creditcard.equals(this.getAcntType())) {
            this.setDebt(this.debt.subtract(amount));
        }
    }

    void rollback(BigDecimal chgAmt, String operator) {
        Date now = new Date();

        BigDecimal newBalance = this.getBalance().add(chgAmt);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException();
        }

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("seqNo", this.getSeqNo());
        param.put("acntOid", this.getKey().getAcntOid());
        param.put("updateBy", operator);
        param.put("updateTime", now);
        param.put("balance", newBalance);
        if (AccountType.creditcard.equals(this.getAcntType())) {
            param.put("debt", this.getDebt().subtract(chgAmt));
        }

        int n = mapper.updateBalance(param);

        if (1 != n) {
            throw new IllegalStateException();
        }

        this.setSeqNo(this.getSeqNo() + 1);
        this.setUpdateBy(operator);
        this.setUpdateTime(now);
        this.setBalance(newBalance);
        if (AccountType.creditcard.equals(this.getAcntType())) {
            this.setDebt(this.debt.subtract(chgAmt));
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void adjustQuota(BigDecimal change, String operator)
            throws AccountAmountInvalidException, NoOperatorException, AccountTypeInvalidException, AccountBalanceInsufficiencyException {
        if (null == change || BigDecimal.ZERO.compareTo(change) == 0) {
            throw new AccountAmountInvalidException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }
        
        if (!AccountType.creditcard.equals(this.getAcntType())) {
            throw new AccountException.AccountTypeInvalidException();
        }
        
        if (this.getBalance().compareTo(change) < 0) {
            throw new AccountBalanceInsufficiencyException();
        }
        
        Date now = new Date();

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("seqNo", this.getSeqNo());
        param.put("acntOid", this.getKey().getAcntOid());
        param.put("updateBy", operator);
        param.put("updateTime", now);
        param.put("balance", this.getBalance().add(change));
        param.put("quota", this.getQuota().add(change));
        
        int n = mapper.increseQuota(param);

        if (1 != n) {
            throw new IllegalStateException();
        }

        AccountAuditVo audit = new AccountAuditVo();
        audit.setAdtDesc("调整限额，原始限额：" + this.getQuota() + "，现限额：" + param.get("quota"));
        audit.setAdtTime(now);
        audit.setAdtType(AccountAuditType.change);
        audit.setBalanceAfter(this.getBalance().add(change));
        audit.setChgAmt(change);
        audit.setAcntOid(this.getKey().getAcntOid());
        audit.setBatchNum(Util.getInstance().generateBatchNum());
        audit.setCreateBy(operator);
        audit.setCreateTime(now);

        n = auditMapper.insert(audit);

        if (1 != n) {
            throw new IllegalStateException();
        }

        this.setSeqNo(this.getSeqNo() + 1);
        this.setUpdateBy(operator);
        this.setUpdateTime(now);
        this.setBalance(this.getBalance().add(change));
        this.setQuota(this.getQuota().add(change));
    }

}

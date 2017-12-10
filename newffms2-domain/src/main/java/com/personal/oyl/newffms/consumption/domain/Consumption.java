package com.personal.oyl.newffms.consumption.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceInsufficiencyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.account.domain.AccountKey;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.account.domain.AccountService;
import com.personal.oyl.newffms.common.AppContext;
import com.personal.oyl.newffms.common.NewffmsDomainException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionAlreadyConfirmedException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionAmountNotMatchException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionBatchNumEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemAmountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemAmountInvalidException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemCategoryEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemDescEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemOwnerEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemsEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionNotConfirmedException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAccountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAmountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAmountInvalidException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentsEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionTimeEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionTypeEmptyException;
import com.personal.oyl.newffms.consumption.store.mapper.AccountConsumptionMapper;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionItemMapper;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionMapper;

public class Consumption implements ConsumptionOperation, Serializable {
    private static final long serialVersionUID = 1L;

    private ConsumptionKey key;
    private ConsumptionType cpnType;
    private BigDecimal amount;
    private Date cpnTime;
    private String batchNum;
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
    @Autowired
    private AccountService acntService;
    @Autowired
    private ConsumptionItemMapper itemMapper;
    @Autowired
    private AccountConsumptionMapper paymentMapper;

    public Consumption() {
        AppContext.getContext().getAutowireCapableBeanFactory().autowireBean(this);
    }
    
    public ConsumptionOperation getProxy() {
        return (ConsumptionOperation) AppContext.getContext().getAutowireCapableBeanFactory()
                .applyBeanPostProcessorsAfterInitialization(this, "Consumption");
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

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
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
        this.setAmount(BigDecimal.ZERO);

        if (null != items) {
            for (ConsumptionItemVo item : items) {
                this.setAmount(this.getAmount().add(item.getAmount()));
            }
        }
    }

    public void addItem(ConsumptionItemVo item) {
        if (null == items) {
            items = new ArrayList<ConsumptionItemVo>();
            this.setAmount(BigDecimal.ZERO);
        }

        items.add(item);
        this.setAmount(this.getAmount().add(item.getAmount()));
    }

    public List<AccountConsumptionVo> getPayments() {
        return payments;
    }

    public void setPayments(List<AccountConsumptionVo> payments) {
        this.payments = payments;
    }

    public void addPayment(AccountConsumptionVo payment) {
        if (null == payments) {
            payments = new ArrayList<AccountConsumptionVo>();
        }

        payments.add(payment);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void confirm(String operator) throws NoOperatorException, ConsumptionAlreadyConfirmedException,
            AccountBalanceInsufficiencyException, NewffmsSystemException {

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        if (this.getConfirmed()) {
            throw new ConsumptionException.ConsumptionAlreadyConfirmedException();
        }

        Date now = new Date();
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("seqNo", this.getSeqNo());
        param.put("cpnOid", this.getKey().getCpnOid());
        param.put("updateBy", operator);
        param.put("updateTime", now);
        param.put("confirmed", true);

        int n = mapper.updateStatus(param);

        if (1 != n) {
            throw new NewffmsDomainException.NewffmsSystemException();
        }

        for (AccountConsumptionVo payment : this.getPayments()) {
            Account acnt = null;
            try {
                acnt = acntRepos.accountOfId(new AccountKey(payment.getAcntOid()));
            } catch (AccountKeyEmptyException e) {
                throw new NewffmsDomainException.NewffmsSystemException();
            }

            try {
                acnt.subtract(payment.getAmount(), "desc", this.getBatchNum(), this.getCpnTime(), operator);
            } catch (AccountBalanceInsufficiencyException e) {
                throw e;
            } catch (NewffmsDomainException e) {
                throw new NewffmsDomainException.NewffmsSystemException();
            }
        }

        this.setSeqNo(this.getSeqNo() + 1);
        this.setUpdateBy(operator);
        this.setUpdateTime(now);
        this.setConfirmed(true);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void unconfirm(String operator)
            throws NoOperatorException, NewffmsSystemException, ConsumptionNotConfirmedException {

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        if (!this.getConfirmed()) {
            throw new ConsumptionException.ConsumptionNotConfirmedException();
        }

        Date now = new Date();
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("seqNo", this.getSeqNo());
        param.put("cpnOid", this.getKey().getCpnOid());
        param.put("updateBy", operator);
        param.put("updateTime", now);
        param.put("confirmed", false);

        int n = mapper.updateStatus(param);

        if (1 != n) {
            throw new NewffmsSystemException();
        }

        try {
            acntService.rollback(this.getBatchNum(), operator);
        } catch (NewffmsDomainException e) {
            throw new NewffmsDomainException.NewffmsSystemException();
        }

        this.setSeqNo(this.getSeqNo() + 1);
        this.setUpdateBy(operator);
        this.setUpdateTime(now);
        this.setConfirmed(false);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void updateAll(String operator)
            throws ConsumptionTypeEmptyException, ConsumptionBatchNumEmptyException, ConsumptionTimeEmptyException,
            ConsumptionAlreadyConfirmedException, ConsumptionItemsEmptyException, ConsumptionItemDescEmptyException,
            ConsumptionItemAmountEmptyException, ConsumptionItemAmountInvalidException,
            ConsumptionItemOwnerEmptyException, ConsumptionItemCategoryEmptyException,
            ConsumptionPaymentsEmptyException, ConsumptionPaymentAmountEmptyException,
            ConsumptionPaymentAmountInvalidException, ConsumptionPaymentAccountEmptyException,
            ConsumptionAmountNotMatchException, NoOperatorException, NewffmsSystemException {

        if (null == this.getCpnType()) {
            throw new ConsumptionTypeEmptyException();
        }

        /*
         * if (null == this.getBatchNum() ||
         * this.getBatchNum().trim().isEmpty()) { throw new
         * ConsumptionBatchNumEmptyException(); }
         */

        if (null == this.getCpnTime()) {
            throw new ConsumptionTimeEmptyException();
        }

        if (this.getConfirmed()) {
            throw new ConsumptionAlreadyConfirmedException();
        }

        if (null == this.getItems() || this.getItems().isEmpty()) {
            throw new ConsumptionItemsEmptyException();
        }

        this.setAmount(BigDecimal.ZERO);
        for (ConsumptionItemVo item : this.getItems()) {
            if (null == item || null == item.getItemDesc() || item.getItemDesc().trim().isEmpty()) {
                throw new ConsumptionItemDescEmptyException();
            }

            if (null == item.getAmount()) {
                throw new ConsumptionItemAmountEmptyException();
            }

            if (BigDecimal.ZERO.compareTo(item.getAmount()) >= 0) {
                throw new ConsumptionItemAmountInvalidException();
            }

            if (null == item.getOwnerOid()) {
                throw new ConsumptionItemOwnerEmptyException();
            }

            if (null == item.getCategoryOid()) {
                throw new ConsumptionItemCategoryEmptyException();
            }

            this.setAmount(this.getAmount().add(item.getAmount()));
        }

        if (null == this.getPayments() || this.getPayments().isEmpty()) {
            throw new ConsumptionPaymentsEmptyException();
        }

        BigDecimal paymentTotal = BigDecimal.ZERO;
        for (AccountConsumptionVo payment : this.getPayments()) {
            if (null == payment || null == payment.getAmount()) {
                throw new ConsumptionPaymentAmountEmptyException();
            }

            if (BigDecimal.ZERO.compareTo(payment.getAmount()) >= 0) {
                throw new ConsumptionPaymentAmountInvalidException();
            }

            if (null == payment.getAcntOid()) {
                throw new ConsumptionPaymentAccountEmptyException();
            }

            paymentTotal = paymentTotal.add(payment.getAmount());
        }

        if (paymentTotal.compareTo(this.getAmount()) != 0) {
            throw new ConsumptionAmountNotMatchException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        Date now = new Date();
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("seqNo", this.getSeqNo());
        param.put("cpnOid", this.getKey().getCpnOid());
        param.put("updateBy", operator);
        param.put("updateTime", now);
        param.put("cpnType", this.getCpnType());
        param.put("amount", this.getAmount());
        param.put("cpnTime", this.getCpnTime());

        int n = mapper.updateInfo(param);

        if (1 != n) {
            throw new NewffmsSystemException();
        }

        ConsumptionItemVo itemParam = new ConsumptionItemVo();
        itemParam.setCpnOid(this.getKey().getCpnOid());
        itemMapper.delete(itemParam);

        AccountConsumptionVo paymentParam = new AccountConsumptionVo();
        paymentParam.setCpnOid(this.getKey().getCpnOid());
        paymentMapper.delete(paymentParam);

        for (ConsumptionItemVo item : this.getItems()) {
            item.setCpnOid(this.getKey().getCpnOid());
            itemMapper.insert(item);
        }

        for (AccountConsumptionVo payment : this.getPayments()) {
            payment.setCpnOid(this.getKey().getCpnOid());
            paymentMapper.insert(payment);
        }

        this.setSeqNo(this.getSeqNo() + 1);
        this.setUpdateBy(operator);
        this.setUpdateTime(now);
    }

}

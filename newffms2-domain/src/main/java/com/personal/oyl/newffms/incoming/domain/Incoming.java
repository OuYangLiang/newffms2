package com.personal.oyl.newffms.incoming.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.account.domain.AccountKey;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.account.domain.AccountService;
import com.personal.oyl.newffms.common.AppContext;
import com.personal.oyl.newffms.common.NewffmsDomainException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAlreadyConfirmedException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAmountInvalidException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingDescInvalidException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingNotConfirmedException;
import com.personal.oyl.newffms.incoming.store.mapper.AccountIncomingMapper;
import com.personal.oyl.newffms.incoming.store.mapper.IncomingMapper;

public class Incoming implements IncomingOperation, Serializable {
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
	
	@Autowired
	private IncomingMapper mapper;
	@Autowired
	private AccountIncomingMapper itemMapper;
	@Autowired
	private AccountRepos acntRepos;
	@Autowired
	private AccountService acntService;
	
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
	
	@Override
    public void confirm(String operator)
            throws NoOperatorException, IncomingAlreadyConfirmedException, NewffmsSystemException {
	
		if (null == operator || operator.trim().isEmpty()) {
			throw new NoOperatorException();
		}
		
		if (this.getConfirmed()) {
			throw new IncomingAlreadyConfirmedException();
		}
		
		Date now = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("seqNo", this.getSeqNo());
		param.put("incomingOid", this.getKey().getIncomingOid());
		param.put("updateBy", operator);
		param.put("updateTime", now);
		param.put("confirmed", true);
		
		int n = mapper.updateStatus(param);
		
		if (1 != n) {
			throw new NewffmsSystemException();
		}
		
		Account acnt = null;
		
        try {
            acnt = acntRepos.accountOfId(new AccountKey(this.getAcntRel().getAcntOid()));
        } catch (AccountKeyEmptyException e) {
            throw new NewffmsSystemException();
        }
        
        if (null == acnt) {
            throw new NewffmsSystemException();
        }
        
		try {
            acnt.increase(this.getAmount(), this.getIncomingDesc(), this.getBatchNum(), this.getIncomingDate(), operator);
        } catch (NewffmsDomainException e) {
            throw new NewffmsSystemException();
        } 
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setUpdateBy(operator);
		this.setUpdateTime(now);
		this.setConfirmed(true);
	}
	
	@Override
    public void unconfirm(String operator)
            throws NoOperatorException, IncomingNotConfirmedException, NewffmsSystemException {
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new NoOperatorException();
		}
		
		if (!this.getConfirmed()) {
			throw new IncomingNotConfirmedException();
		}
		
		Date now = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("seqNo", this.getSeqNo());
		param.put("incomingOid", this.getKey().getIncomingOid());
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
            throw new NewffmsSystemException();
        }
		
		this.setSeqNo(this.getSeqNo() + 1);
		this.setUpdateBy(operator);
		this.setUpdateTime(now);
		this.setConfirmed(false);
	}
	
    @Override
    public void updateAll(String operator) throws NoOperatorException, IncomingAlreadyConfirmedException,
            NewffmsSystemException, IncomingDescInvalidException, IncomingAmountInvalidException {
        if (null == this.getIncomingDesc() || this.getIncomingDesc().trim().isEmpty() || this.getIncomingDesc().trim().length() > 30) {
            throw new IncomingDescInvalidException();
        }

        if (null != this.getAmount() && BigDecimal.ZERO.compareTo(this.getAmount()) >= 0) {
            throw new IncomingAmountInvalidException();
        }

        if (null != this.getConfirmed() && this.getConfirmed()) {
            throw new IncomingAlreadyConfirmedException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        Date now = new Date();
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("seqNo", this.getSeqNo());
        param.put("incomingOid", this.getKey().getIncomingOid());
        param.put("updateBy", operator);
        param.put("updateTime", now);
        param.put("incomingDesc", this.getIncomingDesc());
        param.put("amount", this.getAmount());
        param.put("incomingType", this.getIncomingType());
        param.put("ownerOid", this.getOwnerOid());
        param.put("incomingDate", this.getIncomingDate());

        int n = mapper.updateInfo(param);

        if (1 != n) {
            throw new NewffmsSystemException();
        }

        if (null != this.getAcntRel() && null != this.getAcntRel().getAcntOid()) {
            AccountIncomingVo itemParam = new AccountIncomingVo();
            itemParam.setIncomingOid(this.getKey().getIncomingOid());
            n = itemMapper.delete(itemParam);

            if (1 != n) {
                throw new NewffmsSystemException();
            }

            n = itemMapper.insert(this.getAcntRel());

            if (1 != n) {
                throw new NewffmsSystemException();
            }
        }

        this.setSeqNo(this.getSeqNo() + 1);
        this.setUpdateBy(operator);
        this.setUpdateTime(now);
    }

}

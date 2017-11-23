package com.personal.oyl.newffms.web.incoming;

import java.math.BigDecimal;
import java.util.Date;

import com.personal.oyl.newffms.incoming.domain.AccountIncomingVo;
import com.personal.oyl.newffms.incoming.domain.Incoming;
import com.personal.oyl.newffms.incoming.domain.IncomingKey;
import com.personal.oyl.newffms.incoming.domain.IncomingType;
import com.personal.oyl.newffms.web.account.AccountDto;
import com.personal.oyl.newffms.web.user.UserDto;

public class IncomingDto {
    private BigDecimal incomingOid;
    private String incomingDesc;
    private BigDecimal amount;
    private IncomingType incomingType;
    private Boolean confirmed;
    private UserDto owner;
    private Date incomingDate;
    private String batchNum;
    private String createBy;
    
    private AccountDto targetAccount;

    public IncomingDto() {

    }

    public IncomingDto(Incoming incoming) {
        this.setIncomingOid(incoming.getKey().getIncomingOid());
        this.setIncomingDesc(incoming.getIncomingDesc());
        this.setAmount(incoming.getAmount());
        this.setIncomingType(incoming.getIncomingType());
        this.setConfirmed(incoming.getConfirmed());
        this.setOwner(new UserDto());
        this.getOwner().setUserOid(incoming.getOwnerOid());
        this.setIncomingDate(incoming.getIncomingDate());
        this.setBatchNum(incoming.getBatchNum());
        this.setCreateBy(incoming.getCreateBy());
    }

    public Incoming toIncoming() {
        Incoming incoming = new Incoming();
        incoming.setKey(new IncomingKey(this.getIncomingOid()));
        incoming.setIncomingDesc(this.getIncomingDesc());
        incoming.setAmount(this.getAmount());
        incoming.setIncomingType(this.getIncomingType());
        incoming.setConfirmed(this.getConfirmed());
        incoming.setOwnerOid(this.getOwner().getUserOid());
        incoming.setIncomingDate(this.getIncomingDate());
        incoming.setBatchNum(this.getBatchNum());
        AccountIncomingVo acntRel = new AccountIncomingVo();
        acntRel.setAcntOid(this.getTargetAccount().getAcntOid());
        incoming.setAcntRel(acntRel);
        
        return incoming;
    }

    public BigDecimal getIncomingOid() {
        return incomingOid;
    }

    public void setIncomingOid(BigDecimal incomingOid) {
        this.incomingOid = incomingOid;
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
    
    public String getIncomingTypeDesc() {
        return null == incomingType ? null : incomingType.getDesc();
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

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public AccountDto getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(AccountDto targetAccount) {
        this.targetAccount = targetAccount;
    }

}

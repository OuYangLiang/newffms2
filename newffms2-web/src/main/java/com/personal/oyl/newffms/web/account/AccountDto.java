package com.personal.oyl.newffms.web.account;

import java.math.BigDecimal;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.web.user.UserDto;

public class AccountDto {
    private BigDecimal acntOid;
    private String acntDesc;
    private String acntType;
    private String acntTypeDesc;
    private BigDecimal balance;
    private BigDecimal quota;
    private BigDecimal debt;
    private UserDto owner;
    
    public AccountDto() {
        
    }
    
    public AccountDto(Account acnt) {
        this.setAcntOid(acnt.getKey().getAcntOid());
        this.setAcntDesc(acnt.getAcntDesc());
        this.setAcntType(acnt.getAcntType().name());
        this.setAcntTypeDesc(acnt.getAcntType().getDesc());
        this.setBalance(acnt.getBalance());
        this.setQuota(acnt.getQuota());
        this.setDebt(acnt.getDebt());
        
        UserDto owner = new UserDto();
        owner.setUserOid(acnt.getOwner().getUserOid());
        this.setOwner(owner);
    }

    public BigDecimal getAcntOid() {
        return acntOid;
    }

    public void setAcntOid(BigDecimal acntOid) {
        this.acntOid = acntOid;
    }

    public String getAcntDesc() {
        return acntDesc;
    }

    public void setAcntDesc(String acntDesc) {
        this.acntDesc = acntDesc;
    }

    public String getAcntType() {
        return acntType;
    }

    public void setAcntType(String acntType) {
        this.acntType = acntType;
    }

    public String getAcntTypeDesc() {
        return acntTypeDesc;
    }

    public void setAcntTypeDesc(String acntTypeDesc) {
        this.acntTypeDesc = acntTypeDesc;
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

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

}

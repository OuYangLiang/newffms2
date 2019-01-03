package com.personal.oyl.newffms.web.account;

import java.io.Serializable;
import java.math.BigDecimal;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountKey;
import com.personal.oyl.newffms.account.domain.AccountType;
import com.personal.oyl.newffms.user.domain.UserKey;
import com.personal.oyl.newffms.web.user.UserDto;

@SuppressWarnings("serial")
public class AccountDto implements Serializable {
    private BigDecimal acntOid;
    private String acntDesc;
    private AccountType acntType;
    private BigDecimal balance;
    private BigDecimal quota;
    private BigDecimal debt;
    private UserDto owner;
    private boolean disabled;
    private AccountDto target;
    private BigDecimal payment;

    public AccountDto() {

    }

    public AccountDto(Account acnt) {
        this.setAcntOid(acnt.getKey().getAcntOid());
        this.setAcntDesc(acnt.getAcntDesc());
        this.setAcntType(acnt.getAcntType());
        this.setBalance(acnt.getBalance());
        this.setQuota(acnt.getQuota());
        this.setDebt(acnt.getDebt());
        this.setDisabled(acnt.getDisabled());

        UserDto userDto = new UserDto();
        userDto.setUserOid(acnt.getOwner().getUserOid());
        this.setOwner(userDto);
    }

    public Account toAccount() {
        Account acnt = new Account();
        acnt.setKey(new AccountKey(this.getAcntOid()));
        acnt.setAcntDesc(this.getAcntDesc());
        acnt.setAcntType(this.getAcntType());
        acnt.setBalance(this.getBalance());
        acnt.setQuota(this.getQuota());
        acnt.setDebt(this.getDebt());
        acnt.setOwner(new UserKey(this.getOwner().getUserOid()));
        acnt.setDisabled(disabled);
        return acnt;
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

    public AccountType getAcntType() {
        return acntType;
    }

    public void setAcntType(AccountType acntType) {
        this.acntType = acntType;
    }

    public String getAcntTypeDesc() {
        return null == acntType ? null : acntType.getDesc();
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public AccountDto getTarget() {
        return target;
    }

    public void setTarget(AccountDto target) {
        this.target = target;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getAcntHumanDesc() {
        if (null == owner || null == owner.getUserName()) {
            if (null == acntType) {
                return this.getAcntDesc();
            }

            return this.getAcntTypeDesc() + " " + this.getAcntDesc();
        }

        return owner.getUserName() + " " + this.getAcntTypeDesc() + " " + this.getAcntDesc();
    }
}

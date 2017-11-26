package com.personal.oyl.newffms.consumption.domain;

import java.math.BigDecimal;

public class AccountConsumptionVo {
    private BigDecimal acntOid;
    private BigDecimal cpnOid;
    private BigDecimal amount;

    public BigDecimal getAcntOid() {
        return acntOid;
    }

    public void setAcntOid(BigDecimal acntOid) {
        this.acntOid = acntOid;
    }

    public BigDecimal getCpnOid() {
        return cpnOid;
    }

    public void setCpnOid(BigDecimal cpnOid) {
        this.cpnOid = cpnOid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}

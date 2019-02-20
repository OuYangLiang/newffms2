package com.personal.oyl.newffms.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountKey implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal acntOid;

    public AccountKey() {

    }

    public AccountKey(BigDecimal acntOid) {
        super();
        this.setAcntOid(acntOid);
    }

    public BigDecimal getAcntOid() {
        return acntOid;
    }

    public void setAcntOid(BigDecimal acntOid) {
        if (null != this.acntOid) {
            throw new IllegalStateException();
        }

        this.acntOid = acntOid;
    }
}

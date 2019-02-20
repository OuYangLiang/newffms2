package com.personal.oyl.newffms.user.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserKey implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal userOid;

    public UserKey() {

    }

    public UserKey(BigDecimal userOid) {
        super();
        this.setUserOid(userOid);
    }

    public BigDecimal getUserOid() {
        return userOid;
    }

    public void setUserOid(BigDecimal userOid) {
        if (null != this.userOid) {
            throw new IllegalStateException();
        }

        this.userOid = userOid;
    }
}

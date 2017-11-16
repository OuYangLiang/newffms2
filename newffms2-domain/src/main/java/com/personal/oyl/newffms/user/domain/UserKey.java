package com.personal.oyl.newffms.user.domain;

import java.math.BigDecimal;

public class UserKey {
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

package com.personal.oyl.newffms.user.domain;

import java.math.BigDecimal;

public class UserRole {
    private BigDecimal userOid;
    private BigDecimal roleOid;

    public BigDecimal getUserOid() {
        return userOid;
    }

    public void setUserOid(BigDecimal userOid) {
        this.userOid = userOid;
    }

    public BigDecimal getRoleOid() {
        return roleOid;
    }

    public void setRoleOid(BigDecimal roleOid) {
        this.roleOid = roleOid;
    }
}

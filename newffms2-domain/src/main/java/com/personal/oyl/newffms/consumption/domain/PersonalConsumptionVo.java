package com.personal.oyl.newffms.consumption.domain;

import java.math.BigDecimal;

public class PersonalConsumptionVo {
    private BigDecimal userOid;
    private BigDecimal categoryOid;
    private BigDecimal total;

    public BigDecimal getUserOid() {
        return userOid;
    }

    public void setUserOid(BigDecimal userOid) {
        this.userOid = userOid;
    }

    public BigDecimal getCategoryOid() {
        return categoryOid;
    }

    public void setCategoryOid(BigDecimal categoryOid) {
        this.categoryOid = categoryOid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}

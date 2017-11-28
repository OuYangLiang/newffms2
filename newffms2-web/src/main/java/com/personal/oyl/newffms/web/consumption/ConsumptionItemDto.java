package com.personal.oyl.newffms.web.consumption;

import java.math.BigDecimal;

public class ConsumptionItemDto {
    private BigDecimal itemOid;
    private String itemDesc;
    private BigDecimal amount;
    private BigDecimal ownerOid;
    private String ownerName;
    private BigDecimal categoryOid;
    private String categoryDesc;
    private BigDecimal cpnOid;

    public BigDecimal getItemOid() {
        return itemOid;
    }

    public void setItemOid(BigDecimal itemOid) {
        this.itemOid = itemOid;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOwnerOid() {
        return ownerOid;
    }

    public void setOwnerOid(BigDecimal ownerOid) {
        this.ownerOid = ownerOid;
    }

    public BigDecimal getCategoryOid() {
        return categoryOid;
    }

    public void setCategoryOid(BigDecimal categoryOid) {
        this.categoryOid = categoryOid;
    }

    public BigDecimal getCpnOid() {
        return cpnOid;
    }

    public void setCpnOid(BigDecimal cpnOid) {
        this.cpnOid = cpnOid;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }
}

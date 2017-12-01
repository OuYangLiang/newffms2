package com.personal.oyl.newffms.consumption.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ConsumptionItemPaginationVo {

    private BigDecimal itemOid;
    private String itemDesc;
    private BigDecimal amount;
    private BigDecimal ownerOid;
    private String ownerName;
    private BigDecimal categoryOid;
    private String categoryDesc;
    private BigDecimal cpnOid;
    private ConsumptionType cpnType;
    private Date cpnTime;
    private BigDecimal total;
    private String batchNum;
    private Boolean confirmed;
    private String createBy;

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public ConsumptionType getCpnType() {
        return cpnType;
    }
    
    public String getCpnTypeDesc() {
        return null == cpnType ? null : cpnType.getDesc();
    }

    public void setCpnType(ConsumptionType cpnType) {
        this.cpnType = cpnType;
    }

    public Date getCpnTime() {
        return cpnTime;
    }

    public void setCpnTime(Date cpnTime) {
        this.cpnTime = cpnTime;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

}

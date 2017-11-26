package com.personal.oyl.newffms.web.consumption;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.personal.oyl.newffms.consumption.domain.AccountConsumptionVo;
import com.personal.oyl.newffms.consumption.domain.ConsumptionItemVo;
import com.personal.oyl.newffms.consumption.domain.ConsumptionType;

public class ConsumptionDto {
    private BigDecimal cpnOid;
    private ConsumptionType cpnType;
    private BigDecimal amount;
    private Date cpnTime;
    private String batchNum;
    private Boolean confirmed;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
    private Integer seqNo;

    private List<ConsumptionItemVo> items;
    private List<AccountConsumptionVo> payments;

    public BigDecimal getCpnOid() {
        return cpnOid;
    }

    public void setCpnOid(BigDecimal cpnOid) {
        this.cpnOid = cpnOid;
    }

    public ConsumptionType getCpnType() {
        return cpnType;
    }

    public void setCpnType(ConsumptionType cpnType) {
        this.cpnType = cpnType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCpnTime() {
        return cpnTime;
    }

    public void setCpnTime(Date cpnTime) {
        this.cpnTime = cpnTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public List<ConsumptionItemVo> getItems() {
        return items;
    }

    public void setItems(List<ConsumptionItemVo> items) {
        this.items = items;
    }

    public List<AccountConsumptionVo> getPayments() {
        return payments;
    }

    public void setPayments(List<AccountConsumptionVo> payments) {
        this.payments = payments;
    }

}

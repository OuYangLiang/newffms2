package com.personal.oyl.newffms.web.consumption;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.personal.oyl.newffms.consumption.domain.AccountConsumptionVo;
import com.personal.oyl.newffms.consumption.domain.Consumption;
import com.personal.oyl.newffms.consumption.domain.ConsumptionItemVo;
import com.personal.oyl.newffms.consumption.domain.ConsumptionKey;
import com.personal.oyl.newffms.consumption.domain.ConsumptionType;
import com.personal.oyl.newffms.util.DateUtil;
import com.personal.oyl.newffms.web.account.AccountDto;

public class ConsumptionDto {
    private BigDecimal cpnOid;
    private ConsumptionType cpnType;
    private BigDecimal amount;
    private Date cpnTime;
    private String cpnTimeInput;
    private String batchNum;
    private Boolean confirmed;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
    private Integer seqNo;

    private List<ConsumptionItemDto> items;
    private List<AccountDto> payments;
    
    public ConsumptionDto() {
        this.setCpnTime(new Date());
        this.setItems(new LinkedList<>());
        this.setPayments(new LinkedList<>());
        this.getItems().add(new ConsumptionItemDto());
        this.getPayments().add(new AccountDto());
    }
    
    public Consumption toConsumption() {
        Consumption obj = new Consumption();
        if (null != cpnOid) {
            obj.setKey(new ConsumptionKey(this.getCpnOid()));
        }
        obj.setCpnType(this.getCpnType());
        obj.setAmount(this.getAmount());
        obj.setCpnTime(this.getCpnTime());
        obj.setBatchNum(this.getBatchNum());
        obj.setConfirmed(this.getConfirmed());
        obj.setCreateTime(this.getCreateTime());
        obj.setCreateBy(this.getCreateBy());
        obj.setUpdateBy(this.getUpdateBy());
        obj.setUpdateTime(this.getUpdateTime());
        obj.setSeqNo(this.getSeqNo());
        
        if (null != items) {
            List<ConsumptionItemVo> voList = new LinkedList<>();
            for (ConsumptionItemDto dto : items) {
                ConsumptionItemVo vo = new ConsumptionItemVo();
                vo.setItemOid(dto.getItemOid());
                vo.setItemDesc(dto.getItemDesc());
                vo.setAmount(dto.getAmount());
                vo.setOwnerOid(dto.getOwnerOid());
                vo.setCategoryOid(dto.getCategoryOid());
                vo.setCpnOid(dto.getCpnOid());
                voList.add(vo);
            }
            obj.setItems(voList);
        }
        
        if (null != payments) {
            List<AccountConsumptionVo> voList = new LinkedList<>();
            for (AccountDto dto : payments) {
                AccountConsumptionVo vo = new AccountConsumptionVo();
                vo.setAcntOid(dto.getAcntOid());
                vo.setCpnOid(this.getCpnOid());
                vo.setAmount(dto.getPayment());
                voList.add(vo);
            }
            obj.setPayments(voList);
        }
        
        return obj;
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

    public void setCpnType(ConsumptionType cpnType) {
        this.cpnType = cpnType;
    }
    
    public String getCpnTypeDesc() {
        return null == this.getCpnType() ? null : this.getCpnType().getDesc();
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

    public List<ConsumptionItemDto> getItems() {
        return items;
    }

    public void setItems(List<ConsumptionItemDto> items) {
        this.items = items;
    }

    public List<AccountDto> getPayments() {
        return payments;
    }

    public void setPayments(List<AccountDto> payments) {
        this.payments = payments;
    }
    
    public BigDecimal getTotalItemAmount() {
        if (null == items) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal rlt = BigDecimal.ZERO;
        for (ConsumptionItemDto item : this.items) {
            rlt = rlt.add(null == item.getAmount() ? BigDecimal.ZERO : item.getAmount());
        }
        
        return rlt;
    }
    
    public BigDecimal getTotalPayment() {
        if (null == payments) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal rlt = BigDecimal.ZERO;
        for (AccountDto item : this.payments) {
            rlt = rlt.add(null == item.getPayment() ? BigDecimal.ZERO : item.getPayment());
        }
        
        return rlt;
    }

    public String getCpnTimeInput() {
        if (null != cpnTimeInput) {
            return cpnTimeInput;
        }

        if (null != cpnTime) {
            return DateUtil.getInstance().format(cpnTime, "yyyy-MM-dd HH:mm");
        }

        return null;
    }

    public void setCpnTimeInput(String cpnTimeInput) {
        this.cpnTimeInput = cpnTimeInput;

        try {
            cpnTime = DateUtil.getInstance().parse(cpnTimeInput, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

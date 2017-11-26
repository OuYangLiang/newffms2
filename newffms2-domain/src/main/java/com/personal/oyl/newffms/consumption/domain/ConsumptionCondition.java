package com.personal.oyl.newffms.consumption.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.personal.oyl.newffms.common.PaginationParameter;

public class ConsumptionCondition {
    private BigDecimal ownerOid;
    private BigDecimal categoryOid;
    private String categoryDesc;//回显
    private Boolean confirmed;
    private Date cpnTimeFrom;
    private Date cpnTimeTo;
    private String itemDesc;
    private PaginationParameter paginationParameter;
    
    public void fillMap(Map<String, Object> target) {
        target.put("ownerOid", ownerOid);
        target.put("categoryOid", categoryOid);
        target.put("confirmed", confirmed);
        target.put("cpnTimeFrom", cpnTimeFrom);
        target.put("cpnTimeTo", cpnTimeTo);
        target.put("itemDesc", itemDesc);
        paginationParameter.fillMap(target);
    }

    public void initPaginationParam(int page, int sizePerPage, String sortField, String sortDir) {
        paginationParameter = new PaginationParameter(page, sizePerPage, sortField, sortDir);
    }

    public void initPaginationParam(int page, int sizePerPage) {
        paginationParameter = new PaginationParameter(page, sizePerPage);
    }

    public PaginationParameter getPaginationParameter() {
        return paginationParameter;
    }

    public void setPaginationParameter(PaginationParameter paginationParameter) {
        this.paginationParameter = paginationParameter;
    }

    public Date getCpnTimeFrom() {
        return cpnTimeFrom;
    }

    public void setCpnTimeFrom(Date cpnTimeFrom) {
        this.cpnTimeFrom = cpnTimeFrom;
    }

    public Date getCpnTimeTo() {
        return cpnTimeTo;
    }

    public void setCpnTimeTo(Date cpnTimeTo) {
        this.cpnTimeTo = cpnTimeTo;
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

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}

package com.personal.oyl.newffms.incoming.domain;

import java.math.BigDecimal;
import java.util.Map;

import com.personal.oyl.newffms.common.PaginationParameter;

public class IncomingCondition {
    private BigDecimal ownerOid;
    private IncomingType incomingType;
    private Boolean confirmed;
    private String incomingDesc;
    private PaginationParameter paginationParameter;

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

    public BigDecimal getOwnerOid() {
        return ownerOid;
    }

    public void setOwnerOid(BigDecimal ownerOid) {
        this.ownerOid = ownerOid;
    }

    public IncomingType getIncomingType() {
        return incomingType;
    }

    public void setIncomingType(IncomingType incomingType) {
        this.incomingType = incomingType;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getIncomingDesc() {
        return incomingDesc;
    }

    public void setIncomingDesc(String incomingDesc) {
        this.incomingDesc = incomingDesc;
    }

    public void fillMap(Map<String, Object> target) {
        target.put("ownerOid", ownerOid);
        target.put("incomingType", incomingType);
        target.put("confirmed", confirmed);
        target.put("incomingDesc", incomingDesc);
    }
}

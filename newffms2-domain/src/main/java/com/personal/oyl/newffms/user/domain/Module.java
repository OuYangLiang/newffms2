package com.personal.oyl.newffms.user.domain;

import java.math.BigDecimal;

public class Module {
    private ModuleKey key;
    private String moduleDesc;
    private Integer moduleLevel;
    private Integer showOrder;
    private String moduleLink;
    private BigDecimal parentOid;

    public ModuleKey getKey() {
        return key;
    }

    public void setKey(ModuleKey key) {
        this.key = key;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public Integer getModuleLevel() {
        return moduleLevel;
    }

    public void setModuleLevel(Integer moduleLevel) {
        this.moduleLevel = moduleLevel;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getModuleLink() {
        return moduleLink;
    }

    public void setModuleLink(String moduleLink) {
        this.moduleLink = moduleLink;
    }

    public BigDecimal getParentOid() {
        return parentOid;
    }

    public void setParentOid(BigDecimal parentOid) {
        this.parentOid = parentOid;
    }

}

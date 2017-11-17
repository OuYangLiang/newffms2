package com.personal.oyl.newffms.user.domain;

import java.math.BigDecimal;

public class ModuleKey {
    private BigDecimal moduleOid;

    public ModuleKey() {

    }

    public ModuleKey(BigDecimal moduleOid) {
        super();
        this.setModuleOid(moduleOid);
    }

    public BigDecimal getModuleOid() {
        return moduleOid;
    }

    public void setModuleOid(BigDecimal moduleOid) {
        if (null != this.moduleOid) {
            throw new IllegalStateException();
        }

        this.moduleOid = moduleOid;
    }

}

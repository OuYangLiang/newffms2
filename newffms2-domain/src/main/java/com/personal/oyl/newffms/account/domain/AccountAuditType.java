package com.personal.oyl.newffms.account.domain;

import java.util.HashMap;
import java.util.Map;

public enum AccountAuditType {
    Add("增加"), Subtract("扣减"), Change("更新"), Trans_add("转账增加"), Trans_subtract("转账扣减"), Rollback("回滚");

    private String desc;

    private AccountAuditType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, String> toMapValue() {
        Map<String, String> rlt = new HashMap<String, String>();
        for (AccountAuditType ms : AccountAuditType.values()) {
            rlt.put(ms.name(), ms.getDesc());
        }

        return rlt;
    }
}

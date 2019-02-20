package com.personal.oyl.newffms.account.domain;

import java.util.HashMap;
import java.util.Map;

public enum AccountAuditType {
    add("增加"), subtract("扣减"), change("更新"), trans_add("转账增加"), trans_subtract("转账扣减"), rollback("回滚");

    private String desc;

    AccountAuditType(String desc) {
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

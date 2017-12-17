package com.personal.oyl.newffms.incoming.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum IncomingType {
    Salary("工资"), Bonus("奖金"), Cash("礼金"), Investment("投资收益"), Accumulation("公积金"), Other("其它");

    private String desc;

    private IncomingType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, String> toMapValue() {
        Map<String, String> rlt = new HashMap<String, String>();
        for (IncomingType ms : IncomingType.values()) {
            rlt.put(ms.name(), ms.getDesc());
        }
        return rlt;
    }

    public static Map<IncomingType, BigDecimal> initAmtMap() {
        Map<IncomingType, BigDecimal> rlt = new HashMap<IncomingType, BigDecimal>();
        for (IncomingType ms : IncomingType.values()) {
            rlt.put(ms, BigDecimal.ZERO);
        }
        return rlt;
    }
}

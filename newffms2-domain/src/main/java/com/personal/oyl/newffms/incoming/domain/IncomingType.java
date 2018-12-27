package com.personal.oyl.newffms.incoming.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum IncomingType {
    salary("工资"), bonus("奖金"), cash("礼金"), investment("投资收益"), accumulation("公积金"), other("其它");

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

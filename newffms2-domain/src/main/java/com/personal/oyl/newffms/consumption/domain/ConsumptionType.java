package com.personal.oyl.newffms.consumption.domain;

import java.util.HashMap;
import java.util.Map;

public enum ConsumptionType {
    Supermarket("超市"),
    Online("网购"),
    Store("商店"),
    Other("其它");
    
    private String desc;

    private ConsumptionType(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }
    
    public static Map<String, String> toMapValue()
    {
        Map<String,String> rlt = new HashMap<String,String>();
        for (ConsumptionType ms : ConsumptionType.values())
        {
            rlt.put(ms.name(), ms.getDesc());
        }
        
        return rlt;
    }
}

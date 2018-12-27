package com.personal.oyl.newffms.consumption.domain;

import java.util.HashMap;
import java.util.Map;

public enum ConsumptionType {
    supermarket("超市"),
    online("网购"),
    store("商店"),
    other("其它");
    
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

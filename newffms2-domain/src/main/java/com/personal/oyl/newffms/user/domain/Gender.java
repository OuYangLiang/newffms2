package com.personal.oyl.newffms.user.domain;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    male("男"),
    female("女");
    
    private String desc;

    private Gender(String desc)
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
        for (Gender ms : Gender.values())
        {
            rlt.put(ms.name(), ms.getDesc());
        }
        
        return rlt;
    }

}

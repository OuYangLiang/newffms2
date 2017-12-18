package com.personal.oyl.newffms.account.domain;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {
    Cash("现金"),
    Bankcard("银行卡"),
    Creditcard("信用卡"),
    Alipay("支付宝"),
    Epp("易付宝"),
    MedicalInsurance("医保"),
    Accumulation("公积金"),
    Fund("基金"),
    Other("其它");
    
    private String desc;

    private AccountType(String desc)
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
        for (AccountType ms : AccountType.values())
        {
            rlt.put(ms.name(), ms.getDesc());
        }
        
        return rlt;
    }
}

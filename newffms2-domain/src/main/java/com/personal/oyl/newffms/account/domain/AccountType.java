package com.personal.oyl.newffms.account.domain;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {
    cash("现金"),
    bankcard("银行卡"),
    creditcard("信用卡"),
    alipay("支付宝"),
    epp("易付宝"),
    medicalinsurance("医保"),
    accumulation("公积金"),
    fund("基金"),
    other("其它");
    
    private String desc;

    AccountType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
    
    public static Map<String, String> toMapValue() {
        Map<String, String> rlt = new HashMap<String, String>();
        for (AccountType ms : AccountType.values()) {
            rlt.put(ms.name(), ms.getDesc());
        }
        
        return rlt;
    }
}

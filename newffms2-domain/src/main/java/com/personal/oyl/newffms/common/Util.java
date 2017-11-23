package com.personal.oyl.newffms.common;

import java.util.UUID;

public final class Util {
    private static Util instance;

    private Util() {

    }

    public static Util getInstance() {
        if (null == instance) {
            synchronized (Util.class) {
                if (null == instance) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }

    public String generateBatchNum() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}

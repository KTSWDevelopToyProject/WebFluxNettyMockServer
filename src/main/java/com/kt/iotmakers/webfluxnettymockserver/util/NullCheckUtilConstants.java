package com.kt.iotmakers.webfluxnettymockserver.util;

public enum NullCheckUtilConstants {
    STRING_NULL_CHECK(sourceString -> {
        if (sourceString == null) {
            throw new NullPointerException("sourceString must not be null");
        }
        return sourceString;
    }),
    OBJECT_NULL_CHECK(sourceObject -> {
        if (sourceObject == null) {
            throw new NullPointerException("sourceObject must not be null");
        }
        return sourceObject;
    }),
    MAP_NULL_CHECK(sourceMap -> {
        if (sourceMap == null) {
            throw new NullPointerException("sourceMap must not be null");
        }
        return sourceMap;
    });

    private NullCheckUtil nullCheckUtil;

    NullCheckUtilConstants(NullCheckUtil nullCheckUtil) {
        this.nullCheckUtil = nullCheckUtil;
    }

    public <T> T checkNull(T t) {
        return (T) this.nullCheckUtil.checkNull(t);
    }
}

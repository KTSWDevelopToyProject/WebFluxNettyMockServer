package com.kt.iotmakers.webfluxnettymockserver.util;

@FunctionalInterface
public interface NullCheckUtil<T, R> {

    R checkNull(T t);

}

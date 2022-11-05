package com.can.zupuserservice.core.data.enums;

public enum PageOrder {
    asc("asc"),
    desc("desc");

    public final String ORDER_NAME;
    PageOrder(String ORDER_NAME) {
        this.ORDER_NAME = ORDER_NAME;
    }
}

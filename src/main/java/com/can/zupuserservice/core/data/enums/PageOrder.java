package com.can.zupuserservice.core.data.enums;

public enum PageOrder {
    ASC("asc", 1),
    DESC("desc", -1);

    public final String ORDER_NAME;
    public final Integer ORDER_ID;
    PageOrder(String ORDER_NAME, Integer ORDER_ID) {
        this.ORDER_NAME = ORDER_NAME;
        this.ORDER_ID = ORDER_ID;
    }
}

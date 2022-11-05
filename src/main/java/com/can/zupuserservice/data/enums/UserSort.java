package com.can.zupuserservice.data.enums;

public enum UserSort {
    id("id"),
    username("username"),
    createdAt("createdAt"),
    updatedAt("updatedAt"),
    email("email");

    public final String SORT_NAME;
    UserSort(String SORT_NAME) {
        this.SORT_NAME = SORT_NAME;
    }
}

package com.can.zupuserservice.core.data.enums;

public enum UserStatus {
    PASSIVE(0),
    ACTIVE(1),
    DELETED(2),
    SUSPENDED(3);

    public final int status;
    UserStatus(int status) {
        this.status = status;
    }
}

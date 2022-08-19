package com.can.zupuserservice.core.data.enums;

public enum PasswordConstraintStatus {
    FAIL(0),
    PASS(1),
    NOT_CHECKED(2);

    public final int status;
    PasswordConstraintStatus(int status) {
        this.status = status;
    }
}

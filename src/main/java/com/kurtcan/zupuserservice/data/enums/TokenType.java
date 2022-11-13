package com.kurtcan.zupuserservice.data.enums;

public enum TokenType {
    AUTHENTICATION(0),
    ACCOUNT_ACTIVATION(1),
    PASSWORD_RESET(2);

    public final int type;
    TokenType(int type) {
        this.type = type;
    }
}

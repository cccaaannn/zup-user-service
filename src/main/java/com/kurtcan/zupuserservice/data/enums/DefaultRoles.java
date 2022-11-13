package com.kurtcan.zupuserservice.data.enums;

public enum DefaultRoles {
    USER("USER", "Default user role"),
    ADMIN("ADMIN", "Admin role"),
    SYS_ADMIN("SYS_ADMIN", "Sys admin role");

    public final String name;
    public final String description;
    DefaultRoles(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

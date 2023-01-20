package com.brutus.sievn.security;

public enum ApplicationUserPermission {

    DATA_ADD("data:add"),
    DATA_DELETE("data:delete"),
    DATA_UPDATE("data:update"),

    USER_DELETE("user:delete"),
    USER_VERIFY("user:verify");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

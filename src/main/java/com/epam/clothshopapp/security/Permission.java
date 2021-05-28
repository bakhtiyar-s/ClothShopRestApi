package com.epam.clothshopapp.security;

public enum Permission {
    READ("READ"),
    WRITE("WRITE");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

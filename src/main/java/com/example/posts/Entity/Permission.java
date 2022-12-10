package com.example.posts.Entity;

public enum Permission {
    USER("USER"), ADMIN("ADMIN");

    final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

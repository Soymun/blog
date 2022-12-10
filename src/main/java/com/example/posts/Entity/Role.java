package com.example.posts.Entity;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.USER)), ADMIN(Set.of(Permission.ADMIN));

    final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> authorities(){
        return permissions.stream().map(n -> new SimpleGrantedAuthority(n.permission)).collect(Collectors.toSet());
    }
}

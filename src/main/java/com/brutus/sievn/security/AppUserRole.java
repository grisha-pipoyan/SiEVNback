package com.brutus.sievn.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum AppUserRole {

    ADMIN(Sets.newHashSet(ApplicationUserPermission.USER_DELETE,
            ApplicationUserPermission.USER_VERIFY,
            ApplicationUserPermission.DATA_UPDATE,
            ApplicationUserPermission.DATA_DELETE,
            ApplicationUserPermission.DATA_ADD)),
    USER(Sets.newHashSet(ApplicationUserPermission.DATA_ADD,
            ApplicationUserPermission.DATA_DELETE,
            ApplicationUserPermission.DATA_UPDATE));


    private final Set<ApplicationUserPermission> permissions;

    AppUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }

}

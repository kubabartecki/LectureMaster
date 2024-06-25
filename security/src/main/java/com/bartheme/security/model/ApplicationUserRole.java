package com.bartheme.security.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bartheme.security.model.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(new HashSet<>(Arrays.asList(STUDENT_READ, STUDENT_WRITE))),
    TEACHER(new HashSet<>(Arrays.asList(LECTURE_READ, LECTURE_WRITE, STUDENT_READ, STUDENT_WRITE))),
    ADMIN(new HashSet<>(Arrays.asList(LECTURE_READ, LECTURE_WRITE, STUDENT_READ, STUDENT_WRITE, ADMIN_READ, ADMIN_WRITE)));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return permissions;
    }
}

package com.bartheme.security.auth;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<ApplicationUser> findByUsername(String username);
}

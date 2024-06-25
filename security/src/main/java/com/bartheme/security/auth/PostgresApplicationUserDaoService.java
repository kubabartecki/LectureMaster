package com.bartheme.security.auth;

import com.bartheme.security.feign.UserFeign;
import com.bartheme.security.model.ApplicationUserDto;
import com.bartheme.security.model.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("postgres")
public class PostgresApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;
    private final UserFeign userFeign;

    @Autowired
    public PostgresApplicationUserDaoService(PasswordEncoder passwordEncoder, UserFeign userFeign) {
        this.passwordEncoder = passwordEncoder;
        this.userFeign = userFeign;
    }

    @Override
    public Optional<ApplicationUser> findByUsername(String username) {

        Optional<ApplicationUserDto> applicationUserDaoOptional = userFeign.getApplicationUserDtoByUsername(username);
        if (applicationUserDaoOptional.isEmpty()) {
            return Optional.empty();
        }
        ApplicationUserDto applicationUserDao = applicationUserDaoOptional.get();
        ApplicationUserRole role;
        try {
            role = ApplicationUserRole.valueOf(applicationUserDao.getRole());
        } catch ( IllegalArgumentException e ) {
            throw new RuntimeException(e);
        }

        return Optional.of(new ApplicationUser(
                applicationUserDao.getUsername(),
                passwordEncoder.encode(applicationUserDao.getPassword()),
                role.getGrantedAuthorities(),
                true,
                true,
                true,
                true
        ));
    }
}

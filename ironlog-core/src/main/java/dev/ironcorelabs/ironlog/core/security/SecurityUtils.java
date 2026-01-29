package dev.ironcorelabs.ironlog.core.security;

import dev.ironcorelabs.ironlog.core.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public Long getCurrentUser() {

        final AuthenticateUser userDetail = getCurrentUserDetail();

        return userDetail.getId();
    }

    public AuthenticateUser getCurrentUserDetail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated())
        {
            throw new UnauthorizedException("session.not.active");
        }

        if (authentication.getPrincipal() instanceof AuthenticateUser userDetails)
        {
            return userDetails;
        }

        throw new UnauthorizedException("session.not.active");
    }
}

package dev.ironcorelabs.ironlog.core.security;

public interface SecurityUtils {
    Long getCurrentUserId();
    AuthenticateUser getCurrentUserDetailAuthenticateUser();
    boolean hasAuthority(String authority);
}

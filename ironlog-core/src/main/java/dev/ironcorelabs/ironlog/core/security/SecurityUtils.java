package dev.ironcorelabs.ironlog.core.security;

import java.util.UUID;

public interface SecurityUtils {
    Long getCurrentUserId();
    UUID getExternalId();
    boolean hasRole(String role);

    default boolean isOwnerOrAdmin(Long userId) {
        return hasRole("ROLE_ADMIN") || getCurrentUserId().equals(userId);
    }

    default boolean isOwnerOrAdmin(UUID externalId) {
        return hasRole("ROLE_ADMIN") || getExternalId().equals(externalId);
    }

    default boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }
}

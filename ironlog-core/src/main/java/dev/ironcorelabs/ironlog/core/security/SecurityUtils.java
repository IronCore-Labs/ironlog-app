package dev.ironcorelabs.ironlog.core.security;

import java.util.UUID;

public interface SecurityUtils {
    Long getCurrentUserId();
    UUID getExternalId();
    boolean hasAuthority(String authority);
}

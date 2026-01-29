package dev.ironcorelabs.ironlog.security.model.enums;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public enum UserRole {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_TRAINER("ROLE_TRAINER"),
    ROLE_CLIENT("ROLE_CLIENT");

    UserRole(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    private final String value;
}

package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.core.dto.UserDTO;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UpdateUserRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.User;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UserBaseRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UserRoleEnum;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public interface InternalUserService {
    UserDTO createUnsafe(UserBaseRequest request, UserRoleEnum role, Boolean needRegistration);
    UserDTO updateUnsafe(Long id, UpdateUserRequest request);
    UserDTO updateUnsafeByExternalId(UUID id, UpdateUserRequest request);
    UserDTO findByExternalId(UUID id);
    void addRole(UUID id, UserRoleEnum role);
    void revokeRole(UUID id, UserRoleEnum role);
    void addRole(Long id, UserRoleEnum role);
    void revokeRole(Long id, UserRoleEnum role);

    default boolean needRegistration(UserBaseRequest request) {
        return Stream.of(request.getBirthday(), request.getLastName(), request.getLocation())
                .anyMatch(val -> Objects.isNull(val) || (val instanceof String s && !StringUtils.hasText((s))));
    }
}

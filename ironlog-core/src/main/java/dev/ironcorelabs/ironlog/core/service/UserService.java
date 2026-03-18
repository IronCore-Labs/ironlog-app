package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.restapi.openapi.model.*;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User findById(Long id);

    User findByExternalId(UUID externalId);

    User findByEmail(String email);

    User create(UserBaseRequest request);

    User createUnsafe(UserBaseRequest request, UserRoleEnum role);

    void delete(Long id);

    void deleteByExternalId(UUID externalId);

    User update(Long id, UpdateUserRequest request);

    User updateByExternalId(UUID id, UpdateUserRequest request);

    User updateUnsafe(Long id, UpdateUserRequest request);

    User updateUnsafeByExternalId(UUID id, UpdateUserRequest request);

    List<User> findAll();

    UserList findAll(int page, int size);

    PasswordVerified verifyPassword(Long id, String password);

    void changePassword(Long id, ChangePasswordRequest request);

    void changePasswordByExternalId(UUID id, ChangePasswordRequest request);

    void promoteToAdmin(UUID id);

    void addRole(UUID id, UserRoleEnum role);
}

package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.restapi.openapi.model.*;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User findById(Long id);

    User findByExternalId(UUID externalId);

    User findByEmail(String email);

    User register(RegisterUserRequest request);

    User create(CreateUserRequest request);

    void delete(Long id);

    void deleteByExternalId(UUID externalId);

    User update(Long id, UpdateUserRequest request);

    User updateByExternalId(UUID id, UpdateUserRequest request);

    List<User> findAll();

    UserList findAll(int page, int size);

    PasswordVerified verifyPassword(Long id, String password);

    void changePassword(Long id, ChangePasswordRequest request);
}

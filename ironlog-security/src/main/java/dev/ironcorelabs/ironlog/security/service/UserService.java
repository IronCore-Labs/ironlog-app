package dev.ironcorelabs.ironlog.security.service;

import dev.ironcorelabs.ironlog.restapi.openapi.model.*;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByEmail(String email);

    User register(RegisterUserRequest request);

    User create(CreateUserRequest request);

    void delete(Long id);

    User update(Long id, UpdateUserRequest request);

    List<User> findAll();

    UserList findAll(int page, int size);

    PasswordVerified verifyPassword(Long id, String password);

    void changePassword(Long id, ChangePasswordRequest request);
}

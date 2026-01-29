package dev.ironcorelabs.ironlog.security.controller;

import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import dev.ironcorelabs.ironlog.restapi.openapi.api.UsersApi;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import dev.ironcorelabs.ironlog.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
public class UserController implements UsersApi {

    private final UserService service;
    private final SecurityUtils utils;

    public UserController(UserService service, SecurityUtils utils) {
        this.service = service;
        this.utils = utils;
    }

    @Override
    public ResponseEntity<PasswordVerified> verifyPassword(VerifyPasswordRequest request) {
        return ResponseEntity.ok(service.verifyPassword(utils.getCurrentUser(), request.getPassword()));
    }

    @Override
    public ResponseEntity<User> updateUser(Long id, UpdateUserRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Override
    public ResponseEntity<UserList> getUsers(Integer page, Integer size, String sort) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(service.findById(utils.getCurrentUser()));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<User> createUser(CreateUserRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @Override
    public ResponseEntity<Void> changePassword(ChangePasswordRequest request) {
        service.changePassword(utils.getCurrentUser(), request);
        return ResponseEntity.noContent().build();
    }
}

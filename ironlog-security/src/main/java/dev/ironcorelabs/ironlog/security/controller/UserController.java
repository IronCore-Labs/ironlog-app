package dev.ironcorelabs.ironlog.security.controller;

import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import dev.ironcorelabs.ironlog.restapi.openapi.api.UsersApi;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService service;
    private final SecurityUtils utils;

    @Override
    public ResponseEntity<PasswordVerified> verifyPassword(VerifyPasswordRequest request) {
        return ResponseEntity.ok(service.verifyPassword(utils.getCurrentUserId(), request.getPassword()));
    }

    @Override
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> updateUser(UUID id, UpdateUserRequest request) {
        return ResponseEntity.ok(service.updateByExternalId(id, request));
    }

    @Override
    public ResponseEntity<User> updateMyProfile(UpdateUserRequest updateUserRequest) {
        final Long currentUserId = utils.getCurrentUserId();
        return ResponseEntity.ok(service.update(currentUserId, updateUserRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserList> getUsers(Integer page, Integer size, String sort) {
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @Override
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUserById(UUID id) {
        return ResponseEntity.ok(service.findByExternalId(id));
    }

    @Override
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(service.findById(utils.getCurrentUserId()));
    }

    @Override
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Void> deleteUser(UUID id) {
        service.deleteByExternalId(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> createUser(CreateUserRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @Override
    public ResponseEntity<Void> changePassword(ChangePasswordRequest request) {
        service.changePassword(utils.getCurrentUserId(), request);
        return ResponseEntity.noContent().build();
    }
}

package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.core.dto.UserDTO;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO findById(Long id);

    UserDTO findByExternalId(UUID externalId);

    UserDTO findByEmail(String email);

    UserDTO create(UserBaseRequest request);

    void delete(Long id);

    void deleteByExternalId(UUID externalId);

    UserDTO update(Long id, UpdateUserRequest request);

    UserDTO updateByExternalId(UUID id, UpdateUserRequest request);

    List<UserDTO> findAll();

    UserList findAll(int page, int size);

    PasswordVerified verifyPassword(Long id, String password);

    void changePassword(Long id, ChangePasswordRequest request);

    void changePasswordByExternalId(UUID id, ChangePasswordRequest request);

    void promoteToAdmin(UUID id);

    void revokeAdmin(UUID id);

    void deactivateByUUID(UUID id);

    void deactivate(Long id);
}

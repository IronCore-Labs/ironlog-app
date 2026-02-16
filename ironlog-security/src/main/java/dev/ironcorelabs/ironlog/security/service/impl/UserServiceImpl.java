package dev.ironcorelabs.ironlog.security.service.impl;

import dev.ironcorelabs.ironlog.core.exception.AccessDeniedException;
import dev.ironcorelabs.ironlog.core.exception.RecordNotFoundException;
import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import dev.ironcorelabs.ironlog.security.exception.BadCredentialsException;
import dev.ironcorelabs.ironlog.security.mapper.UserMapper;
import dev.ironcorelabs.ironlog.security.model.entities.AppUser;
import dev.ironcorelabs.ironlog.security.model.enums.UserRole;
import dev.ironcorelabs.ironlog.security.model.repositories.UserRepository;
import dev.ironcorelabs.ironlog.security.service.RefreshTokenService;
import dev.ironcorelabs.ironlog.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final RefreshTokenService refreshTokenService;
    private final SecurityUtils securityUtils;

    @Override
    public User findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found")));
    }

    @Override
    public User findByExternalId(UUID externalId) {
        return mapper.toDto(repository.findByExternalId(externalId)
                .orElseThrow(() -> new RecordNotFoundException("not.found")));
    }

    @Override
    public User findByEmail(String email) {
        return mapper.toDto(repository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("not.found")));
    }

    @Override
    @Transactional
    public User register(RegisterUserRequest request) {
        final AppUser user = mapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(UserRole.ROLE_CLIENT);
        user.setEnabled(Boolean.TRUE);

        return mapper.toDto(repository.save(user));
    }

    @Override
    @Transactional
    public User create(CreateUserRequest request) {
        final AppUser user = mapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));

        return mapper.toDto(repository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        repository.delete(user);
    }

    @Override
    @Transactional
    public void deleteByExternalId(UUID externalId) {
        final AppUser user = repository.findByExternalId(externalId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        repository.delete(user);
    }

    @Override
    @Transactional
    public User update(Long id, UpdateUserRequest request) {
        validate(id);
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return update(user, request);
    }

    @Override
    @Transactional
    public User updateByExternalId(UUID id, UpdateUserRequest request) {

        final AppUser user = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));
        validate(user.getId());

        return update(user, request);
    }

    private User update(AppUser user, UpdateUserRequest request) {
        mapper.updateEntity(request, user);

        return mapper.toDto(repository.save(user));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public UserList findAll(int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        final Page<AppUser> result = repository.findAll(pageable);

        return mapper.toDto(result);
    }

    @Override
    public PasswordVerified verifyPassword(Long id, String password) {
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        if (!encoder.matches(password, user.getPassword()))
        {
            throw new BadCredentialsException("password.incorrect");
        }

        return new PasswordVerified()
                .verified(Boolean.TRUE);
    }

    @Override
    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request) {
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        if (!encoder.matches(request.getOldPassword(), user.getPassword()))
        {
            throw new BadCredentialsException("password.incorrect");
        }

        user.setPassword(encoder.encode(request.getNewPassword()));
        repository.save(user);

        refreshTokenService.revokeAllSessions(user.getId());
    }

    private void validate(Long userId) {
        final Long currentUserId = securityUtils.getCurrentUserId();

        if (!currentUserId.equals(userId)
            && !securityUtils.hasAuthority("user:write"))
        {
            throw new AccessDeniedException("security.access_denied");
        }
    }
}

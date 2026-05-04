package dev.ironcorelabs.ironlog.security.service.impl;

import dev.ironcorelabs.ironlog.core.dto.UserDTO;
import dev.ironcorelabs.ironlog.core.event.UserStatusEvent;
import dev.ironcorelabs.ironlog.core.exception.RecordNotFoundException;
import dev.ironcorelabs.ironlog.core.service.InternalUserService;
import dev.ironcorelabs.ironlog.core.service.UserService;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import dev.ironcorelabs.ironlog.security.exception.BadCredentialsException;
import dev.ironcorelabs.ironlog.security.mapper.UserMapper;
import dev.ironcorelabs.ironlog.security.model.entity.AppUser;
import dev.ironcorelabs.ironlog.security.model.enums.UserRole;
import dev.ironcorelabs.ironlog.security.model.repository.UserRepository;
import dev.ironcorelabs.ironlog.security.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements InternalUserService, UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final RefreshTokenService refreshTokenService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UserDTO findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found")));
    }

    @Override
    public UserDTO findByExternalId(UUID externalId) {
        return mapper.toDto(repository.findByExternalId(externalId)
                .orElseThrow(() -> new RecordNotFoundException("not.found")));
    }

    @Override
    public UserDTO findByEmail(String email) {
        return mapper.toDto(repository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("not.found")));
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isAdmin()")
    public UserDTO create(UserBaseRequest request) {
        return createUnsafe(request, UserRoleEnum.ADMIN, needRegistration(request));
    }

    @Override
    @Transactional
    public UserDTO createUnsafe(UserBaseRequest request, UserRoleEnum role, Boolean needRegistration) {
        final AppUser user = mapper.toEntity(request);
        user.setRoles(List.of(mapper.toEntity(role)));
        user.setPassword(encoder.encode(request.getPassword()));
        user.setNeedRegistration(needRegistration);

        return mapper.toDto(repository.save(user));
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isAdmin()")
    public void delete(Long id) {
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        repository.delete(user);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isAdmin()")
    public void deleteByExternalId(UUID externalId) {
        final AppUser user = repository.findByExternalId(externalId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        repository.delete(user);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    public UserDTO update(Long id, UpdateUserRequest request) {
        return updateUnsafe(id, request);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    public UserDTO updateByExternalId(UUID id, UpdateUserRequest request) {
        return updateUnsafeByExternalId(id, request);
    }

    @Override
    @Transactional
    public UserDTO updateUnsafe(Long id, UpdateUserRequest request) {
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return update(user, request);
    }

    @Override
    @Transactional
    public UserDTO updateUnsafeByExternalId(UUID id, UpdateUserRequest request) {
        final AppUser user = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return update(user, request);
    }

    private UserDTO update(AppUser user, UpdateUserRequest request) {
        mapper.updateEntity(request, user);

        if (user.getNeedRegistration())
        {
            user.setNeedRegistration(false);
        }

        return mapper.toDto(repository.save(user));
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    public List<UserDTO> findAll() {
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

        changeUserPassword(user, request);
    }

    @Override
    @Transactional
    public void changePasswordByExternalId(UUID id, ChangePasswordRequest request) {
        final AppUser user = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        changeUserPassword(user, request);
    }

    private void changeUserPassword(AppUser user, ChangePasswordRequest request) {
        if (!encoder.matches(request.getOldPassword(), user.getPassword()))
        {
            throw new BadCredentialsException("password.incorrect");
        }

        user.setPassword(encoder.encode(request.getNewPassword()));

        if (user.getPasswordChangeRequired())
        {
            user.setPasswordChangeRequired(false);
        }

        repository.save(user);

        refreshTokenService.revokeAllSessions(user.getId());
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isAdmin()")
    public void promoteToAdmin(UUID id) {
        addRole(id, UserRoleEnum.ADMIN);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isAdmin()")
    public void revokeAdmin(UUID id) {
        revokeRole(id, UserRoleEnum.ADMIN);
    }

    @Override
    @Transactional
    public void addRole(UUID id, UserRoleEnum role) {
        final AppUser user = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        addRole(user, role);
    }

    @Override
    @Transactional
    public void revokeRole(UUID id, UserRoleEnum role) {
        final AppUser user = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        revokeRole(user, role);
    }

    @Override
    @Transactional
    public void addRole(Long id, UserRoleEnum role) {
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        addRole(user, role);
    }

    @Override
    @Transactional
    public void revokeRole(Long id, UserRoleEnum role) {
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        revokeRole(user, role);
    }

    protected void revokeRole(AppUser user, UserRoleEnum role) {
        final UserRole entityRole = mapper.toEntity(role);

        if (user.getRoles().stream().noneMatch(entityRole::equals))
        {
            return ;
        }

        user.revokeRole(entityRole);

        if (user.getRoles().isEmpty())
        {
            user.setEnabled(false);
        }

        repository.save(user);
    }

    protected void addRole(AppUser user, UserRoleEnum role) {
        final UserRole entityRole = mapper.toEntity(role);

        if (user.getRoles().stream().anyMatch(entityRole::equals))
        {
            return ;
        }

        user.addRole(entityRole);
        user.setEnabled(true);

        repository.save(user);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    public void deactivateByUUID(UUID id) {
        final AppUser user = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        setEnabled(user, false);
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    public void deactivate(Long id) {
        final AppUser user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        setEnabled(user, false);
    }

    protected void setEnabled(AppUser user, boolean enabled) {
        user.setEnabled(enabled);
        repository.save(user);

        eventPublisher.publishEvent(new UserStatusEvent(this, user.getId(), user.getEnabled()));
    }
}

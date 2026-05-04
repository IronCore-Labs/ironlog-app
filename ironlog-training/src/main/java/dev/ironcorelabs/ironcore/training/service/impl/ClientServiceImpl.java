package dev.ironcorelabs.ironcore.training.service.impl;

import dev.ironcorelabs.ironcore.training.mapper.ClientMapper;
import dev.ironcorelabs.ironcore.training.model.entity.Client;
import dev.ironcorelabs.ironcore.training.model.repository.ClientRepository;
import dev.ironcorelabs.ironlog.core.dto.ClientDTO;
import dev.ironcorelabs.ironlog.core.dto.UserDTO;
import dev.ironcorelabs.ironlog.core.exception.RecordNotFoundException;
import dev.ironcorelabs.ironlog.core.service.ClientService;
import dev.ironcorelabs.ironlog.core.service.InternalClientService;
import dev.ironcorelabs.ironlog.core.service.InternalUserService;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService, InternalClientService {

    private final ClientRepository repository;

    private final InternalUserService userService;

    private final ClientMapper mapper;

    @Override
    @PreAuthorize("@sec.isAdmin()")
    @Transactional
    public ClientDTO update(Long id, UpdateMyClientProfileRequest request) {
        return updateUnsafe(id, request);
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    @Transactional
    public ClientDTO updateByExternalId(UUID id, UpdateMyClientProfileRequest request) {
        return updateByExternalIdUnsafe(id, request);
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    @Transactional
    public ClientDTO create(RegisterClientRequest request) {
        return createUnsafe(request);
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    public ClientDTO findById(Long id) {
        return findUnsafeById(id);
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    public ClientDTO findByExternalId(UUID id) {
        return findUnsafeByExternalId(id);
    }

    @Override
    public ClientDTO findUnsafeById(Long id) {
        final Client client = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return mapper.toDto(client);
    }

    @Override
    public ClientDTO findUnsafeByExternalId(UUID id) {
        final Client client = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return mapper.toDto(client);
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    @Transactional
    public void delete(Long id) {
        deleteUnsafe(id);
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    @Transactional
    public void deleteByExternalId(UUID id) {
        deleteUnsafeByExternalId(id);
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    public ClientList findAll(int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        final Page<Client> clients = repository.findByEnabledTrue(pageable);

        return mapper.toDto(clients);
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    public ClientList findByTrainer(UUID trainerId, int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        return mapper.toDto(repository.findByTrainerExternalId(trainerId, pageable));
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    public ClientList findByTrainerId(Long trainerId, int page, int size) {
        return findByTrainerIdUnsafe(trainerId, page, size);
    }

    @Override
    public ClientList findByTrainerIdUnsafe(Long trainerId, int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        return mapper.toDto(repository.findByTrainerId(trainerId, pageable));
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    @Transactional
    public void promoteToClient(UUID id) {
        repository.findByExternalId(id)
                .ifPresentOrElse(this::activateClient, () -> createEmptyClient(id));
    }

    protected void activateClient(Client client) {
        setEnabled(client, true);
        userService.addRole(client.getId(), UserRoleEnum.CLIENT);
    }

    protected void createEmptyClient(UUID id) {
        final UserDTO user = userService.findByExternalId(id);

        final Client client = new Client();
        client.setId(user.getId());
        client.setExternalId(user.getExternalId());
        client.setNeedRegistration(true);

        repository.save(client);

        userService.addRole(user.getId(), UserRoleEnum.CLIENT);
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    @Transactional
    public void disableClient(UUID id) {
        disableUnsafe(id);
    }

    @Override
    @Transactional
    public ClientDTO updateUnsafe(Long id, UpdateMyClientProfileRequest request) {
        final Client client = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return update(client, request);
    }

    @Override
    @Transactional
    public ClientDTO updateByExternalIdUnsafe(UUID id, UpdateMyClientProfileRequest request) {
        final Client client = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return update(client, request);
    }

    @Override
    @Transactional
    public ClientDTO createUnsafe(RegisterClientRequest request) {

        boolean userNeedRegistration = userService.needRegistration(request.getUserData());
        boolean needRegistration = needRegistration(request);

        UserDTO userData = userService.createUnsafe(request.getUserData()
                , UserRoleEnum.CLIENT, userNeedRegistration || needRegistration);

        Client client = mapper.toEntity(request);
        client.setNeedRegistration(needRegistration);
        client.setId(userData.getId());
        client.setExternalId(userData.getExternalId());

        return mapper.toDto(repository.save(client));
    }

    @Override
    @Transactional
    public void deleteUnsafe(Long id) {
        final Client client = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        userService.revokeRole(client.getId(), UserRoleEnum.CLIENT);

        repository.delete(client);
    }

    @Override
    @Transactional
    public void deleteUnsafeByExternalId(UUID id) {
        final Client client = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        userService.revokeRole(client.getId(), UserRoleEnum.CLIENT);

        repository.delete(client);
    }

    @Override
    @Transactional
    public void disableUnsafe(UUID id) {
        final Client client = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        setEnabled(client, false);

        userService.revokeRole(id, UserRoleEnum.CLIENT);
    }

    protected ClientDTO update(Client client, UpdateMyClientProfileRequest request) {
        mapper.updateEntity(request, client);
        client.setNeedRegistration(false);
        return mapper.toDto(repository.save(client));
    }

    protected void setEnabled(Client client, boolean enabled) {
        client.setEnabled(enabled);
        repository.save(client);
    }
}

package dev.ironcorelabs.ironcore.training.service.impl;

import dev.ironcorelabs.ironcore.training.mapper.TrainerMapper;
import dev.ironcorelabs.ironcore.training.model.entity.Trainer;
import dev.ironcorelabs.ironcore.training.model.repository.TrainerRepository;
import dev.ironcorelabs.ironlog.core.dto.ClientDTO;
import dev.ironcorelabs.ironlog.core.dto.UserDTO;
import dev.ironcorelabs.ironlog.core.exception.RecordNotFoundException;
import dev.ironcorelabs.ironlog.core.service.*;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService, InternalTrainerService {

    private final TrainerRepository repository;
    private final TrainerMapper mapper;
    private final InternalUserService userService;
    private final InternalEnrollmentService enrollmentService;
    private final InternalClientService clientService;

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    @Transactional
    public TrainerDto update(Long id, UpdateMyTrainerProfileRequest request) {
        return updateUnsafe(id, request);
    }

    @Override
    @Transactional
    public TrainerDto updateUnsafe(Long id, UpdateMyTrainerProfileRequest request) {
        final Trainer trainer = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return updateTrainer(trainer, request);
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    @Transactional
    public TrainerDto updateByExternalId(UUID id, UpdateMyTrainerProfileRequest request) {
        return updateByExternalIdUnsafe(id, request);
    }

    @Override
    @Transactional
    public TrainerDto updateByExternalIdUnsafe(UUID id, UpdateMyTrainerProfileRequest request) {

        final Trainer trainer = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return updateTrainer(trainer, request);
    }

    protected TrainerDto updateTrainer(Trainer trainer, UpdateMyTrainerProfileRequest request) {
        mapper.updateEntity(request, trainer);
        return mapper.toDto(repository.save(trainer));
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    @Transactional
    public TrainerDto create(RegisterTrainerRequest request) {
        return createUnsafe(request);
    }

    @Override
    @Transactional
    public TrainerDto createUnsafe(RegisterTrainerRequest request) {
        boolean needRegistrationUser = userService.needRegistration(request.getUserData());
        boolean needRegistration = needRegistration(request);

        final UserDTO user = userService.createUnsafe(request.getUserData()
                , UserRoleEnum.TRAINER
                , needRegistrationUser || needRegistration);

        final Trainer trainer = mapper.toEntity(request);
        trainer.setId(user.getId());
        trainer.setExternalId(user.getExternalId());
        trainer.setNeedRegistration(needRegistration);

        return mapper.toDto(repository.save(trainer));
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    public TrainerDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    public TrainerDto findByExternalId(UUID id) {
        return repository.findByExternalId(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));
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
    public TrainerList findAll(int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        final Page<Trainer> trainers = repository.findByEnabledTrue(pageable);

        return mapper.toDto(trainers);
    }

    @Override
    @PreAuthorize("@sec.isAdmin()")
    @Transactional
    public void promoteToTrainer(UUID id) {
        final Optional<Trainer> sTrainer = repository.findByExternalId(id);
        sTrainer.ifPresentOrElse(this::activateTrainer, () -> createEmptyTrainer(id));
    }

    protected void activateTrainer(Trainer trainer) {
        setEnabled(trainer, true);
        userService.addRole(trainer.getId(), UserRoleEnum.TRAINER);
    }

    protected void createEmptyTrainer(UUID id) {
        final UserDTO user = userService.findByExternalId(id);

        Trainer trainer = new Trainer();
        trainer.setId(user.getId());
        trainer.setExternalId(id);
        trainer.setNeedRegistration(true);

        repository.save(trainer);

        userService.addRole(user.getId(), UserRoleEnum.TRAINER);
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#id)")
    @Transactional
    public void disableTrainer(UUID id) {
        disableUnsafe(id);
    }

    @Override
    @Transactional
    public void deleteUnsafe(Long id) {
        final Trainer trainer = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        repository.delete(trainer);

        userService.revokeRole(id, UserRoleEnum.TRAINER);
    }

    @Override
    @Transactional
    public void deleteUnsafeByExternalId(UUID id) {
        final Trainer trainer = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        repository.delete(trainer);

        userService.revokeRole(trainer.getId(), UserRoleEnum.TRAINER);
    }

    @Override
    @Transactional
    public void disableUnsafe(UUID id) {
        final Trainer trainer = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        setEnabled(trainer, false);

        userService.revokeRole(id, UserRoleEnum.TRAINER);
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#trainerId)")
    public ClientList getMyAthletes(Long trainerId, int page, int size) {
        return clientService.findByTrainerIdUnsafe(trainerId, page, size);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isOwnerOrAdmin(#trainerId)")
    public ClientDTO registerAndEnroll(Long trainerId, RegisterClientRequest request) {
        return registerAndEnrollUnsafe(trainerId, request);
    }

    @Override
    @Transactional
    public ClientDTO registerAndEnrollUnsafe(Long trainerId, RegisterClientRequest request) {
        final ClientDTO client = clientService.createUnsafe(request);

        enrollmentService.assignClientToTrainer(client.getExternalId(), trainerId);

        return client;
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isOwnerOrAdmin(#trainerId)")
    public void assignClientToTrainer(Long trainerId, UUID clientExtId) {
        enrollmentService.assignClientToTrainer(clientExtId, trainerId);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isOwnerOrAdmin(#trainerId)")
    public void disableClientToTrainer(Long trainerId, UUID clientExtId) {
        enrollmentService.disableClientToTrainer(clientExtId, trainerId);
    }

    protected boolean needRegistration(RegisterTrainerRequest request) {
        return Stream.of(request.getSpeciality(), request.getExperienceYears())
                .anyMatch(value -> Objects.isNull(value) || value instanceof String s && !StringUtils.hasText(s));
    }

    protected void setEnabled(Trainer trainer, Boolean enabled) {
        trainer.setEnabled(enabled);
        repository.save(trainer);
    }
}

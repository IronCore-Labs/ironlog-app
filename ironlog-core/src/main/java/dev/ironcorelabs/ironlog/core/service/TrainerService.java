package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.restapi.openapi.model.*;

import java.util.UUID;

public interface TrainerService {
    TrainerDto update(Long id, UpdateMyTrainerProfileRequest request);

    TrainerDto updateUnsafe(Long id, UpdateMyTrainerProfileRequest request);

    TrainerDto updateByExternalId(UUID id, UpdateMyTrainerProfileRequest request);

    TrainerDto updateByExternalIdUnsafe(UUID id, UpdateMyTrainerProfileRequest request);

    TrainerDto create(RegisterTrainerRequest request);

    TrainerDto createUnsafe(RegisterTrainerRequest request);

    TrainerDto findById(Long id);

    TrainerDto findByExternalId(UUID id);

    void delete(Long id);

    void deleteByExternalId(UUID id);

    TrainerList findAll(int page, int size);

    void promoteToTrainerDto(UUID id);

    ClientList getMyAthletes(int page, int size);

    Client registerAndEnroll(RegisterClientRequest request);

    void enroll(UUID clientId);
}

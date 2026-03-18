package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterTrainerRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.Trainer;
import dev.ironcorelabs.ironlog.restapi.openapi.model.TrainerList;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UpdateMyTrainerProfileRequest;

import java.util.UUID;

public interface TrainerService {
    Trainer update(Long id, UpdateMyTrainerProfileRequest request);

    Trainer updateUnsafe(Long id, UpdateMyTrainerProfileRequest request);

    Trainer updateByExternalId(UUID id, UpdateMyTrainerProfileRequest request);

    Trainer updateByExternalIdUnsafe(UUID id, UpdateMyTrainerProfileRequest request);

    Trainer create(RegisterTrainerRequest request);

    Trainer createUnsafe(RegisterTrainerRequest request);

    Trainer findById(Long id);

    Trainer findByExternalId(UUID id);

    void delete(Long id);

    void deleteByExternalId(UUID id);

    TrainerList findAll(int page, int size);
}

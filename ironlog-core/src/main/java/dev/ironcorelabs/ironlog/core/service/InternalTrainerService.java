package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.core.dto.ClientDTO;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterClientRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterTrainerRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.TrainerDto;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UpdateMyTrainerProfileRequest;

import java.util.UUID;

public interface InternalTrainerService {
    TrainerDto updateUnsafe(Long id, UpdateMyTrainerProfileRequest request);

    TrainerDto updateByExternalIdUnsafe(UUID id, UpdateMyTrainerProfileRequest request);

    TrainerDto createUnsafe(RegisterTrainerRequest request);

    ClientDTO registerAndEnrollUnsafe(Long trainerId, RegisterClientRequest request);

    void deleteUnsafe(Long id);

    void deleteUnsafeByExternalId(UUID id);

    void disableUnsafe(UUID id);
}

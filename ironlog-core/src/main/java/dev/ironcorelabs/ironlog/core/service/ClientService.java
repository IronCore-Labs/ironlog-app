package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.core.dto.ClientDTO;
import dev.ironcorelabs.ironlog.restapi.openapi.model.ClientList;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterClientRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UpdateMyClientProfileRequest;

import java.util.UUID;

public interface ClientService {
    ClientDTO update(Long id, UpdateMyClientProfileRequest request);

    ClientDTO updateByExternalId(UUID id, UpdateMyClientProfileRequest request);

    ClientDTO create(RegisterClientRequest request);

    ClientDTO findById(Long id);

    ClientDTO findByExternalId(UUID id);

    void delete(Long id);

    void deleteByExternalId(UUID id);

    ClientList findAll(int page, int size);

    ClientList findByTrainer(UUID trainerId, int page, int size);

    ClientList findByTrainerId(Long trainerId, int page, int size);

    void promoteToClient(UUID id);

    void disableClient(UUID id);
}

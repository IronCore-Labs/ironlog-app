package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.core.dto.ClientDTO;
import dev.ironcorelabs.ironlog.restapi.openapi.model.ClientList;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterClientRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UpdateMyClientProfileRequest;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public interface InternalClientService {
    ClientDTO updateUnsafe(Long id, UpdateMyClientProfileRequest request);

    ClientDTO updateByExternalIdUnsafe(UUID id, UpdateMyClientProfileRequest request);

    ClientDTO createUnsafe(RegisterClientRequest request);

    void deleteUnsafe(Long id);

    void deleteUnsafeByExternalId(UUID id);

    void disableUnsafe(UUID id);

    ClientDTO findUnsafeById(Long id);

    ClientDTO findUnsafeByExternalId(UUID id);

    ClientList findByTrainerIdUnsafe(Long trainerId, int page, int size);

    default boolean needRegistration(RegisterClientRequest request) {
        // 1. Validar campos obligatorios base
        boolean missingBaseData = Stream.of(
                request.getWeight(),
                request.getHeight(),
                request.getHasSurgeries()
        ).anyMatch(Objects::isNull);

        if (missingBaseData) return true;

        // 2. Validación condicional de cirugías
        // Si tiene cirugías pero el detalle es nulo o está vacío, necesita registro
        return Boolean.TRUE.equals(request.getHasSurgeries()) &&
                !StringUtils.hasText(request.getSurgeryDetails());
    }
}

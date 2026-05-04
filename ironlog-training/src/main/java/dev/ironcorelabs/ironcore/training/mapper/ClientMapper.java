package dev.ironcorelabs.ironcore.training.mapper;

import dev.ironcorelabs.ironcore.training.model.entity.Client;
import dev.ironcorelabs.ironlog.core.dto.ClientDTO;
import dev.ironcorelabs.ironlog.restapi.openapi.model.ClientDto;
import dev.ironcorelabs.ironlog.restapi.openapi.model.ClientList;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterClientRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UpdateMyClientProfileRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toDto(Client client);

    List<ClientDTO> toDto(List<Client> clients);

    default ClientList toDto(Page<Client> page) {
        return new ClientList()
                .first(page.isFirst())
                .last(page.isLast())
                .pageNumber(page.getNumber())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .totalElements((int) page.getTotalElements())
                .content(toDto(page.getContent()).stream().map(client -> (ClientDto) client).toList());
    }

    Client toEntity(RegisterClientRequest request);

    void updateEntity(UpdateMyClientProfileRequest request, @MappingTarget Client client);
}

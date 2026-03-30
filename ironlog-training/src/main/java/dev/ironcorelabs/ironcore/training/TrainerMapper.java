package dev.ironcorelabs.ironcore.training;

import dev.ironcorelabs.ironcore.training.model.entity.Trainer;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterTrainerRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.TrainerDto;
import dev.ironcorelabs.ironlog.restapi.openapi.model.TrainerList;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UpdateMyTrainerProfileRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    TrainerDto toDto(Trainer trainer);

    List<TrainerDto> toDto(List<Trainer> trainers);

    default TrainerList toDto(Page<Trainer> page) {
        return new TrainerList()
                .first(page.isFirst())
                .last(page.isLast())
                .pageNumber(page.getNumber())
                .totalPage(page.getTotalPages())
                .pageSize(page.getSize())
                .totalElements((int) page.getTotalElements())
                .content(toDto(page.getContent()));
    }

    Trainer toEntity(RegisterTrainerRequest request);

    void updateEntity(UpdateMyTrainerProfileRequest request, @MappingTarget Trainer entity);
}

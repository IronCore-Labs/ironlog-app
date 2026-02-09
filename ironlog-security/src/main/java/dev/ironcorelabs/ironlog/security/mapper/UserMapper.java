package dev.ironcorelabs.ironlog.security.mapper;

import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import dev.ironcorelabs.ironlog.security.dto.CustomGrantedAuthority;
import dev.ironcorelabs.ironlog.security.dto.UserDetailsCustom;
import dev.ironcorelabs.ironlog.security.model.entities.AppUser;
import dev.ironcorelabs.ironlog.security.model.enums.UserRole;
import dev.ironcorelabs.ironlog.security.service.impl.AppUserDetailsService;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "email", target = "username")
    @Mapping(target = "authorities", ignore = true)
    UserDetailsCustom toUserDetail(AppUser user);

    default List<CustomGrantedAuthority> toAuthorities(List<String> permissions) {
        return permissions.stream().map(CustomGrantedAuthority::new).toList();
    }

    User toDto(AppUser user);

    List<User> toDto(List<AppUser> users);

    default UserRoleEnum toDto(UserRole entity) {
        if (entity == null)
        {
            return null;
        }

        return UserRoleEnum.fromValue(entity.getValue().replaceAll("ROLE_", ""));
    }

    default UserList toDto(Page<AppUser> page) {
        return new UserList()
                .first(page.isFirst())
                .last(page.isLast())
                .pageNumber(page.getNumber())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .totalElements((int) page.getTotalElements())
                .content(toDto(page.getContent()));
    }

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "enabled")
    @Mapping(ignore = true, target = "refreshTokens")
    @Mapping(ignore = true, target = "password")
    AppUser toEntity(CreateUserRequest request);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "enabled")
    @Mapping(ignore = true, target = "refreshTokens")
    @Mapping(ignore = true, target = "role")
    @Mapping(ignore = true, target = "password")
    AppUser toEntity(UpdateUserRequest request);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "enabled")
    @Mapping(ignore = true, target = "refreshTokens")
    @Mapping(ignore = true, target = "role")
    @Mapping(ignore = true, target = "password")
    AppUser toEntity(RegisterUserRequest request);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "enabled")
    @Mapping(ignore = true, target = "refreshTokens")
    @Mapping(ignore = true, target = "role")
    @Mapping(ignore = true, target = "password")
    void updateEntity(UpdateUserRequest request, @MappingTarget AppUser entity);

    default UserRole toEntity(UserRoleEnum dto) {
        if (dto == null)
        {
            return null;
        }

        return UserRole.valueOf("ROLE_" + dto.getValue());
    }
}

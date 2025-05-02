package edu.tinkoff.tinkoffbackendacademypetproject.mappers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.RoleResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Role;
import edu.tinkoff.tinkoffbackendacademypetproject.model.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Named("getRoleAsString")
    static String getRole(Role role) {
        return role.getDescription();
    }

    @Mapping(source = "name", target = "role", qualifiedByName = "getRoleAsString")
    RoleResponseDto toRoleResponseDto(RoleEntity roleEntity);
}

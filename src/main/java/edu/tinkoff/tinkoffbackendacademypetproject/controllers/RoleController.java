package edu.tinkoff.tinkoffbackendacademypetproject.controllers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.RoleResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.RoleMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsAdmin;
import edu.tinkoff.tinkoffbackendacademypetproject.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Роли", description = "Работа с ролями")
@RequestMapping("/v1/roles")
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping
    @Operation(description = "Получение списка ролей, которые может назначить админ", summary = "Получение списка ролей, которые может назначить админ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получены роли"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsAdmin
    public List<RoleResponseDto> getAllRolesInsteadOfAdmin() {
        return roleService
                .getAllRolesInsteadOfAdmin()
                .stream()
                .map(roleMapper::toRoleResponseDto)
                .toList();
    }
}

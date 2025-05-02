package edu.tinkoff.tinkoffbackendacademypetproject.controllers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.AccountUpdateRoleRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.PageRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.SaveInformationAboutAccountRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.*;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.AccountMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.PageMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsAdmin;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsUser;
import edu.tinkoff.tinkoffbackendacademypetproject.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "Аккаунты", description = "Работа с аккаунтами")
@RequestMapping("/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    private final PageMapper pageMapper;
    private final AccountMapper accountMapper;

    @GetMapping
    @Operation(description = "Получение админом списка всех пользователей", summary = "Получение админом списка всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получение всего списка пользователей"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsAdmin
    public PageResponseDto<GetAllUserResponseDto> getAllUsers(@ParameterObject @Valid PageRequestDto pageRequestDto) {
        return pageMapper.toPageResponseDto(
                accountService.getAllUsers(pageRequestDto.getPageNumber(), pageRequestDto.getPageSize()),
                accountMapper::toGetAllUserResponseDto
        );
    }

    @PostMapping("/{id}/ban")
    @Operation(description = "Удаление пользователя", summary = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешно удален пользователь"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsAdmin
    public StatusAccountResponseDto banUser(@PathVariable
                                            @Schema(description = "Id пользователя", example = "1")
                                            @Min(1)
                                            @NotNull
                                            Long id) throws EntityModelNotFoundException {
        return accountMapper.toStatusAccountResponseDto(accountService.banUserById(id));
    }

    @PostMapping("/{id}/unban")
    @Operation(description = "Возвращение пользователя", summary = "Возвращение пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешно возвращен пользователь"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsAdmin
    public StatusAccountResponseDto unbanUser(@PathVariable
                                              @Schema(description = "Id пользователя", example = "1")
                                              @Min(1)
                                              @NotNull
                                              Long id) throws EntityModelNotFoundException {
        return accountMapper.toStatusAccountResponseDto(accountService.unbanUserById(id));
    }

    @GetMapping(value = "/{nickname}")
    @Operation(description = "Получение информации о пользователе", summary = "Получение информации о пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация успешно получена"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
    })
    public AccountResponseDto getAccount(@PathVariable
                                         @Schema(description = "Nickname пользователя", example = "admin")
                                         @Size(max = 150, message = "Слишком длинный nickname")
                                         @NotBlank
                                         String nickname) throws EntityModelNotFoundException {
        return accountMapper.toAccountResponseDto(accountService.getAccount(nickname));
    }

    @PutMapping(consumes = {"multipart/form-data"})
    @Operation(description = "Сохранение информации о пользователе", summary = "Сохранение информации о пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация успешно сохранена"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsUser
    public AccountResponseDto saveInfoAboutAccount(@ModelAttribute @Valid SaveInformationAboutAccountRequestDto request) throws EntityModelNotFoundException {
        return accountMapper.toAccountResponseDto(accountService.saveInformationAboutAccount(
                accountMapper.fromSaveInformationAboutAccountRequestDto(request),
                request.photo()
        ));
    }

    @PutMapping("/{id}/role")
    @Operation(description = "Обновление роли у пользователя. Это может сделать только админ", summary = "Админ имеет возможность изменить роль пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно изменена роль пользователя"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsAdmin
    public AccountUpdateRoleResponseDto updateAccountRole(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid AccountUpdateRoleRequestDto updateRoleRequest) throws RoleNotFoundException {
        Account account = accountService.updateAccountRole(id, updateRoleRequest.roleId());
        return accountMapper.toAccountUpdateRoleResponseDto(account);
    }
}

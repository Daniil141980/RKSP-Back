package edu.tinkoff.tinkoffbackendacademypetproject.controllers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.CreatePublicationRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.PublicationsInOneCategoryRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.PageResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.PublicationResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.PublicationTitleAndIdResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.PageMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.PublicationMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsAdmin;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsUser;
import edu.tinkoff.tinkoffbackendacademypetproject.services.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Публикации", description = "Работа с публикациями")
@RequestMapping("/v1/publications")
public class PublicationController {
    private final PublicationService publicationService;
    private final PublicationMapper publicationMapper;
    private final PageMapper pageMapper;

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(description = "Добавить новую публикацию", summary = "Добавить новую публикацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно добавлена новая публикация"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsUser
    public PublicationResponseDto createPublication(@ModelAttribute @Valid CreatePublicationRequestDto request,
                                                    @AuthenticationPrincipal Account account) throws EntityModelNotFoundException {
        return publicationMapper.toPublicationResponseDto(
                publicationService.createPublicationInSubjectTopic(publicationMapper.fromCreatePublicationRequestDto(request), account, request.files())
        );
    }

    @GetMapping("/{id}")
    @Operation(description = "Найти публикацию по id", summary = "Найти публикацию по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получена публикация"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так")
    })
    public PublicationResponseDto getPublication(@PathVariable
                                                 @Min(value = 1, message = "Id публикации не может быть меньше 1")
                                                 @NotNull(message = "Id публикации не может быть пустой")
                                                 @Schema(description = "Id публикации", example = "1")
                                                 Long id) throws EntityModelNotFoundException {
        return publicationMapper.toPublicationResponseDto(publicationService.getPublication(id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удалить публикацию по id", summary = "Удалить публикацию по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно удалена публикация"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsAdmin
    public void deletePublication(@PathVariable
                                  @Min(value = 1, message = "Id публикации не может быть меньше 1")
                                  @NotNull(message = "Id публикации не может быть пустой")
                                  @Schema(description = "Id публикации", example = "1")
                                  Long id) throws EntityModelNotFoundException {
        publicationService.deletePublication(id);
    }

    @GetMapping
    @Operation(description = "Найти все публикации в заданном топике по id",
            summary = "Найти все публикации в заданном топике по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получены все публикации в заданном топике по id"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так")
    })
    public PageResponseDto<PublicationTitleAndIdResponseDto> getPublicationsInOneTopic(@ParameterObject @Valid PublicationsInOneCategoryRequestDto publications) {
        return pageMapper.toPageResponseDto(publicationService.getPublicationsInOneCategory(
                        publications.getPageNumber(),
                        publications.getPageSize(),
                        publications.getSubjectTopicId()),
                publicationMapper::toPublicationTitleAndIdResponseDto);
    }

}

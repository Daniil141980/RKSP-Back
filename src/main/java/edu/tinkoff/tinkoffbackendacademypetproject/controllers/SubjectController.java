package edu.tinkoff.tinkoffbackendacademypetproject.controllers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.SubjectByCourseNumberRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.SubjectRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.PageResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.SubjectResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.PageMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.SubjectMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.security.annotations.IsAdmin;
import edu.tinkoff.tinkoffbackendacademypetproject.services.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с предметами
 */
@RestController
@RequestMapping("/v1/subjects")
@RequiredArgsConstructor
@Validated
@Tag(name = "Предмет", description = "Работа с предметами")
public class SubjectController {
    /**
     * Сервис для работы с предметами
     */
    private final SubjectService subjectService;
    /**
     * Маппер для работы с предметами
     */
    private final SubjectMapper subjectMapper;
    private final PageMapper pageMapper;

    /**
     * Получение всех предметов, которые есть на указанном курсе
     *
     * @param dto передача параметров, которые будут использоваться для поиска
     * @return список предметов
     */

    @GetMapping
    @Operation(summary = "Получение предметов на указанном курсе", description = "Возвращает список предметов, которые ведутся на указанном курсе. Если же не указан курс, то возвращаются все предметы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос был успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Запрос имеет не валидные параметры", content = @Content),
    })
    public PageResponseDto<SubjectResponseDto> findAllByCourseNumber(@ParameterObject @Valid SubjectByCourseNumberRequestDto dto) {
        return pageMapper.toPageResponseDto(subjectService.findAllByCourseNumber(
                        dto.getPageNumber(),
                        dto.getPageSize(),
                        dto.getCourse()),
                subjectMapper::toSubjectResponseDTO);
    }

    /**
     * Создание нового предмета
     *
     * @param dto дто, содержащее название предмета
     * @return сохраненный предмет
     */
    @PostMapping
    @Operation(summary = "Создание нового предмета", description = "Создание нового предмета на каком-то курсе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предмет был успешно создан"),
            @ApiResponse(responseCode = "400", description = "Такой предмет уже существует", content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })

    @IsAdmin
    public SubjectResponseDto createSubject(@Valid @RequestBody SubjectRequestDto dto) throws EntityModelNotFoundException {
        SubjectEntity subject = subjectMapper.fromSubjectRequestDTO(dto);
        var savedSubject = subjectService.createSubject(subject);
        return subjectMapper.toSubjectResponseDTO(savedSubject);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление предмета", description = "Удаление предмета на указанном курсе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предмет был успешно удален"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @IsAdmin
    public void deleteSubject(@Valid @PathVariable @Min(0) Long id) {
        subjectService.deleteSubject(id);
    }
}

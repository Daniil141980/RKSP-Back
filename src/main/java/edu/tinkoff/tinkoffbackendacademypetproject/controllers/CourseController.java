package edu.tinkoff.tinkoffbackendacademypetproject.controllers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.CourseResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.mappers.CourseMapper;
import edu.tinkoff.tinkoffbackendacademypetproject.model.CourseEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для работы с курсами
 */
@RestController
@RequestMapping("/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Курс", description = "Работа с курсами")
public class CourseController {
    /**
     * Сервис для работы с курсами
     */
    private final CourseService courseService;
    /**
     * Маппер курсов
     */
    private final CourseMapper courseMapper;

    /**
     * Получение всех курсов
     *
     * @return список всех курсов
     */
    @GetMapping
    @Operation(summary = "Получение всех курсов обучения", description = "Получение всех курсов обучения и их описаний")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получен список курсов")
    })
    public List<CourseResponseDto> getCourses() {
        List<CourseEntity> courses = courseService.findAll();
        return courseMapper.toListOfCourseResponseDTO(courses);
    }
}

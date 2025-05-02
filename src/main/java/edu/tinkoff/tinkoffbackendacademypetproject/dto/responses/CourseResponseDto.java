package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ДТО для получения курса
 */
public record CourseResponseDto(@Schema(name = "Номер курса", example = "1")
                                @JsonProperty(value = "course_number")
                                Long courseNumber,
                                @Schema(name = "Описание курса", example = "1 курс бакалавриата")
                                String description) {
}

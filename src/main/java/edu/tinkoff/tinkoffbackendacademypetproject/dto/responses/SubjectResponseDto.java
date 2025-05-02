package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ДТО, которое возвращает предмет
 */
public record SubjectResponseDto(@Schema(description = "Идентификатор предмета", example = "1")
                                 Long id,
                                 @Schema(description = "Название предмета", example = "Математика")
                                 String name) {

}

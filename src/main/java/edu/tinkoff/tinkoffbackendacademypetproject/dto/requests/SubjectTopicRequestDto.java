package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Класс для получения всех секций с указанным предметом и курсом
 */
public record SubjectTopicRequestDto(/**
                                      * Название предмета
                                      */
                                     @Min(1)
                                     @Schema(description = "Id предмета", example = "1")
                                     @NotNull
                                     @JsonProperty("subject_id")
                                     Long subjectId,
                                     @Min(1)
                                     @Schema(description = "Id типа топика", example = "2")
                                     @NotNull
                                     @JsonProperty("topic_id")
                                     Long topicId) {

}

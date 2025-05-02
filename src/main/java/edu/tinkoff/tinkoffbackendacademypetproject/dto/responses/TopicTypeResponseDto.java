package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ДТО для получения типа топика
 */
public record TopicTypeResponseDto(@Schema(name = "Идентификатор типа топика", example = "1")
                                   Long id,

                                   @Schema(name = "Тип топика", example = "Контрольная работа")
                                   @JsonProperty(value = "topic_type")
                                   String topicType) {
}

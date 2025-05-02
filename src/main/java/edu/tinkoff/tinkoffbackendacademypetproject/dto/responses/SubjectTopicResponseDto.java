package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ДТО-ответ про топики предметов
 */

public record SubjectTopicResponseDto(@Schema(description = "id топика")
                                      Long id,
                                      @Schema(description = "ДТО предмета")
                                      @JsonProperty("subject")
                                      SubjectResponseDto subjectResponseDTO,
                                      @Schema(description = "ДТО типа топика")
                                      @JsonProperty("topic")
                                      TopicTypeResponseDto topicTypeResponseDTO) {

}

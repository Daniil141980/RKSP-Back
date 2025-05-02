package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record PublicationResponseDto(Long id,
                                     String description,

                                     String title,
                                     @JsonProperty("created_at") LocalDateTime createdAt,
                                     List<FileResponseDto> files,
                                     String nickname) {
}

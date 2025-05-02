package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record CommentResponseDto(Long id,
                                 String content,
                                 @JsonProperty("created_at") LocalDateTime createdAt,
                                 @JsonProperty("last_updated_at") LocalDateTime lastUpdatedAt,
                                 String nickname,
                                 @JsonProperty("is_anonymous") Boolean isAnonymous
) {
}

package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentAudResponseDto(
        @JsonProperty("comment_id")
        Long commentId,
        Long rev,
        String operation,
        String content,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("last_updated_at")
        String lastUpdatedAt
) {
}

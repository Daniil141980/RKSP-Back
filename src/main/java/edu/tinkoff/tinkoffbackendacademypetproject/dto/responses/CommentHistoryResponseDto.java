package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record CommentHistoryResponseDto(Long id,
                                        String content,
                                        @JsonProperty("time_change") LocalDateTime timeChange) {
}

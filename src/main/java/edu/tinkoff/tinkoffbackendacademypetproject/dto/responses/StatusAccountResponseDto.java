package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StatusAccountResponseDto(Long id, @JsonProperty("is_banned") Boolean isBanned) {
}

package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AccountUpdateRoleRequestDto(
        @Min(0)
        @NotNull(message = "id не может быть пустым")
        @JsonProperty("role_id")
        Long roleId) {
}

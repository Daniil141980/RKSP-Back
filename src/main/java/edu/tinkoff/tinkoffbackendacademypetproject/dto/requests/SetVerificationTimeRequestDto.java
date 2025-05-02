package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SetVerificationTimeRequestDto(@Schema(description = "Частота удаления старых комментариев", example = "0 */15 * * * ?")
                                            @Size(max = 100, message = "Слишком длинная крона")
                                            @NotBlank(message = "Крона не может быть пустой")
                                            String cron) {
}
package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import edu.tinkoff.tinkoffbackendacademypetproject.validation.constraints.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record AccountRegistrationRequestDto(@Size(max = 150, message = "Слишком длинный никнейм")
                                            @NotBlank(message = "Никнейм не должен быть пустым")
                                            @Schema(description = "Никнейм пользователя", example = "user123")
                                            String nickname,

                                            @Size(max = 50, message = "Слишком длинный пароль")
                                            @NotBlank(message = "Пароль не должен быть пустым")
                                            @Schema(description = "Пароль пользователя", example = "12345678")
                                            String password,

                                            @Size(max = 150, message = "Слишком длинный email")
                                            @NotBlank(message = "Email не должен быть пустым")
                                            @Schema(description = "Email пользователя", example = "user@gmail.com")
                                            @ValidEmail
                                            String email) {
}

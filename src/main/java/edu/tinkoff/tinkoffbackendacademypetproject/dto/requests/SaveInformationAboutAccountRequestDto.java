package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record SaveInformationAboutAccountRequestDto(@Schema(description = "Nickname пользователя", example = "admin")
                                                    @Size(max = 150, message = "Слишком длинный nickname")
                                                    @NotBlank
                                                    String nickname,
                                                    @Schema(description = "Описание пользователя", example = "Я люблю капибар")
                                                    @Size(max = 250, message = "Слишком длинное описание")
                                                    String description,
                                                    @Schema(description = "Имя пользователя", example = "Вася")
                                                    @Size(max = 150, message = "Слишком длинное имя")
                                                    @JsonProperty("first_name")
                                                    String firstName,
                                                    @Schema(description = "Фамилия пользователя", example = "Пупкин")
                                                    @Size(max = 150, message = "Слишком длинная фамилия")
                                                    @JsonProperty("last_name")
                                                    String lastName,
                                                    @Schema(description = "Отчество пользователя", example = "Васильевич")
                                                    @Size(max = 150, message = "Слишком длинное отчество")
                                                    @JsonProperty("middle_name")
                                                    String middleName,
                                                    @Schema(description = "Дата рождения пользователя", example = "2000-01-01")
                                                    @Size(max = 50, message = "Слишком длинная дата рождения")
                                                    @JsonProperty("birth_date")
                                                    String birthDate,
                                                    MultipartFile photo) {
}

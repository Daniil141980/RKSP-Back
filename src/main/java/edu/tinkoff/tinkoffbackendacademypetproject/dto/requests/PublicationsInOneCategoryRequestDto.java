package edu.tinkoff.tinkoffbackendacademypetproject.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PublicationsInOneCategoryRequestDto extends PageRequestDto {
    @Min(value = 1, message = "Id топика не может быть меньше 1")
    @NotNull(message = "Id топика не может быть пустым")
    @Schema(description = "Id топика", example = "1")
    private final Long subjectTopicId;
}

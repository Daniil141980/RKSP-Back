package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FileResponseDto(Long id,
                              @JsonProperty("file_name_in_directory")
                              String fileNameInDirectory,
                              @JsonProperty("initial_file_name")
                              String initialFileName) {
}

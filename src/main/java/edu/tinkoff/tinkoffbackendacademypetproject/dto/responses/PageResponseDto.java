package edu.tinkoff.tinkoffbackendacademypetproject.dto.responses;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record PageResponseDto<T>(@JsonProperty("total_pages")
                                 int totalPages,
                                 @JsonProperty("total_size")
                                 long totalSize,
                                 @JsonProperty("page_number")
                                 int pageNumber,
                                 @JsonProperty("page_size")
                                 int pageSize,
                                 List<T> content) {
}

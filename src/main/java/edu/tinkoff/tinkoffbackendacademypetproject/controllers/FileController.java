package edu.tinkoff.tinkoffbackendacademypetproject.controllers;

import edu.tinkoff.tinkoffbackendacademypetproject.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Файлы", description = "Работа с файлами")
@RequestMapping("/v1/files")
public class FileController {
    private final FileService storageService;

    @GetMapping("/{filename}")
    @Operation(description = "Достать файл", summary = "Достать файл")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получен файл из папки"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так")
    })
    public ResponseEntity<Resource> serveFile(@PathVariable
                                              @Schema(description = "Название файла в папке", example = "obi.jpg")
                                              @Size(max = 400, message = "Слишком длинное название для файла")
                                              @NotBlank(message = "Название файла не может быть пустым")
                                              String filename) {
        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + storageService.getInitialFileName(filename) + "\"").body(file);
    }

    @GetMapping(
            value = "/photos/{filename}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @Operation(description = "Достать фотографию", summary = "Достать фотографию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно достали фотографию"),
            @ApiResponse(responseCode = "400", description = "Что-то пошло не так")
    })
    public @ResponseBody byte[] getImage(@PathVariable
                                         @Schema(description = "Название фотографии в папке", example = "obi.jpg")
                                         @Size(max = 400, message = "Слишком длинное название для фотографии")
                                         @NotBlank(message = "Название фотографии не может быть пустым")
                                         String filename) throws IOException {
        return IOUtils.toByteArray(storageService.loadAsResource(filename).getInputStream());
    }

}

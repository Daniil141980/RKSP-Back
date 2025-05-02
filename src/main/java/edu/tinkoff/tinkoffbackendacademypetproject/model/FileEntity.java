package edu.tinkoff.tinkoffbackendacademypetproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Вложение (файл)
 */
@Entity
@Table(name = "file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileEntity {
    /**
     * Идентификатор вложения (файла)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ссылка на вложение (файл)
     */
    @Column(name = "file_name_in_directory")
    private String fileNameInDirectory;

    /**
     * Расширение вложения (файла)
     */
    @Column(name = "initial_file_name")
    private String initialFileName;
}

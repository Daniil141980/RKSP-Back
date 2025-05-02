package edu.tinkoff.tinkoffbackendacademypetproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Предмет
 */

@Entity
@Table(name = "subject")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectEntity {
    /**
     * Идентификатор предмета
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название предмета
     */
    @Column(name = "name")
    private String name;

    /**
     * Список топиков у предмета
     */
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<SubjectTopicEntity> subjectTopics;

    /**
     * Курс, к которому относится предмет
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_number", referencedColumnName = "course_number")
    private CourseEntity course;
}

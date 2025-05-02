package edu.tinkoff.tinkoffbackendacademypetproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Тип топика
 */
@Entity
@Table(name = "topic_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TopicTypeEntity {
    /**
     * Идентификатор топика
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Вид топика
     */
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private Topic topic;

    /**
     * Список топиков с данным типом
     */
    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<SubjectTopicEntity> subjectTopics;
}

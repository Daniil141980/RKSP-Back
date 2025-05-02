package edu.tinkoff.tinkoffbackendacademypetproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Пост
 */
@Entity
@Table(name = "publication")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublicationEntity {
    /**
     * Идентификатор поста
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Описание поста
     */
    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    /**
     * Дата создания поста
     */
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "supports_thread")
    private Boolean supportsThread;

    /**
     * Комментарии к посту
     */
    @OneToMany(mappedBy = "publication", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "publication_file",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileEntity> files;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "subject_topic_publication",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_topic_id"))
    private Set<SubjectTopicEntity> subjectTopics;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "publication")
    private NewsPublicationEntity publication;
}

package edu.tinkoff.tinkoffbackendacademypetproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "news_publication")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsPublicationEntity {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "id", referencedColumnName = "id")
    private PublicationEntity publication;
}
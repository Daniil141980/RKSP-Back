package edu.tinkoff.tinkoffbackendacademypetproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentAudit {
    private Long commentId;
    private String revtype;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}

package edu.tinkoff.tinkoffbackendacademypetproject.repositories;

import edu.tinkoff.tinkoffbackendacademypetproject.model.CommentAudit;
import edu.tinkoff.tinkoffbackendacademypetproject.model.CommentEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentAudRepository {
    private final EntityManager entityManager;

    public List<CommentAudit> getRevisionForComment(Long commentId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

// Создаем запрос на получение данных из аудиторской таблицы для конкретной ревизии
        List<Object[]> revisionData = auditReader.createQuery()
                .forRevisionsOfEntity(CommentEntity.class, false, true)
                .add(AuditEntity.id().eq(commentId))
                .getResultList();

        List<CommentAudit> list = new ArrayList<>();
        for (Object[] o : revisionData) {
            CommentAudit commentAudit = new CommentAudit();
            CommentEntity commentEntity = (CommentEntity) o[0];
            RevisionType type = (RevisionType) o[2];
            commentAudit.setCommentId(commentEntity.getId());
            commentAudit.setRevtype(type.name());
            commentAudit.setContent(commentEntity.getContent());
            commentAudit.setCreatedAt(commentEntity.getCreatedAt());
            commentAudit.setLastUpdatedAt(commentEntity.getLastUpdatedAt());
            list.add(commentAudit);
        }

        return list;
    }

}

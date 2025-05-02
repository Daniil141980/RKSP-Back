package edu.tinkoff.tinkoffbackendacademypetproject.services;


import edu.tinkoff.tinkoffbackendacademypetproject.model.CommentAudit;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.CommentAudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentAudService {
    private final CommentAudRepository commentAudRepository;

    public List<CommentAudit> getRevisionForComment(Long commentId) {
        return commentAudRepository.getRevisionForComment(commentId);
    }
}

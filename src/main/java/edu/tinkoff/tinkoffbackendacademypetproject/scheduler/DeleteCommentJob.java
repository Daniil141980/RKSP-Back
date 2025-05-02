package edu.tinkoff.tinkoffbackendacademypetproject.scheduler;

import edu.tinkoff.tinkoffbackendacademypetproject.services.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@RequiredArgsConstructor
public class DeleteCommentJob extends QuartzJobBean {

    private final CommentService commentService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        commentService.deleteOldComment();
        log.info("DeleteCommentJob");
    }
}
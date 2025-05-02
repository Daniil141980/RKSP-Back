package edu.tinkoff.tinkoffbackendacademypetproject.scheduler;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DeleteCommentJobConfig {
    private final SchedulerProperties schedulerProperties;

    @Bean
    public JobDetail deleteCommentJobDetail() {
        return JobBuilder.newJob(DeleteCommentJob.class)
                .withIdentity("deleteCommentJob", schedulerProperties.getPermanentJobsGroupName())
                .storeDurably()
                .requestRecovery(true)
                .build();
    }

    @Bean
    public Trigger deleteCommentTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(deleteCommentJobDetail())
                .withIdentity("deleteCommentJobTrigger", schedulerProperties.getPermanentJobsGroupName())
                .withSchedule(CronScheduleBuilder.cronSchedule(schedulerProperties.getDeleteCommentJobCron()))
                .build();
    }
}
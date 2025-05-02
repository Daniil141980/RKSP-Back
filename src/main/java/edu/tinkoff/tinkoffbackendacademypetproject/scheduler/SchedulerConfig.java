package edu.tinkoff.tinkoffbackendacademypetproject.scheduler;

import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    private final SchedulerProperties schedulerProperties;

    @Bean
    public Scheduler scheduler(List<Trigger> triggers, List<JobDetail> jobDetails, SchedulerFactoryBean factory) throws Exception {
        factory.setWaitForJobsToCompleteOnShutdown(true);
        Scheduler scheduler = factory.getScheduler();
        revalidateJobs(jobDetails, scheduler);
        rescheduleTriggers(triggers, scheduler);
        scheduler.start();
        return scheduler;
    }

    public void rescheduleTriggers(List<Trigger> triggers, Scheduler scheduler) throws Exception {
        for (Trigger trigger : triggers) {
            if (!scheduler.checkExists(trigger.getKey())) {
                scheduler.scheduleJob(trigger);
            } else {
                scheduler.rescheduleJob(trigger.getKey(), trigger);
            }
        }
    }

    private void revalidateJobs(List<JobDetail> jobDetails, Scheduler scheduler) throws Exception {
        List<JobKey> jobKeys = new ArrayList<>();
        for (JobDetail jobDetail : jobDetails) {
            jobKeys.add(jobDetail.getKey());
        }

        for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(schedulerProperties.getPermanentJobsGroupName()))) {
            if (!jobKeys.contains(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        }
    }
}
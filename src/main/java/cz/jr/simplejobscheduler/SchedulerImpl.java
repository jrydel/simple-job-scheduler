package cz.jr.simplejobscheduler;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by Jiří Rýdel on 1/10/20, 8:52 PM
 */
public class SchedulerImpl implements Scheduler {

    private final Map<String, JobData> map = new ConcurrentHashMap<>();

    private final ScheduledThreadPoolExecutor executor;
    private final ScheduledFuture<?> jobCheckerFuture;
    private final JobChecker jobChecker;

    public SchedulerImpl(int corePoolSize, Duration checkerDuration, JobChecker.JobCheckerListener jobCheckerListener) {
        this.executor = new ScheduledThreadPoolExecutor(corePoolSize, new SimpleThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        this.jobChecker = new JobChecker(map, checkerDuration, jobCheckerListener);
        this.jobCheckerFuture = this.executor.scheduleWithFixedDelay(this.jobChecker, 0, checkerDuration.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void scheduleJob(Job job) {
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(job, job.getInitialDelay().toMillis(), job.getExecutionRate().toMillis(), TimeUnit.MILLISECONDS);
        map.put(UUID.randomUUID().toString(), new JobData(job, future));
    }

    @Override
    public SchedulerMetadata getMetaData() {
        SchedulerMetadata schedulerMetadata = new SchedulerMetadata();
        map.forEach((key, jobData) -> schedulerMetadata.getJobMetadataList().add(
                new SchedulerMetadata.JobMetadata(
                        jobData.getJob().getName(),
                        isExecuted(jobData.getScheduledFuture()),
                        jobData.getJob().getLastExecutionTime(),
                        jobData.getJob().getLastCompletionTime(),
                        jobData.getJob().getNextExecutionTime(),
                        jobData.getJob().isExecutedWithError()
                )
        ));
        schedulerMetadata.getJobMetadataList().add(new SchedulerMetadata.JobMetadata(
                jobChecker.getName(),
                isExecuted(jobCheckerFuture),
                jobChecker.getLastExecutionTime(),
                jobChecker.getLastCompletionTime(),
                jobChecker.getNextExecutionTime(),
                jobChecker.isExecutedWithError()
        ));
        return schedulerMetadata;
    }

    private boolean isExecuted(ScheduledFuture<?> future) {
        return future.getDelay(TimeUnit.MILLISECONDS) < 0;
    }
}

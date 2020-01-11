package cz.jr.simplejobscheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jiří Rýdel on 1/10/20, 10:10 PM
 */
public class JobChecker extends Job {

    private static final Logger LOG = LogManager.getLogger(JobChecker.class);

    private final Map<String, JobData> map;
    private final Duration delay;
    private final JobCheckerListener jobCheckerListener;

    public JobChecker(Map<String, JobData> map, Duration delay, JobCheckerListener jobCheckerListener) {
        super("TaskChecker", Duration.ofMillis(0), delay, null, new SimpleJobListener());
        this.map = map;
        this.delay = delay;
        this.jobCheckerListener = jobCheckerListener;
    }

    @Override
    protected void execute() {
        map.forEach((key, jobData) -> {
            long jobDelay = jobData.getScheduledFuture().getDelay(TimeUnit.MILLISECONDS);
            Duration maxExecutionTime = jobData.getJob().getMaxExecutionTime();
            if (maxExecutionTime.toMillis() + jobDelay < 0) {
                jobCheckerListener.onTaskExceedation(jobData.getJob().getName(), Duration.ofMillis(Math.abs(jobDelay)), jobData.getJob().getMaxExecutionTime());
            }
        });
    }

    public interface JobCheckerListener {
        void onTaskExceedation(String name, Duration executionTime, Duration maxExecutionTime);
    }
}

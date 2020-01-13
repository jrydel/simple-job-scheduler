package cz.jr.simplejobscheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by Jiří Rýdel on 1/10/20, 9:12 PM
 */
public abstract class Job implements Runnable {

    private static final Logger LOG = LogManager.getLogger(Job.class);

    private final String name;
    private final Duration initialDelay;
    private final Duration executionRate;
    private final Duration maxExecutionTime;
    private final JobListener jobListener;

    private Instant lastExecutionTime;
    private Instant lastCompletionTime;
    private Instant nextExecutionTime;
    private boolean executedWithError = false;

    public Job(String name, Duration initialDelay, Duration executionRate, Duration maxExecutionTime, JobListener jobListener) {
        this.name = name;
        this.initialDelay = initialDelay;
        this.executionRate = executionRate;
        this.maxExecutionTime = maxExecutionTime;
        this.jobListener = jobListener;
    }

    @Override
    public void run() {
        jobListener.beforeExecution();
        try {
            lastExecutionTime = Instant.now();
            execute();
        } catch (Throwable t) {
            LOG.error(String.format("Error in job: %s", name), t);
            executedWithError = true;
        } finally {
            lastCompletionTime = Instant.now();
            nextExecutionTime = lastCompletionTime.plus(executionRate);
            jobListener.afterExecution();
        }
    }

    protected abstract void execute();

    public String getName() {
        return name;
    }

    public Duration getInitialDelay() {
        return initialDelay;
    }

    public Duration getExecutionRate() {
        return executionRate;
    }

    public Duration getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public JobListener getJobListener() {
        return jobListener;
    }

    public Instant getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(Instant lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }

    public Instant getLastCompletionTime() {
        return lastCompletionTime;
    }

    public void setLastCompletionTime(Instant lastCompletionTime) {
        this.lastCompletionTime = lastCompletionTime;
    }

    public Instant getNextExecutionTime() {
        return nextExecutionTime;
    }

    public void setNextExecutionTime(Instant nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }

    public boolean isExecutedWithError() {
        return executedWithError;
    }

    public void setExecutedWithError(boolean executedWithError) {
        this.executedWithError = executedWithError;
    }
}

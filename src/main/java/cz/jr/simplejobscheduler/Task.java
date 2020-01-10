package cz.jr.simplejobscheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by Jiří Rýdel on 1/10/20, 9:12 PM
 */
public abstract class Task implements Runnable {

    private static final Logger LOG = LogManager.getLogger(Task.class);

    private final String name;
    private final Duration executionRate;
    private final Duration maxExecutionTime;
    private final TaskListener taskListener;

    private Instant lastExecutionTime;
    private Instant lastCompletionTime;
    private boolean executedWithError = false;

    public Task(String name, Duration executionRate, Duration maxExecutionTime, TaskListener taskListener) {
        this.name = name;
        this.executionRate = executionRate;
        this.maxExecutionTime = maxExecutionTime;
        this.taskListener = taskListener;
    }

    @Override
    public void run() {
        taskListener.beforeExecution();
        try {
            lastExecutionTime = Instant.now();
            execute();
        } catch (Throwable t) {
            LOG.error(String.format("Error in task: %s", name), t);
            executedWithError = true;
        } finally {
            lastCompletionTime = Instant.now();
            taskListener.afterExecution();
        }
    }

    protected abstract void execute();

    public String getName() {
        return name;
    }

    public Duration getExecutionRate() {
        return executionRate;
    }

    public Duration getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public TaskListener getTaskListener() {
        return taskListener;
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

    public boolean isExecutedWithError() {
        return executedWithError;
    }

    public void setExecutedWithError(boolean executedWithError) {
        this.executedWithError = executedWithError;
    }
}

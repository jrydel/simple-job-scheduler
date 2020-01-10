package cz.jr.simplejobscheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jiří Rýdel on 1/10/20, 10:10 PM
 */
public class TaskChecker extends Task {

    private static final Logger LOG = LogManager.getLogger(TaskChecker.class);

    private final Map<String, TaskData> map;
    private final Duration delay;

    public TaskChecker(Map<String, TaskData> map, Duration delay) {
        super("TaskChecker", Duration.ofMinutes(2), null, new TaskListener() {
            @Override
            public void beforeExecution() {
                LOG.info("Starting task checker.");
            }

            @Override
            public void afterExecution() {
                LOG.info("Terminating task checker.");
            }
        });
        this.map = map;
        this.delay = delay;
    }

    @Override
    protected void execute() {
        map.forEach((key, taskData) -> {
            long taskDelay = taskData.getScheduledFuture().getDelay(TimeUnit.MILLISECONDS);
            Duration maxExecutionTime = taskData.getTask().getMaxExecutionTime();
            if (maxExecutionTime.toMillis() + taskDelay < 0) {
                LOG.error(String.format("Task: %s has exceeded: %d, execution time: %s.", taskData.getTask().getName(), taskData.getTask().getMaxExecutionTime().toMillis(), taskDelay));
            }
        });
    }
}

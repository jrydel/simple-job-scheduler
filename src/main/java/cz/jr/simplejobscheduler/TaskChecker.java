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
    private final TaskCheckerListener taskCheckerListener;

    public TaskChecker(Map<String, TaskData> map, Duration delay, TaskCheckerListener taskCheckerListener) {
        super("TaskChecker", Duration.ofMillis(0), delay, null, new EmptyTaskListener());
        this.map = map;
        this.delay = delay;
        this.taskCheckerListener = taskCheckerListener;
    }

    @Override
    protected void execute() {
        map.forEach((key, taskData) -> {
            long taskDelay = taskData.getScheduledFuture().getDelay(TimeUnit.MILLISECONDS);
            Duration maxExecutionTime = taskData.getTask().getMaxExecutionTime();
            if (maxExecutionTime.toMillis() + taskDelay < 0) {
                taskCheckerListener.onTaskExceedation(taskData.getTask().getName(), Duration.ofMillis(Math.abs(taskDelay)), taskData.getTask().getMaxExecutionTime());
            }
        });
    }

    public interface TaskCheckerListener {
        void onTaskExceedation(String name, Duration executionTime, Duration maxExecutionTime);
    }
}

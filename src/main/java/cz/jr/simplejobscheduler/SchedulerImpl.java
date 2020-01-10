package cz.jr.simplejobscheduler;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by Jiří Rýdel on 1/10/20, 8:52 PM
 */
public class SchedulerImpl implements Scheduler {

    private final Map<String, TaskData> map = new ConcurrentHashMap<>();

    private final ScheduledThreadPoolExecutor executor;
    private final ScheduledFuture<?> taskCheckerFuture;
    private final TaskChecker taskChecker;

    public SchedulerImpl(int corePoolSize, Duration checkerDuration, TaskChecker.TaskCheckerListener taskCheckerListener) {
        this.executor = new ScheduledThreadPoolExecutor(corePoolSize, new SimpleThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        this.taskChecker = new TaskChecker(map, checkerDuration, taskCheckerListener);
        this.taskCheckerFuture = this.executor.scheduleWithFixedDelay(this.taskChecker, 0, checkerDuration.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void scheduleTask(Task task) {
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(task, 0, task.getExecutionRate().toMillis(), TimeUnit.MILLISECONDS);
        map.put(UUID.randomUUID().toString(), new TaskData(task, future));
    }

    @Override
    public SchedulerMetadata getMetaData() {
        SchedulerMetadata schedulerMetadata = new SchedulerMetadata();
        map.forEach((key, taskData) -> {
            schedulerMetadata.getTaskMetaDataList().add(
                    new SchedulerMetadata.TaskMetaData(
                            taskData.getTask().getName(),
                            isExecuted(taskData.getScheduledFuture()),
                            taskData.getTask().getLastExecutionTime(),
                            taskData.getTask().getLastCompletionTime(),
                            taskData.getTask().isExecutedWithError()
                    )
            );
        });
        schedulerMetadata.getTaskMetaDataList().add(new SchedulerMetadata.TaskMetaData(
                taskChecker.getName(),
                isExecuted(taskCheckerFuture),
                taskChecker.getLastExecutionTime(),
                taskChecker.getLastCompletionTime(),
                taskChecker.isExecutedWithError()
        ));
        return schedulerMetadata;
    }

    private boolean isExecuted(ScheduledFuture<?> future) {
        return future.getDelay(TimeUnit.MILLISECONDS) < 0;
    }
}

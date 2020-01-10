package cz.jr.simplejobscheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by Jiří Rýdel on 1/10/20, 9:09 PM
 */
public class TaskData {

    private final Task task;
    private final ScheduledFuture<?> scheduledFuture;

    public TaskData(Task task, ScheduledFuture<?> scheduledFuture) {
        this.task = task;
        this.scheduledFuture = scheduledFuture;
    }

    public Task getTask() {
        return task;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }
}

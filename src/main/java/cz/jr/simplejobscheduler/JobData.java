package cz.jr.simplejobscheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by Jiří Rýdel on 1/10/20, 9:09 PM
 */
public class JobData {

    private final Job job;
    private final ScheduledFuture<?> scheduledFuture;

    public JobData(Job job, ScheduledFuture<?> scheduledFuture) {
        this.job = job;
        this.scheduledFuture = scheduledFuture;
    }

    public Job getJob() {
        return job;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }
}

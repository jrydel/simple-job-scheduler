package cz.jr.simplejobscheduler;

/**
 * Created by Jiří Rýdel on 1/10/20, 8:46 PM
 */
public interface Scheduler {

    void scheduleJob(Job job);

    SchedulerMetadata getMetaData();
}

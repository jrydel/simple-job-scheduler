package cz.jr.simplejobscheduler;

/**
 * Created by Jiří Rýdel on 1/10/20, 8:46 PM
 */
public interface Scheduler {

    void scheduleTask(Job job);

    SchedulerMetadata getMetaData();
}

package cz.jr.simplejobscheduler;

/**
 * Created by Jiří Rýdel on 1/10/20, 9:47 PM
 */
public interface JobListener {

    void beforeExecution();

    void afterExecution();
}

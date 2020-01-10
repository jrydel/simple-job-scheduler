package cz.jr.simplejobscheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Created by Jiří Rýdel on 1/10/20, 10:46 PM
 */
public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        SchedulerImpl scheduler = new SchedulerImpl(4, Duration.ofSeconds(20));

        scheduler.scheduleTask(new Task("Task1", Duration.ofSeconds(5), Duration.ofSeconds(10), new EmptyTaskListener()) {
            @Override
            protected void execute() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    LOG.error("Error.", e);
                }
            }
        });

        scheduler.scheduleTask(new Task("Task2", Duration.ofSeconds(3), Duration.ofSeconds(10), new EmptyTaskListener()) {
            @Override
            protected void execute() {
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    LOG.error("Error.", e);
                }
            }
        });
    }
}

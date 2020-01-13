package cz.jr.simplejobscheduler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiří Rýdel on 1/10/20, 8:52 PM
 */
public class SchedulerMetadata {

    private List<JobMetadata> jobMetadataList = new ArrayList<>();

    public List<JobMetadata> getJobMetadataList() {
        return jobMetadataList;
    }

    public void setJobMetadataList(List<JobMetadata> jobMetadataList) {
        this.jobMetadataList = jobMetadataList;
    }

    public static class JobMetadata {

        private final String name;
        private final boolean isRunning;
        private final Instant lastExecutionTime;
        private final Instant lastCompletionTime;
        private final Instant nextExecutionTime;
        private final boolean executedWithError;

        public JobMetadata(String name, boolean isRunning, Instant lastExecutionTime, Instant lastCompletionTime, Instant nextExecutionTime, boolean executedWithError) {
            this.name = name;
            this.isRunning = isRunning;
            this.lastExecutionTime = lastExecutionTime;
            this.lastCompletionTime = lastCompletionTime;
            this.nextExecutionTime = nextExecutionTime;
            this.executedWithError = executedWithError;
        }

        public String getName() {
            return name;
        }

        public boolean isRunning() {
            return isRunning;
        }

        public Instant getLastExecutionTime() {
            return lastExecutionTime;
        }

        public Instant getLastCompletionTime() {
            return lastCompletionTime;
        }

        public Instant getNextExecutionTime() {
            return nextExecutionTime;
        }

        public boolean isExecutedWithError() {
            return executedWithError;
        }
    }
}

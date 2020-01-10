package cz.jr.simplejobscheduler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiří Rýdel on 1/10/20, 8:52 PM
 */
public class SchedulerMetadata {

    private List<TaskMetaData> taskMetaDataList = new ArrayList<>();

    public List<TaskMetaData> getTaskMetaDataList() {
        return taskMetaDataList;
    }

    public void setTaskMetaDataList(List<TaskMetaData> taskMetaDataList) {
        this.taskMetaDataList = taskMetaDataList;
    }

    public static class TaskMetaData {

        private final String name;
        private final boolean isRunning;
        private final Instant lastExecutionTime;
        private final Instant lastCompletionTime;
        private final boolean executedWithError;

        public TaskMetaData(String name, boolean isRunning, Instant lastExecutionTime, Instant lastCompletionTime, boolean executedWithError) {
            this.name = name;
            this.isRunning = isRunning;
            this.lastExecutionTime = lastExecutionTime;
            this.lastCompletionTime = lastCompletionTime;
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

        public boolean isExecutedWithError() {
            return executedWithError;
        }

        @Override
        public String toString() {
            return "TaskMetaData{" +
                    "name='" + name + '\'' +
                    ", isRunning=" + isRunning +
                    ", lastExecutionTime=" + lastExecutionTime +
                    ", lastCompletionTime=" + lastCompletionTime +
                    ", executedWithError=" + executedWithError +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SchedulerMetadata{" +
                "taskMetaDataList=" + taskMetaDataList +
                '}';
    }
}

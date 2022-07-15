package br.com.bmont.task.request;

import java.time.LocalDateTime;

public class TaskPostRequest {
    private String task;
    private boolean isComplete;
    private LocalDateTime date;

    public TaskPostRequest() {
    }

    public TaskPostRequest(String task, boolean isComplete, LocalDateTime date) {
        this.task = task;
        this.isComplete = isComplete;
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

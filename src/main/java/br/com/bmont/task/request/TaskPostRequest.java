package br.com.bmont.task.request;

import java.time.LocalDateTime;

public class TaskPostRequest {
    private String task;
    private boolean complete;
    private LocalDateTime date;

    public TaskPostRequest() {
    }

    public TaskPostRequest(String task, boolean complete, LocalDateTime date) {
        this.task = task;
        this.complete = complete;
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

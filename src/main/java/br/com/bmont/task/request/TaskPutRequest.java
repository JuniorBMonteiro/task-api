package br.com.bmont.task.request;

import java.time.LocalDateTime;

public class TaskPutRequest {
    private long id;
    private String task;
    private boolean isComplete;
    private LocalDateTime date;

    public TaskPutRequest() {
    }

    public TaskPutRequest(long id, String task, boolean isComplete, LocalDateTime date) {
        this.id = id;
        this.task = task;
        this.isComplete = isComplete;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

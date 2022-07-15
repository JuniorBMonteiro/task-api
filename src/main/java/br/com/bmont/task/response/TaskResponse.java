package br.com.bmont.task.response;

import java.time.LocalDateTime;

public class TaskResponse {
    private long id;
    private String task;
    private boolean isComplete;
    private LocalDateTime date;

    public TaskResponse() {
    }

    public TaskResponse(long id, String task, boolean isComplete, LocalDateTime date) {
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

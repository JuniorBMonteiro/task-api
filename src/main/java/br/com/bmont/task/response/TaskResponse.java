package br.com.bmont.task.response;

import java.time.LocalDateTime;

public class TaskResponse {
    private Long id;
    private String task;
    private boolean complete;
    private LocalDateTime date;

    public TaskResponse() {
    }

    public TaskResponse(Long id, String task, boolean complete, LocalDateTime date) {
        this.id = id;
        this.task = task;
        this.complete = complete;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

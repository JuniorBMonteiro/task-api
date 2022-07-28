package br.com.bmont.task.request;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TaskPutRequest {
    @NotNull(message = "Id cannot be null")
    private Long id;
    private String task;
    private boolean complete;
    private LocalDateTime date;

    public TaskPutRequest() {
    }

    public TaskPutRequest(Long id, String task, boolean complete, LocalDateTime date) {
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

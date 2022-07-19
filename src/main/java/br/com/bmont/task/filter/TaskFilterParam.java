package br.com.bmont.task.filter;

import br.com.bmont.task.model.User;

import java.time.LocalDateTime;

public class TaskFilterParam {
    private String task;
    private String complete;
    private LocalDateTime date;
    private User user;

    public TaskFilterParam() {
    }

    public TaskFilterParam(String task, String complete, LocalDateTime date, User user) {
        this.task = task;
        this.complete = complete;
        this.date = date;
        this.user = user;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package br.com.bmont.task.util;

import br.com.bmont.task.model.Task;
import br.com.bmont.task.model.User;
import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;

import java.time.LocalDateTime;

public class TaskCreator {
    public static Task createTaskWithoutId(User user) {
        Task task = new Task();
        task.setTask("teste");
        task.setComplete(false);
        task.setDate(LocalDateTime.now());
        task.setUser(user);
        return task;
    }

    public static Task createTaskWithCompleteTrue(User user) {
        Task task = new Task();
        task.setTask("isComplete");
        task.setComplete(true);
        task.setDate(LocalDateTime.now());
        task.setUser(user);
        return task;
    }

    public static Task createTaskWithId(User user) {
        return new Task(1L, "teste", false, LocalDateTime.now(), user);
    }

    public static TaskPostRequest createTaskPostRequest() {
        return new TaskPostRequest("teste", false, LocalDateTime.now());
    }

    public static TaskPutRequest createTaskPutRequest() {
        return new TaskPutRequest(1L, "testePutRequest", true, LocalDateTime.now());
    }
    public static TaskResponse createTaskResponse() {
        return new TaskResponse(1L, "teste", false, LocalDateTime.now());
    }


}
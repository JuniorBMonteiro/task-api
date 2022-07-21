package br.com.bmont.task.util;

import br.com.bmont.task.model.Task;
import br.com.bmont.task.model.User;

import java.time.LocalDateTime;

public class TaskCreator {
    public static Task createTaskWithoutId(User user){
        Task task = new Task();
        task.setTask("teste");
        task.setComplete(false);
        task.setDate(LocalDateTime.now());
        task.setUser(user);
        return task;
    }
    public static Task createTaskWithId(User user){
        return new Task(1L, "teste", false, LocalDateTime.now(), user);
    }
}

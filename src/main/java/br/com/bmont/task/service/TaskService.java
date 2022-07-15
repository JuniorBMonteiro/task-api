package br.com.bmont.task.service;

import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    public List<TaskResponse> findAllTasks(){
        return new ArrayList<>();
    }

    public TaskResponse createTask(TaskPostRequest taskPostRequest) {
        return null;
    }

    public TaskResponse updateTask(TaskPutRequest taskPutRequest) {
        return null;
    }


    public void deleteTask(long taskId) {
    }
}

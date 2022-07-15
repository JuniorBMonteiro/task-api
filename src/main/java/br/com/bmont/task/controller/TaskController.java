package br.com.bmont.task.controller;

import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import br.com.bmont.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<?> findAllTasks(){
        return new ResponseEntity<>(taskService.findAllTasks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask(TaskPostRequest taskPostRequest){
        return new ResponseEntity<>(taskService.createTask(taskPostRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateTask(TaskPutRequest taskPutRequest){
        return new ResponseEntity<>(taskService.updateTask(taskPutRequest), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(long taskId){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

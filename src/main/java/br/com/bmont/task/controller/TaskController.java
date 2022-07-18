package br.com.bmont.task.controller;

import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import br.com.bmont.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<TaskResponse>> findAllTasks(Pageable pageable){
        return new ResponseEntity<>(taskService.findAllTasks(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskPostRequest taskPostRequest){
        return new ResponseEntity<>(taskService.createTask(taskPostRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskPutRequest taskPutRequest){
        return new ResponseEntity<>(taskService.updateTask(taskPutRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable long taskId){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

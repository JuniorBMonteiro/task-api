package br.com.bmont.task.controller;

import br.com.bmont.task.model.User;
import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import br.com.bmont.task.filter.TaskFilterParam;
import br.com.bmont.task.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> findAllTasks(@AuthenticationPrincipal UserDetails userDetails,
                                                           @RequestParam(required = false) String task,
                                                           @RequestParam(required = false) String complete,
                                                           @RequestParam(required = false)
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                               LocalDate date,
                                                           Pageable pageable){
        TaskFilterParam filter = new TaskFilterParam(task, complete, date, (User) userDetails);
        return new ResponseEntity<>(taskService.findAllTasks(filter, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid TaskPostRequest taskPostRequest){
        return new ResponseEntity<>(taskService.createTask(userDetails, taskPostRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TaskResponse> updateTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid TaskPutRequest taskPutRequest){
        return new ResponseEntity<>(taskService.updateTask(userDetails, taskPutRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long taskId){
        taskService.deleteTask(userDetails, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

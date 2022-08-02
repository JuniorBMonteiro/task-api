package br.com.bmont.task.controller;

import br.com.bmont.task.exception.ExceptionDetails;
import br.com.bmont.task.filter.TaskFilterParam;
import br.com.bmont.task.model.User;
import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import br.com.bmont.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final static String AUTHORIZATION_TYPE = "bearer-key";
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get a page of Tasks", security = @SecurityRequirement(name = AUTHORIZATION_TYPE))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "403", description = "The user is not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class) ))
    })

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> findAllTasks(@AuthenticationPrincipal UserDetails userDetails,
                                                           @RequestParam(required = false) String task,
                                                           @RequestParam(required = false) String complete,
                                                           @RequestParam(required = false)
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                               LocalDate date,
                                                           @ParameterObject Pageable pageable){
        TaskFilterParam filter = new TaskFilterParam(task, complete, date, (User) userDetails);
        return new ResponseEntity<>(taskService.findAllTasks(filter, pageable), HttpStatus.OK);
    }

    @Operation(summary = "Create task", security = @SecurityRequirement(name = AUTHORIZATION_TYPE))
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "The object sent in the request is not valid"),
            @ApiResponse(responseCode = "403", description = "The user is not authenticated",
            content = @Content(schema = @Schema(implementation = Void.class) ))
    })
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid TaskPostRequest taskPostRequest){
        return new ResponseEntity<>(taskService.createTask(userDetails, taskPostRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Task", security = @SecurityRequirement(name = AUTHORIZATION_TYPE))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400",
                    description = "The object sent in the request is not valid or the user is not the owner of the task",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "403", description = "The user is not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class) ))
    })
    @PutMapping
    public ResponseEntity<TaskResponse> updateTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid TaskPutRequest taskPutRequest){
        return new ResponseEntity<>(taskService.updateTask(userDetails, taskPutRequest), HttpStatus.OK);
    }

    @Operation(summary = "Delete Task", security = @SecurityRequirement(name = AUTHORIZATION_TYPE))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400",
                    description = "The object sent in the request is not valid or the user is not the owner of the task",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "403", description = "The user is not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class) ))
    })

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long taskId){
        taskService.deleteTask(userDetails, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
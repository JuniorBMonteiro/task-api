package br.com.bmont.task.controller;

import br.com.bmont.task.filter.TaskFilterParam;
import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import br.com.bmont.task.service.TaskService;
import br.com.bmont.task.util.TaskCreator;
import br.com.bmont.task.util.UserCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class TaskControllerTest {
    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @BeforeEach
    void init(){
        TaskResponse taskResponse = TaskCreator.createTaskResponse();

        BDDMockito.when(taskService.findAllTasks(ArgumentMatchers.any(TaskFilterParam.class),
                        ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(taskResponse)));

        BDDMockito.when(taskService.createTask(ArgumentMatchers.any(UserDetails.class),
                        ArgumentMatchers.any(TaskPostRequest.class)))
                .thenReturn(taskResponse);

        BDDMockito.when(taskService.updateTask(ArgumentMatchers.any(UserDetails.class),
                        ArgumentMatchers.any(TaskPutRequest.class)))
                .thenReturn(taskResponse);

        BDDMockito.doNothing().when(taskService)
                .deleteTask(ArgumentMatchers.any(UserDetails.class), ArgumentMatchers.anyLong());
    }

    @Test
    void findAllTasks_ReturnsPageOfTask_WhenSuccessful(){
        TaskResponse expectedTask = TaskCreator.createTaskResponse();
        UserDetails user = UserCreator.createUserWithId();
        // parametros não obrigatorios = null
        ResponseEntity<Page<TaskResponse>> response = taskController
                .findAllTasks(user, null, null, null, PageRequest.of(0,10));
        TaskResponse firstTask = response.getBody().toList().get(0);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedTask.getId(), firstTask.getId());
        Assertions.assertEquals(expectedTask.getTask(), firstTask.getTask());
    }

    @Test
    void createTask_ReturnsTaskResponse_WhenSuccessful(){
        TaskResponse expectedTask = TaskCreator.createTaskResponse();
        UserDetails user = UserCreator.createUserWithId();
        TaskPostRequest task = TaskCreator.createTaskPostRequest();
        ResponseEntity<TaskResponse> response = taskController.createTask(user, task);
        TaskResponse taskResponse = response.getBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedTask.getId(), taskResponse.getId());
        Assertions.assertEquals(expectedTask.getTask(), taskResponse.getTask());
    }

    @Test
    void updateTask_ReturnsTaskResponse_WhenSuccessful(){
        UserDetails user = UserCreator.createUserWithId();
        TaskResponse expectedTask = TaskCreator.createTaskResponse();
        TaskPutRequest task = TaskCreator.createTaskPutRequest();
        ResponseEntity<TaskResponse> response = taskController.updateTask(user,task);
        TaskResponse taskResponse = response.getBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedTask.getId(), taskResponse.getId());
        Assertions.assertEquals(expectedTask.getTask(), taskResponse.getTask());
    }

    @Test
    void deleteTask_RemovesTask_WhenSuccessful(){
        UserDetails user = UserCreator.createUserWithId();
        Long taskId = 1L;
        Assertions.assertDoesNotThrow(() -> taskController.deleteTask(user, taskId));
        ResponseEntity<Void> response = taskController.deleteTask(user, taskId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
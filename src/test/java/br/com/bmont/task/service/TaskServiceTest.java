package br.com.bmont.task.service;

import br.com.bmont.task.exception.EntityNotFoundException;
import br.com.bmont.task.exception.PermissionIsMissingException;
import br.com.bmont.task.model.Task;
import br.com.bmont.task.model.User;
import br.com.bmont.task.repository.TaskRepository;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void init(){
        Task task = TaskCreator.createTaskWithId(UserCreator.createUserWithId());

        BDDMockito.when(taskRepository.findAll(ArgumentMatchers.any(Specification.class),
                        ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task)));

        BDDMockito.when(taskRepository.save(ArgumentMatchers.any(Task.class)))
                .thenReturn(task);

        BDDMockito.when(taskRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(task));

        BDDMockito.doNothing().when(taskRepository).delete(ArgumentMatchers.any(Task.class));
    }

    @Test
    void findAllTasks_returnsPageOfTaskResponse_WhenSuccessful(){
        Task expectedTask = TaskCreator.createTaskWithId(UserCreator.createUserWithId());
        Pageable page = PageRequest.of(0,10);
        Page<TaskResponse> tasks = taskService.findAllTasks(null, page);
        TaskResponse firstTask = tasks.toList().get(0);
        Assertions.assertNotNull(tasks);
        Assertions.assertFalse(tasks.isEmpty());
        Assertions.assertEquals(1, tasks.getTotalElements());
        Assertions.assertNotNull(firstTask);
        Assertions.assertEquals(expectedTask.getId(), firstTask.getId());
        Assertions.assertEquals(expectedTask.getTask(), firstTask.getTask());
    }

    @Test
    void createTask_ReturnsTaskResponse_WhenSuccessful(){
        Long expectedId = 1L;
        TaskResponse task = taskService.createTask(UserCreator.createUserWithId(), TaskCreator.createTaskPostRequest());
        Assertions.assertNotNull(task);
        Assertions.assertNotNull(task.getId());
        Assertions.assertEquals(expectedId, task.getId());
    }

    @Test
    void updateTask_ReturnsTaskResponse_WhenSuccessful(){
        TaskResponse task = taskService.updateTask(UserCreator.createUserWithId(), TaskCreator.createTaskPutRequest());
        Assertions.assertNotNull(task);
        Assertions.assertNotNull(task.getId());
    }

    @Test
    void deleteTask_RemovesTask_WhenSuccessful(){
        User user = UserCreator.createUserWithId();
        Task task = TaskCreator.createTaskWithId(user);
        Assertions.assertDoesNotThrow(() -> taskService.deleteTask(user, task.getId()));
    }

    @Test
    void userIsOwner_ThrowsPermissionIsMissingException_WhenUserIsNotOwner(){
        User ownerUser = UserCreator.createUserWithId();
        Task task = TaskCreator.createTaskWithId(ownerUser);
        // sobreescreve mock implementado no beforeEach
        BDDMockito.when(taskRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(task));
        // Usuario dono da tarefa foi criado com ID = 1L
        // Usuario que tentara modificar a tarefa possui ID = 2L
        User invalidUser = UserCreator.createUserWithId();
        invalidUser.setId(2L);
        // Metodo acessado pelo updateTask por ser private
        Assertions.assertThrows(PermissionIsMissingException.class,
                () -> taskService.updateTask(invalidUser, TaskCreator.createTaskPutRequest()));
    }

    @Test
    void findTaskById_ThrowsEntityNotFoundException_WhenTaskIsNotFound(){
        // sobreescreve mock definido no beforeEach lançando a exception
        BDDMockito.when(taskRepository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        User user = UserCreator.createUserWithId();
        TaskPutRequest task = TaskCreator.createTaskPutRequest();
        Assertions.assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(user, task));
    }

}
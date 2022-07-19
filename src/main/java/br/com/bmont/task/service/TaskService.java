package br.com.bmont.task.service;

import br.com.bmont.task.filter.TaskFilterParam;
import br.com.bmont.task.filter.TaskSpecifications;
import br.com.bmont.task.mapper.TaskMapper;
import br.com.bmont.task.model.Task;
import br.com.bmont.task.model.User;
import br.com.bmont.task.repository.TaskRepository;
import br.com.bmont.task.request.TaskPostRequest;
import br.com.bmont.task.request.TaskPutRequest;
import br.com.bmont.task.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskResponse> findAllTasks(TaskFilterParam filter, Pageable pageable){
        Specification<Task> specification = TaskSpecifications.getFilteredTasks(filter);
        return TaskMapper.toTaskResponse(taskRepository.findAll(specification, pageable));
    }

    public TaskResponse createTask(UserDetails userDetails, TaskPostRequest taskPostRequest) {
        Task task = new Task();
        task.setTask(taskPostRequest.getTask());
        task.setComplete(taskPostRequest.isComplete());
        task.setDate(taskPostRequest.getDate());
        task.setUser((User) userDetails);
        return TaskMapper.toTaskResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(UserDetails userDetails, TaskPutRequest taskPutRequest) {
        Task taskSaved = findTaskByIdOrThrowException(taskPutRequest.getId());
        userIsOwner((User) userDetails, taskSaved);
        taskSaved.setTask(taskPutRequest.getTask());
        taskSaved.setComplete(taskPutRequest.isComplete());
        taskSaved.setDate(taskPutRequest.getDate());
        taskRepository.save(taskSaved);
        return TaskMapper.toTaskResponse(taskSaved);
    }


    public void deleteTask(UserDetails userDetails, long taskId) {
        Task taskSaved = findTaskByIdOrThrowException(taskId);
        userIsOwner((User) userDetails, taskSaved);
        taskRepository.delete(taskSaved);
    }

    private Task findTaskByIdOrThrowException(long taskId){
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    private void userIsOwner(User user, Task task){
        if(!Objects.equals(task.getUser(), user)){
            throw new RuntimeException("User not is owner");
        }
    }


}
